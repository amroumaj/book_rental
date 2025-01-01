const borrowBook = "INSERT INTO borrowing (user_id, book_id, due_date) VALUES ($1, $2, CURRENT_TIMESTAMP + INTERVAL '14 days');";
const reserveBook = 'INSERT INTO borrowing (user_id, book_id, borrow_date, due_date, returned) VALUES ($1, $2, NULL, NULL, FALSE);';

const getUserHistory = 'SELECT b.id, b.book_id, bk.title, b.borrow_date, b.due_date, b.returned FROM borrowing b JOIN books bk ON b.book_id = bk.id WHERE b.user_id = $1 ORDER BY b.borrow_date DESC;';
const getAllRecords = 'SELECT * FROM borrowing;';

const returnBook = 'UPDATE borrowing SET returned = TRUE WHERE book_id = $1 AND returned = FALSE;';

export default {
    borrowBook,
    reserveBook,
    getUserHistory,
    getAllRecords,
    returnBook
}