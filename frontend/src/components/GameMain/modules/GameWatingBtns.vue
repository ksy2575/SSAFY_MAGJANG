<template>
  <div
    class="button-flex">
    <button
      type="button"
      class="btn mj-btn"
      disabled>
      게임 코드 <hr />
      {{ gameCode }}
    </button>
    <button
      type="button"
      class="btn mj-btn"
      :disabled="userCount < 4"
      @click="gamePossible">
      게임시작
    </button>
    <button
      type="button"
      class="btn mj-btn"
      @click=" this.$router.push({ name: 'Home' })">
      메인화면으로
    </button>
  </div>
  <div></div>
</template>

<script>

export default {
  data() {
    return {
      gameStartFlag: false,
      gameCode: '2D3201',
      userCount: 4,
    }
  },
  methods: {
    gamePossible(){
      this.$emit('game-possible')
    }
  }
}

</script>

<style>
.button-flex {
  display: flex;
  justify-content: space-evenly;
<<<<<<< HEAD:frontend/src/components/GameMain/modules/GameWatingBtns.vue
  margin-top: 50px;
=======
  margin-top: 6vh;
>>>>>>> FE-game-main:src/components/GameMain/modules/GameWatingBtns.vue
}


</style>