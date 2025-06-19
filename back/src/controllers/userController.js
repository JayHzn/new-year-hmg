import bcrypt from "bcryptjs";
import User from "../models/user.js";

export const getAllUsers = async (req, res) => {
    try {
        const users = await User.find();
        res.json({ users, messages: "All users fetched successfully" });
    } catch (err) {
        res.status(500).json({ error: "Internal server error" });
    }
};

export const getUserById = async (req, res) => {
    try {
        const user = await User.findById(req.params.id);

        if (!user) {
            return res.status(404).json({ error: "User not found" });
        }
        res.json({ user, message: "User fetched successfully." });
    } catch (err) {
        res.status(500).json({ error: "Internal server error", err });
    }
};

export const createUser = async (req, res) => {
    const {
        firstName,
        lastName,
        username,
        email,
        password,
        bActive,
        bOrganizer = false,
        companyName = '',
    } = req.body;

    if (!username || !email || !password) {
        return res.status(400).json({
            error: "Username, email and password are required",
        });
    }

    try {
        // Génération d'un hash pour le MDP
        const saltRounds = 10;
        const hashedPassword = await bcrypt.hash(password, saltRounds)

        const newUser = new User({
            firstName: firstName,
            lastName: lastName,
            username: username,
            email: email,
            password: hashedPassword,
            bActive,
            bOrganizer: bOrganizer,
            companyName: bOrganizer ? companyName : null,
        });

        const savedUser = await newUser.save();

        const {password: _, ...userWithoutPassword } = savedUser.toJSON();
        res.status(201).json({
            user: userWithoutPassword,
            message: "User created successfully",
        });

    } catch (err) {
        console.error("Error creating user.", err);

        if (err.code === 11000) {
            const field = Object.keys(err.keyValue)[0];
            return res.status(409).json({
                error: `${field} already exists`
            });
        }

        res.status(500).json({
            error: "Internal server error",
        });
    }
};