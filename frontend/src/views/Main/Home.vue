<template>
  <div
    id="main"
    style="overflow: hidden">
    <div class="container">
      <MainTheme />
      <MainText />
      <IsLogined />
    </div>
  </div>
</template>

<script>
import MainTheme from '@/components/Main/modules/MainTheme.vue'
import MainText from '@/components/Main/modules/MainText.vue'
import IsLogined from '@/components/Main/layouts/IsLogined.vue'

export default {
  components: { 
    MainTheme,
    MainText,
    IsLogined, 
  },
}

</script>


<style>
  :root {
    height: 100%;
    overflow: hidden;
    background-color: #1f1f1f;
  }
 #main {
<<<<<<< HEAD:frontend/src/views/Main/Home.vue
   position: relative;
   height: 100vh;
=======
   height: 100vh;
   max-height: 100vh;
>>>>>>> FE-game-main:src/views/Main/Home.vue
   background-image: url('/src/assets/money_bg1.jpg');
   background-repeat: no-repeat;
   background-size: 100%; 
   background-attachment: fixed;
<<<<<<< HEAD:frontend/src/views/Main/Home.vue

 } 
   #main::before {
    content: '';
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #000;
=======
   background-blend-mode: overlay;
   background-color: rgba(0, 0, 0, 0.5);
>>>>>>> FE-game-main:src/views/Main/Home.vue
 }

</style>
