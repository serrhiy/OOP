'use strict';

const path = require('node:path');
const { once } = require('node:events');
const child_process = require('node:child_process');

module.exports = (config, find) => async (program) => {
  const fullpath = path.join(config.programsFolder, program);
  const targetFolder = path.join(config.target, program);
  const files = await find(fullpath, '.java$');
  const args = ['-d', targetFolder, ...config.libs, ...files];
  const subprocess = child_process.spawn('javac', args, { stdio: 'inherit' });
  return once(subprocess, 'exit').then(([status]) => status);
};