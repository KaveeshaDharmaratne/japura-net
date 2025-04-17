<template>
    <div>
      <div class="topBar">

      </div>
      <div class="auth-container">
        <component 
          :is="currentComponent" 
          @switch-to-signup="toggleComponent('Signup')"
          @switch-to-login="toggleComponent('Login')"
          ></component>    
      </div>
    </div>
  </template>
  
  <script>
  import Login from '../components/Auth/Login.vue';
  import Signup from '../components/Auth/Signup.vue';
  
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
  
<style>
  .topBar {
    height: 1vh;
    background: #C3262D;
    background: linear-gradient(90deg,rgba(195, 38, 45, 1) 0%, rgba(255, 245, 0, 1) 25%, rgba(195, 38, 45, 1) 75%, rgba(255, 245, 0, 1) 100%);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
}
.auth-container {
    
    margin-top: 30px;

  }
</style>