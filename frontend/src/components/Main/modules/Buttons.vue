<template>
  <div>
    <button class="mj-btn">
      {{ btnText }}
    </button>
  </div>
</template>

<script>
export default {

  props: {
    btnText: {
      type: String,
      default: "default"
    },
  },

}
</script>

<style scoped>

.mj-btn {
  width: 30vw;
  padding: 10px;
  border: 2px solid #333;
  border-radius: 4px;
  color: #333;
  font-weight: 700;
  font-size: 20px;
  text-align: center;
  cursor: pointer;
  box-sizing: border-box;
  display: block;
  transition: .4s;
  margin: 0 auto;
  margin-top: 20px;
  background-color: #333;
  color: #fff;

}
.mj-btn:hover {
  background-color: rgb(223, 223, 223);
  color: #333;
}

</style>