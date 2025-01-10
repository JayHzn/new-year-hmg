import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { User } from "../models/user";

export const signUp = async (req, res) => {
    try {
        const { username, email, password } = req.body;

        const existingUser = await User.findOne({ where: { email } });

        if (existingUser) {
            return res.status(400).json({
                success: false,
                message: "User already exists with this email",
            });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = new User({
            firstName,
            lastName,
            username,
            email,
            password: hashedPassword,
            bOrganizer,
        });

        await newUser.save();

        const token = jwt.sign(
            { userId: newUser.id, email: newUser.email },
            process.env.JWT_SECRET,
            { expiresIn: "3h" }
        );

        return res.status(201).json({
            success: true,
            message: "User registered successfully",
            token,
        });
    } catch (e) {
        return res.status(500).json({
            success: false,
            message: "Registration failed, something went wrong",
        });
    }
};

export const signIn = async (req, res) => {
    try {
        const { email, password } = req.body;

        const user = await User.findOne({ where: { email } });

        if (!user) {
            return res.status(400).json({
                success: false,
                message: "User not find"
            });
        }
        if  (!user.password) {
            return res.status(404).json({
                success: false,
                message: "Incorrect password"
            });
        }

        const token = jwt.sign({ userId: user.id },
            process.env.JWT_SECRET,
           { expiresIn: "3h" },
        );

        return res.status(200).json({
            success: true,
            message: "User logged in successfully",
            data: {
                userId: user.id,
                email: user.email,
                username: user.username,
                accessToken: token,
            },
        });
    } catch (e) {
        return res.status(500).json({
            success: false,
            message: "Login failed, something went wrong",
        });
    }
};