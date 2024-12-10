'use strict';

const path = require('node:path');
const fsp = require('node:fs/promises');

const find = async (folder, pattern) => {
  const result = new Set();
  const files = await fsp.readdir(folder, { withFileTypes: true });
  for (const file of files) {
    const { name: filename } = file;
    const fullpath = path.join(folder, filename);    
    if (file.isDirectory()) {
      const subset = await find(fullpath, pattern);
      for (const file of subset) result.add(file);
      continue;
    }
    const match = filename.match(pattern);
    if (match) result.add(fullpath);
  }
  return result;
};

module.exports = find;
