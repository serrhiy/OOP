'use strict';

const config = require('./config.json');
const find = require('./find.js');
const exists = require('./exists.js');
const fsp = require('node:fs/promises');
const execute = require('./execute.js')(config);
const compile = require('./compile.js')(config, find);
const moveResources = require('./moveResources.js')(config, exists);
const events = require('node:events');

const pipe = (...functions) => {
  const next = (value, index = 0) => {
    if (index >= functions.length) return value;
    const answer = functions[index](value);
    const callback = (arg) => next(arg, index + 1);
    return answer.then ? answer.then(callback) : callback(answer);
  };
  return (value) => next(value);
};

const compileProject = async () => {
  const programs = await fsp.readdir(config.programsFolder);
  const compiles = programs.map(compile);
  const copies = programs.map(moveResources);
  await Promise.all(compiles);
  await Promise.all(copies);
};

const manageProcesses = async (project) => {
  const processes = new Map();
  const manager = async (name) => {
    const subprocess = await execute(name);
    processes.set(name, subprocess);
    subprocess.stderr.pipe(process.stderr);
    subprocess.stdout.setEncoding('utf-8');
    subprocess.stdout.setDefaultEncoding('utf-8');
    const subprocesses = new Set();
    subprocess.stdout.on('data', async (chunk) => {
      const { service, receiver, data } = JSON.parse(chunk);
      if (!processes.has(receiver)) {
        subprocesses.add(await manager(receiver));
      }
      const subprocess = processes.get(receiver);
      const message = JSON.stringify({ service, data });
      subprocess.stdin.write(message + '\n');
    });
    subprocess.once('close', () => {
      processes.delete(name);
      for (const subprocess of subprocesses) {
        subprocess.kill();
      }
    });
    return subprocess;
  };
  return manager(project);
};

const main = pipe(
  compileProject,
  () => manageProcesses(config.mainProject),
  (mainProcess) => events.once(mainProcess, 'close'),
  () => fsp.rm(config.target, { recursive: true, force: true })
);

main();
