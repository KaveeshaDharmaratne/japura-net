<template>
    <div>
        <div class="banner">
            <div class="logo-container">
                <img :src="getImageUrl('sjp-logo.jpg')" alt="University Logo" class="logo" />
            </div>
            <div class="title">
                <h1>Japura Net Forum</h1>
                <p>For the students, by the students</p>
            </div>
        </div>
    
        <form  @submit.prevent="handleLogin">
    
            <label>Email </label>
            <input 
            type="email"
            v-model="email"
            placeholder="Enter your university email"
            required 
            >
            <div v-if="errors.email" class="error">{{ errors.email }}</div>
     
            <label>Password </label>
            <input 
            type="password" 
            v-model="password"
            placeholder="Enter your password"
            required 
            >
            <div v-if="errors.password" class="error">{{ errors.password }}</div>
    
            <div class="login">
                <button type="submit">Login</button>
            </div>
    
            <div class="signup-section">
                <p>Don't have an account?</p>
                <a @click="goToSignUp" href="#">Sign up</a>
            </div>
        </form>
    </div>
</template>

<script>
import DoValidations from '../services/DoValidations';

export default {
    data() {
        return {
            email: '',
            password: '',
            getImageUrl(imageName) {
                return require('../assets/sjp-logo.jpg')
            },
            errors: '',
            
        }
    }, 
    methods: {
        handleLogin() {
            let validations = new DoValidations(
                this.email,
                this.password
            );

            this.errors = validations.checkValidations();
            if (this.errors.length) {
                return false    
            }
        },
        goToSignUp() {
            this.$emit('switch-to-signup');
        },
    }
}
</script>

<style>

form {
    max-width: 420px;
    margin: 30px auto;
    background: white;
    text-align: left;
    padding: 40px;
    border-radius: 10px;
}

label {
    color: #aaa;
    display: inline-block;
    margin: 25px 0 15px;
    font-size: 0.8em;
    text-transform: uppercase;
    letter-spacing: 1px;
    font-weight: bold;
}

input, select {
    display: block;
    padding: 10px 6px;
    width: 100%;
    box-sizing: border-box;
    border: none;
    border-bottom: 1px solid #ddd;
    color: #555;
}

.banner {
    display: flex;
    max-width: 420px;
    margin: 10px auto;
    align-items: flex-start;
    height: 80px;

}

.title {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding-left: 5px;
    justify-content: space-between;
    align-items: flex-start;
    border-left-width: 2px;
    border-left-color: #ddd;
    border-left-style: solid;
}

.title h1 {
    margin: 0;
    color: #555;
}

.title p { 
    font-size: small;
    text-transform: uppercase;
    color: #aaa;
    margin: auto;
}

.logo-container {
    padding: 0 5px 5px 0;
    max-width: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    
}

.logo {
    width: 100%;
    object-fit: cover;
}

button {
    background:#c3262d;
    border: 0;
    padding: 10px 20px;
    margin-top: 20px;
    color: white;
    border-radius: 20px;
    cursor: pointer;
}
.login {
    text-align: center;
}
.signup-section {
    margin-top: 30px;
    text-align: left;
}
.signup-section p {
    color: #555;
}
.signup-section a {
    color: #c3262d;
    text-decoration: none;
    font-weight: bold;
}
.error {
    color: #ff0062;
    margin-top: 10px;
    font-size: 0.8em;
    font-weight: bold;
}

</style>