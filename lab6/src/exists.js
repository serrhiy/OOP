'use strict';

const fsp = require('node:fs/promises');

module.exports = (file) => fsp.access(file).then(() => true, () => false);
