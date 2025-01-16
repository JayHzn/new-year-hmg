import Express from "express";
import { signIn, signUp } from "../controllers/authController";

const router = Express.Router();

router.post('/signup', async(req, res) => {
    await signUp(req, res);
});

router.post('/signin', async(req, res) => {
    await signIn(req, res);
});

export default router;