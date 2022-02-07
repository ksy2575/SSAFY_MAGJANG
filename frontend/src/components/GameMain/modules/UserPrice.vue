<template>
  <span class="price-box">
    <b>￦</b>
    <input
      class="price"
      type="text"
      dir="rtl"
      placeholder="Price"
      v-model="price"
      readonly />
    <b> 만원</b>
  </span>
</template>

<script>
export default {
  data() {
    return {
      price : 7000,
    };
  },
}
</script>

<style scoped>
.price-box {
    /* border: 1px solid black;*/
    width: 200px;
}
.price-box .price {
    font-size: 2rem;
    color : white;
    border : none;
    background-color: #1f1f1f;
}
.price-box b {
    font-size: 2rem; 
    color : white;
}
</style>