'use strict';

const config = require('./config.json');
const find = require('./find.js');
const exists = require('./exists.js');
const fsp = require('node:fs/promises');
const path = require('node:path');
const execute = require('./execute.js')(config);
const compile = require('./compile.js')(config, find);
const moveResources = require('./moveResources.js')(config, exists);

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
  const programs = await fsp.readdir(config.programsFolder);
  const compiles = programs.map(compile);
  const copies = programs.map(moveResources);
  await Promise.all(compiles);
  await Promise.all(copies);
};

const manageProcesses = async (mainProcess) => {
  mainProcess.stdout.setEncoding('utf-8');
  mainProcess.stdout.setDefaultEncoding('utf-8');
  const processes = new Map();
  processes.set(config.mainProject, mainProcess);
  mainProcess.stdout.pipe(process.stdout);
  mainProcess.stderr.pipe(process.stderr);
  mainProcess.stdout.on('data', async (chunk) => {
    // const { service, receiver, data } = JSON.parse(chunk);
    // if (!processes.has(receiver)) {
    //   const subprocess = execute(receiver);
    //   processes.set(receiver, subprocess);
    // }
    const message = JSON.stringify({ 'service': 'close' });
    mainProcess.stdin.write(message + '\n');
  });
};

const main = asyncPipe(compileProject, execute, manageProcesses);

main();
