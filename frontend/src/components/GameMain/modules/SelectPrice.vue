<template>
  <span class="price-box">
    <b>￦</b>
    <input
      class="price"
      type="text"
      dir="rtl"
      placeholder="Price" />
    <b> 만원</b>
  </span>
</template>

<script>
export default {
  data() {
    return {
      price : '',
    };
  },
  methods: {
    inputPrice(event) {
      this.price = event.target.value;
      console(event)
    }
  }
}
</script>

<style scoped>
.price-box {
    /* border: 1px solid black;*/
    width: 70%;
}
.price-box .price {
    font-size: 2rem;
    color : white;
    border : 3px solid red;
    background-color: #1f1f1f;
}
.price-box b {
    font-size: 2rem; 
    color : white;
}
</style>