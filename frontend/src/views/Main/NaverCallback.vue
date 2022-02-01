<template>
  <div></div>
</template>

<script>
import { mapActions } from 'vuex'
import axios from 'axios'

export default {
  mounted () {
    var naver_id_login = new window.naver_id_login("XcnVRW5TXoLW3FDEkiUJ", "http://localhost:8083/navercallback");
    console.log(naver_id_login.oauthParams.access_token);
    
    // this.setUser({
    //   "user": 1,
    //   "nickname": "김주호",
    //   "score": 1000,
    // })

    this.test();

    // 토큰이 있든 없든, 루트 페이지로 이동한다.
    this.$router.replace('/')
  },
  methods: {  // vue 공부 빡쎄게 해서 제대로 쓰기
    ...mapActions([
      'setUser'
    ]),
    test() {
      axios.get('/api/test/')
        .then(response => {
          console.log(response);
        })
        .catch(error =>{
          console.log("error:" + error)
        });
    }
  }
}
</script>