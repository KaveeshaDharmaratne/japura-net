<template>
    <div>
      <component 
        :is="currentComponent" 
        @switch-to-signup="toggleComponent('Signup')"
        @switch-to-login="toggleComponent('Login')"
        ></component>    
    </div>
  </template>
  
  <script>
  import Login from '../components/Login.vue';
  import Signup from '../components/Signup.vue';
  
  export default {
    name: 'AuthView',
    components: {
      Login,
      Signup,
    },
    data() {
      return {
        currentComponent: 'Login', // Default to Login component
      };
    },
    methods: {
      toggleComponent(componentName) {
        this.currentComponent = componentName;
      },
    },
  };
  </script>
  