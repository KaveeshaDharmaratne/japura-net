import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { initializeApp } from "firebase/app";
import { ApolloClient, createHttpLink, InMemoryCache } from '@apollo/client/core'

// HTTP connection to the API
const httpLink = createHttpLink({
  // You should use an absolute URL here
  uri: 'http://localhost:3020/graphql',
})

// Cache implementation
const cache = new InMemoryCache()

// Create the apollo client
const apolloClient = new ApolloClient({
  link: httpLink,
  cache,
})

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
