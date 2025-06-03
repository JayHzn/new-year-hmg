import 'dotenv/config';
import express from "express";
import cors from "cors";
import bodyParser from "body-parser";
import connectToDatabase from "../src/config/database.js";
import userRoutes from "../src/routes/users.js";
import authRoutes from "../src/routes/auth.js";

const app = express();

app.use(cors());

// Middleware pour traiter les données JSON
app.use(bodyParser.json());

// Connexion à la base de données
connectToDatabase();

// Ajout des routes
app.use('/api/user', userRoutes);
app.use('/api/auth', authRoutes);

// Démarrage du serveur
const port = 3000;

app.listen(port, () => {
  console.log(`listening on ${port}`);
});

export default app;