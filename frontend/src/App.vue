<template>
  <div class="container d-flex justify-content-center my-3">
    <NaverLoginButtonComponent/>
  </div>
</template>

<script>
import NaverLoginButtonComponent from './components/mag-jang/NaverLoginButtonComponent.vue'
import "bootstrap/dist/css/bootstrap.min.css";

export default {
  name: 'App',
  components: {
    NaverLoginButtonComponent,
  }
}
</script>

<style>

</style>
