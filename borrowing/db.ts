const { Pool } = require ('pg');

const pool = new Pool ({
    user: 'admin',
    host: 'localhost',
    database: 'borrowing_db',
    password: 'admin123',
    port: 5444
});

module.exports = pool