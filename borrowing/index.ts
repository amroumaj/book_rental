const express = require ('express');
const borrowingRoutes = require('./src/borrowing/routes');
const pool = require('./db');

const port = 3002;

const app = express();

app.use(express.json());
app.use('/borrow', borrowingRoutes);






app.listen(port, () => {
    console.log(`Borrowing service listening on port ${port}`);
})