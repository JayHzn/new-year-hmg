import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";
import User from "../models/user.js";

export const signUp = async (req, res) => {
    try {
        const {
            firstname,
            lastname,
            username,
            email,
            password,
            bOrganizer = false,
            companyName = '',
        } = req.body;

        const existingUser = await User.findOne({ email });

        if (existingUser) {
            return res.status(400).json({
                success: false,
                message: "User already exists with this email",
            });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = new User({
            firstName: firstname,
            lastName: lastname,
            username: username,
            email: email,
            password: hashedPassword,
            bOrganizer: bOrganizer,
            companyName: bOrganizer ? companyName : undefined,
        });

        await newUser.save();

        const token = jwt.sign(
            { userId: newUser._id, email: newUser.email },
            process.env.JWT_SECRET,
            { expiresIn: "3h" }
        );

        return res.status(201).json({
            success: true,
            message: "User registered successfully",
            token,
        });
    } catch (e) {
        console.error("signUp error:", e);
        if (e.name === "ValidationError") {
            const messages = Object.values(e.errors).map((e) => e.message);
            return res.status(400).json({ success: false, errors: messages });
        }

        return res.status(500).json({
            success: false,
            message: "Registration failed, something went wrong",
        });
    }
};

export const signIn = async (req, res) => {
    try {
        const { username, password } = req.body;

        const user = await User.findOne({ username });

        if (!user) {
            return res.status(400).json({
                success: false,
                message: "User not found"
            });
        }
        
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(401).json({ error: "Invalid credentials" });
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