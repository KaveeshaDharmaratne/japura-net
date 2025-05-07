import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyBZldRkdvtkBiHC0oSwH5bTcY5Otm-7_h8",
  authDomain: "japura-net.firebaseapp.com",
  databaseURL: "https://japura-net-default-rtdb.firebaseio.com",
  projectId: "japura-net",
  storageBucket: "japura-net.firebasestorage.app",
  messagingSenderId: "667540554589",
  appId: "1:667540554589:web:6c2982d76b7b989b3adeb4",
  measurementId: "G-TZPZ7RK21R"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

createApp(App).use(router).mount('#app')
