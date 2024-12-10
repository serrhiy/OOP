'use strict';

const path = require('node:path');
const fsp = require('node:fs/promises');

module.exports = (config, exists) => {
  const { programsFolder, target, resourcesFolder } = config;
  return async (programName) => {
    const oldpath = path.join(programsFolder, programName, resourcesFolder);
    const newpath = path.join(target, programName, resourcesFolder);
    const present = await exists(oldpath);
    if (!present) return;
    return fsp.cp(oldpath, newpath, { recursive: true, force: true });
  };
};
