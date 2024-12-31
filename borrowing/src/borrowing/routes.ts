import { Router } from 'express';
import controller from './controller.ts';

const router = Router();

router.get('/', controller.getAllRecords);
router.get('/history/:user_id', controller.getUserHistory);


router.post('/borrow', controller.borrowBook);
router.post('/reserve', controller.reserveBook);

router.put('/return/:book_id', controller.returnBook);

export default router;