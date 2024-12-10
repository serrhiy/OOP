'use strict';

const path = require('node:path');
const { once } = require('node:events');
const child_process = require('node:child_process');

module.exports = (config) => {
  const { target, mainProject, mainFile, libs } = config;
  return (project = mainProject) => {
    const fullpath = path.join(target, project);
    const args = ['-cp', fullpath, ...libs, mainFile];
    const process = child_process.spawn('java', args);
    return once(process, 'spawn').then(() => process);
  };
};
