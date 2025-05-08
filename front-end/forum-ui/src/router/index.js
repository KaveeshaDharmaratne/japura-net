import { createRouter, createWebHistory } from 'vue-router'
import { getAuth, onAuthStateChanged } from 'firebase/auth'
import AuthView from '../views/AuthView.vue'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'AuthView',
    component: AuthView
  },
  {
    path: '/home',
    name: 'HomeView',
    component: HomeView,
    meta: {
      requiresAuth: true // This route requires authentication
    }
  },
 
];

const getCurrentUser = () => {
  return new Promise((resolve, reject) => {
    const removeListener = onAuthStateChanged(
      getAuth(),
    (user) => {
      removeListener();
      resolve(user);
    },
  reject);
  });
}

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const hideForAuth = to.matched.some((record) => record.meta.hideForAuth); // Check for hideForAuth meta
  const user = await getCurrentUser(); // Check auth state once

  if (requiresAuth && !user) {
      // Route requires auth, but user is not logged in
      alert("You need to be logged in to access this page.");
      next("/"); // Redirect to login page
  } else if (hideForAuth && user) {
      // Route should be hidden for authenticated users (like login/register)
      next("/home"); // Redirect logged-in user to home
  } else {
      // No specific auth requirements met, or requirements are met
      next(); // Proceed to the route
  }
});

export default router
