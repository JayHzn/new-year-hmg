import mongoose from "mongoose";

const MONGO_URI = process.env.MONGO_URI;

const connectToDatabase = async() => {
    try {
        await mongoose.connect(MONGO_URI, {
            useNewUrlParser: true,
            useUnifiedTopology: true
        });
        console.log("Connected to database");
    } catch (err) {
        console.error("Error connecting to database", err);
        process.exit(1);
    }
};

export default connectToDatabase;