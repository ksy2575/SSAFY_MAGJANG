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
      @click="[leaveSession(), goBack()]">
      메인화면으로
    </button>
  </div>
  <!---->
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
    },
    goBack() {
      this.$router.push({ name: 'Game' })
      console.log('goBack')
    },
    leaveSession() {
      this.$emit('leave-session')
    }
  }
}

</script>

<style>
.button-flex {
  display: flex;
  justify-content: space-evenly;
  margin-top: 6vh;
}


</style>