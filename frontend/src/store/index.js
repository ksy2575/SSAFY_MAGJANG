import { createStore } from 'vuex'

export default createStore({
  state: {
    user: null,  // DB에서 유저 키값
    nickname: null,
    score: null,
  },
  getters: {  // stage 상태 가져오기
    user: state => state.user,
    nickname: state => state.nickname,
    score: state => state.score,
    isLogined: function(state){
      if (state.user && state.nickname)
        return true
      return false
    }
  },
  mutations: {  // state 상태 변경, 동기적이어야 함
    SET_USER: function (state, userdata) {
      state.user = userdata["user"]
      state.nickname = userdata["nickname"]
      state.score = userdata["score"]
      console.log("user:" + state.user, "\nnickname: " + state.nickname, "\nscore: " + state.score)
    },
  },
  actions: {  // mutations 호출, 비동기 가능
    setUser: function ({commit}, user, nickname, score) {
      commit('SET_USER', user, nickname, score)
    }
  },
  modules: {
  }
})
