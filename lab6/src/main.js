'use strict';

const config = require('./config.json');
const find = require('./find.js');
const exists = require('./exists.js');
const fsp = require('node:fs/promises');
const execute = require('./execute.js')(config);
const compile = require('./compile.js')(config, find);
const moveResources = require('./moveResources.js')(config, exists);

const { programsFolder, mainProject } = config;

const asyncPipe = (...functions) => {
  const next = (value, index = 0) => {
    if (index >= functions.length) return value;
    const answer = functions[index](value);
    const callback = (arg) => next(arg, index + 1);
    return answer.then ? answer.then(callback) : callback(answer);
  };
  return next;
};

const compileProject = async () => {
  const programs = await fsp.readdir(programsFolder);
  const compiles = programs.map(compile);
  const copies = programs.map(moveResources);
  await Promise.all(compiles);
  await Promise.all(copies);
};

const manageProcesses = (project) => {
  const processes = new Map();
  const manager = (name) => {
    const subprocess = execute(name);
    subprocess.stderr.pipe(process.stderr);
    processes.set(name, subprocess);
    subprocess.stdout.setEncoding('utf-8');
    subprocess.stdout.setDefaultEncoding('utf-8');
    subprocess.stdout.on('data', (chunk) => {
      const { service, receiver, data } = JSON.parse(chunk);      
      if (receiver === 'manager' && service === 'log') {
        return void console.log(data);
      }
      if (!processes.has(receiver)) manager(receiver);
      const subprocess = processes.get(receiver);
      const message = JSON.stringify({ service, data });      
      subprocess.stdin.write(message + '\n');
    });
    subprocess.on('exit', () => void processes.delete(name));
    return manager;
  };
  return manager(project);
};

const main = asyncPipe(compileProject, () => manageProcesses(mainProject));

main();
