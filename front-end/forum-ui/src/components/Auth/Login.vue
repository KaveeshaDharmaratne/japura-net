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

        <form @submit.prevent="handleLogin">

            <label>Email </label>
            <input type="email" v-model="email" placeholder="Enter your university email" required>
            <div v-if="errors.email" class="error">{{ errors.email }}</div>

            <label>Password </label>
            <input type="password" v-model="password" placeholder="Enter your password" required>
            <div v-if="errors.password" class="error">{{ errors.password }}</div>

            <div class="login">
                <button class="login-button" type="submit">Login</button>
                <button @click="signInWithGoogle" class="google-button login-button" type="button">
                    Sign In With Google
                </button>
            </div>

            <div class="signup-section">
                <div class="signup-text">
                    <p>Don't have an account?</p>
                </div>
                <div class="signup-link">
                    <a @click="goToSignUp" href="#">Sign up</a>
                </div>
            </div>
        </form>
        <p v-if="errMsg" class="error">{{ errMsg }}</p>
    </div>
</template>

<script>
import { ref } from 'vue';
import DoValidations from '../../services/DoValidations';
import { getAuth, signInWithEmailAndPassword, GoogleAuthProvider, signInWithPopup } from 'firebase/auth';

export default {
    data() {
        return {
            email: '',
            password: '',
            getImageUrl(imageName) {
                return require(`@/assets/${imageName}`);
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

            const userData = {
                email: this.email,
                password: this.password
            };

            const auth = getAuth();
            const errMsg = ref();

            signInWithEmailAndPassword(auth, userData.email, userData.password)
                .then((data) => {
                    // Signed in 
                    console.log("Successfully logged in!");
                    console.log(auth.currentUser);
                    // Redirect to home page after successful login
                    this.$router.push('/home');
                })
                .catch((error) => {
                    switch (error.code) {
                        case 'auth/invalid-email':
                            errMsg.value = 'Invalid email address.';
                            break;
                        case 'auth/user-not-found':
                            errMsg.value = 'No user found with this email.';
                            break;
                        case 'auth/wrong-password':
                            errMsg.value = 'Incorrect password.';
                            break;
                        default:
                            errMsg.value = 'Email or password was incorrect.';
                            break;
                    }
                });
        },
        goToSignUp() {
            this.$emit('switch-to-signup');
        },
        signInWithGoogle() {
            const provider = new GoogleAuthProvider();
            signInWithPopup(getAuth(), provider)
                .then((result) => {
                    console.log(result.user);
                    this.$router.push('/home');  // Redirect to home page after successful login
                })
                .catch((error) => {
                    console.error(error.code);
                    alert(error.code);
                });
        }
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

input,
select {
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
    background: #c3262d;
    border: 0;
    padding: 10px 20px;
    margin-top: 20px;
    color: white;
    border-radius: 20px;
    cursor: pointer;
    
}

.login-button {
    text-transform: uppercase;
    width: 100%;
    font-weight: 700;
    height: 40px;
}

.login {
    display: flex;
    margin-top: 20px;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
}

.google-button {
    display: flex;
    align-items: center;
    justify-content: center;
    color: rgb(65, 63, 63);
    background-color: #fff;
    border: 1px solid rgba(0, 0, 0, 0.25);
}

.google-button::before {
    content: '';
    display: inline-block;
    width: 20px;
    height: 20px;
    margin-right: 10px;
    background: url('https://www.gstatic.com/marketing-cms/assets/images/d5/dc/cfe9ce8b4425b410b49b7f2dd3f3/g.webp=s48-fcrop64=1,00000000ffffffff-rw') no-repeat center;
    background-size: contain;
}

.signup-section {
    display: flex;
    flex-direction: row;
    justify-content: center;
    gap: 5px;
    align-items: center;
    margin-top: 30px;

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