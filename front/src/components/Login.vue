<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const form = ref({
    email: '',
    password: ''
})

const errors = ref({})
const isLoading = ref(false)
const router = useRouter()

function validateLogin() {
    errors.value = {}
    if (!form.value.email) errors.value.email = "L'email est requis."
    if (!form.value.password) errors.value.password = "Le mot de passe est requis."
    return Object.keys(errors.value).length === 0;
}

async function submitLogin() {
    if (!validateLogin()) return

    isLoading.value = true
    try {
        const res = await fetch(`${import.meta.env.VITE_API_URI}/api/auth/signin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(form.value)
        })
        if (!res.ok) {
            const err = await res.json()
            return alert(err.message || err.errors?.join('\n') || 'Échec de la connexion')
        }
        const { data } = await res.json()

        localStorage.setItem('accessToken', data.accessToken)
        localStorage.setItem('email', data.email)

        router.push({ name: 'Home' })
    } catch (error) {
        console.error(error)
        alert("Erreur lors de la connexion.")
    } finally {
        isLoading.value = false
    }
}
</script>

<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="bg-white p-8 shadow-lg rounded-lg w-96">
            <h2 class="text-x1 font-semibold text-center mb-4">Se connecter</h2>
            <form @submit.prevent="submitLogin">
                <div class="mb-4">
                    <label
                        v_model="form.email"
                        type="email"
                    ></label>
                </div>
            </form>
        </div>
    </div>
</template>