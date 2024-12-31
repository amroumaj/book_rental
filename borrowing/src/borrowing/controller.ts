import pool from '../../db';
import queries from './queries';
import type { Request, Response } from 'express';

const getAllRecords = async (req: Request, res: Response) => {
    try {
        const data = await pool.query(queries.getAllRecords)
        res.status(200).send(data.rows)
    } catch (err) {
        console.log(err)
        res.sendStatus(500);
    }
}

const getUserHistory = async (req: Request, res: Response) => {
    try {
        const data = await pool.query(queries.getUserHistory)
        res.status(200).send(data.rows)
    } catch (err) {
        console.log(err)
        res.sendStatus(500)
    }
}

const borrowBook = async (req: Request, res: Response) => {
    const { user_id, book_id, due_date } = req.body

    try {
        await pool.query(queries.borrowBook, [user_id, book_id, due_date])
        res.status(200).send("Successfully booked")
    } catch (err) {
        console.log(err)
        res.sendStatus(500)
    }
}

const reserveBook = async (req: Request, res: Response) => {
    const { user_id, book_id, borrow_date, due_date, returned } = req.body

    try {
        await pool.query(queries.reserveBook, [user_id, book_id, borrow_date, due_date, returned])
        res.status(200).send("Successfully reserved")
    } catch (err) {
        console.log(err)
        res.sendStatus(500)
    }
}

const returnBook = async (req: Request, res: Response) => {
    const id = req.params.book_id

    try {
        await pool.query(queries.returnBook, [id])
        res.status(200).send(`Book returned ${id}`)
    } catch (err) {
        console.log(500)
        res.sendStatus(500)
    }
}

export default {
    borrowBook,
    reserveBook,
    getUserHistory,
    getAllRecords,
    returnBook
}