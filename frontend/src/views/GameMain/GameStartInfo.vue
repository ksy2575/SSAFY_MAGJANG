<template>
  <div class="row">
    <div class="col">
      <div> 
        인원 조건
      </div>
      <div> 
        능력 조건
      </div>
    </div>

    <!-- 거래 금액 -->
    <div class="col">
      <img
        src="@/assets/money.png"
        alt="money.png" />
      <div> {{ money }} 만원</div>
    </div>
          
    <!-- 설명 -->
    <div
      class="col">
      <div class="row">
        <div class="col">
          <GameText />
        </div>
        <div class="col">
          <GameTimer />
          <BaseTimer
            :time-left="timeLeft" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GameTimer from '@/components/GameMain/modules/GameTimer'
import GameText from '@/components/GameMain/modules/GameText'
import BaseTimer from '@/components/GameMain/modules/BaseTimer'


export default {
  components: {
    GameTimer,
    GameText,
    BaseTimer
  },
  data() {
    return {
      money : 2000,
      timeLimit: 30,
      timePassed: 0,
      timerInterval: null,
    };
  },
  computed: {
    timeLeft() {
      if (this.timeLimit - this.timePassed == 0) {
        this.skipTimer();
      }
      return this.timeLimit - this.timePassed
    },
    // formattedTimeLeft() {
    //   const timeLeft = this.timeLeft
			
    //   const minutes = Math.floor(timeLeft / 60)
			
    //   let seconds = timeLeft % 60
			
    //   if (seconds < 10) {
    //     seconds = `0${seconds}`
    //   }
			
    //   // The output in MM:SS format
    //   return `${minutes}:${seconds}`
    // }
  },
  methods: {
    startTimer() {
      this.timerInterval = setInterval(() => (this.timePassed += 1), 1000);
    },
    skipTimer() {
      this.timeLimit = 10,
      this.timePassed = 0,
      clearInterval(this.timerInterval);
      this.timerInterval = null;
      this.startTimer(); // 타이머 무한 반복하고 싶지 않을 때 주석하기
    }
  },
  mounted() {
    this.startTimer();
  },
}
</script>

<style>

</style>