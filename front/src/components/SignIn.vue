<script setup>
import { ref } from 'vue';

const form = ref ({
    firstname: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
});

const errors = ref({});
const isLoading = ref(false);

const validateForm = () => {
    errors.value = {};
    if (!form.value.firstname) errors.value.firstname = "Le prénom est requis.";
    if (!form.value.lastname) errors.value.lastname = "Le nom est requis.";
    if (!form.value.username) errors.value.username = "Le nom d'utilisateur est requis.";
    if (!form.value.email.includes('@')) errors.value.email = "Email invalide.";
    if (form.value.password.length < 6) errors.value.password = "Mot de passe trop court";
    if (form.value.password !== form.value.confirmPassword) {
        errors.value.confirmPassword = "Les mots de passe ne correspondent pas.";
    }
    return Object.keys(errors.value).length === 0;
};

const submitForm = async () => {
    if (!validateForm()) return;

    isLoading.value = true;
    try {
        const response = await fetch('/api/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(form.value),
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'inscription");
        }

        const data = await response.json();
        alert('Inscription réussie !');
        console.log(data);
    } catch(err) {
        alert("Erreur d'incription.");
        console.error(err);
    } finally {
        isLoading.value = false;
    }
};
</script>

<template>
    <form @submit.prevent="submitForm" class="p-4 bg-white shadow-md rounded">
        <label class="block mb-2">Prénom :</label>
        <input v-model="form.firstname" type="text" class="w-full p-2 border rounded" />
        <p v-if="errors.firstname" class="text-red-500 text-sm">{{ errors.firstname }}</p>

        <label class="block mt-4 mb-2">Nom :</label>
        <input v-model="form.lastname" type="text" class="w-full p-2 border rounded" />
        <p v-if="errors.lastname" class="text-red-500 text-sm">{{ errors.lastname }}</p>

        <label class="block mt-4 mb-2">Nom d'utilisateur :</label>
        <input v-model="form.username" type="text" class="w-full p-2 border rounded" />
        <p v-if="errors.username" class="text-red-500 text-sm">{{ errors.username }}</p>

        <label class="block mt-4 mb-2">Email :</label>
        <input v-model="form.email" type="email" class="w-full p-2 border rounded" />
        <p v-if="errors.email" class="text-red-500 text-sm">{{ errors.email }}</p>

        <label class="block mt-4 mb-2">Mot de passe :</label>
        <input v-model="form.password" type="password" class="w-full p-2 border rounded" />
        <p v-if="errors.password" class="text-red-500 text-sm">{{ errors.password }}</p>

        <label class="block mt-4 mb-2">Confirmer mot de passe :</label>
        <input v-model="form.confirmPassword" type="text" class="w-full p-2 border rounded" />
        <p v-if="errors.confirmPassword" class="text-red-500 text-sm">{{ errors.confirmPassword }}</p>

        <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded mt-4">
            {{ isLoading ? 'Inscription...' : "S'inscrire" }}
        </button>
    </form>
</template>

<style scoped>

</style>