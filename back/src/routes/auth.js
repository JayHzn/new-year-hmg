import Express from "express";
import { signIn, signUp } from "../controllers/authController.js";

const router = Express.Router();

// Création d'un user
router.post('/signup', async(req, res) => {
    await signUp(req, res);
});

// Connexion d'un user
router.post('/signin', async(req, res) => {
    await signIn(req, res);
});

export default router;