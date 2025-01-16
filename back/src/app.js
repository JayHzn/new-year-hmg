import express from "express";
import bodyParser from "body-parser";
import connectToDatabase from "./config/database";
import userRoutes from "./routes/users";
import authRoutes from "./routes/auth";

const app = express();

// Middleware pour traiter les données JSON
app.use(bodyParser.json());

// Connexion à la base de données
connectToDatabase();

// Ajout des routes
app.use('/api/users', userRoutes);
app.use('/api/auth', authRoutes);

// Démarrage du serveur
const port = 3000;

app.listen(port, () => {
  console.log(`listening on ${port}`);
});

export default app;