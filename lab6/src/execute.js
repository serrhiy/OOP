'use strict';

const path = require('node:path');
const child_process = require('node:child_process');

module.exports = (config) => {
  const { target, mainProject, mainFile, libs } = config;
  return (project = mainProject) => {
    const fullpath = path.join(target, project);
    const args = ['-cp', fullpath, ...libs, mainFile];
    return child_process.spawn('java', args);
  };
};
