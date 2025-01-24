import mongoose from 'mongoose';

const userSchema = new mongoose.Schema({
    firstName: {
        type: String,
        required: false,
        minlength: 2,
    },
    lastName: {
        type: String,
        required: false,
        minlength: 2,
    },
    username: {
        type: String,
        required: true,
        unique: true,
        minlength: 3,
    },
    email: {
        type: String,
        required: true,
        unique: true,
        match: [/.+\@.+\..+/, "Please enter a valid email address"]
    },
    password: {
        type: String,
        required: true,
        minlength: 6,
    },
    bActive: {
        type: Boolean,
        default: true,
    },
    bOrganizer: {
        type: Boolean,
        default: false,
    },
    createdAt: {
        type: Date,
        default: Date.now,
    },
});

const User = mongoose.model('user', userSchema);

export default User;