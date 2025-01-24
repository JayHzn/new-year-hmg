import Express from "express";
import { getAllUsers, getUserById, createUser } from "../controllers/userController.js";

const router = Express.Router();

// Récupère tous les utilisateurs
router.get('/', async (req, res) => {
    await getAllUsers(req, res);
});

// Récupère un utilisateur avec un ID
router.get('/:id', async (req, res) => {
    await getUserById(req, res);
});

// Crée un nouvel utilisateur
router.post('/', async (req, res) => {
    await createUser(req, res);
});

export default router;