<template>
  <div id="game-main">
    <div class="container-fluid">
      <!-- gamePossible=ture일 때 금액 화면 -->
      <div class="nav-top">
        <GameRuleInfo />
        <div v-if="gamePossible">
          <UserPrice />
        </div>
      </div>
      <div class="video-container">
        <div
          id="join"
          v-if="!session">
          <div
            id="join-dialog"
            class="jumbotron vertical-center">
            <div class="form-group">
              <h1 style="color: white;">
                닉네임을 입력하세요
              </h1>
              <input
                v-model="myUserName"
                class="form-control"
                type="text"
                required />
              <h1 style="color: white;">
                방코드를 입력하세요
              </h1>
              <input
                v-model="mySessionId"
                class="form-control"
                type="text"
                required />
              <p class="text-center">
                <button
                  class="btn btn-lg btn-success"
                  @click="joinSession()">
                  Join!
                </button>
              </p>
            </div>
          </div>
        </div>
        <!-- 게임 대기 화면 -->
        <div
          class="row"
          id="session"
          v-if="session">
          <div class="game-layout col-10">
            <!-- user-video1 -->
            <div class="row user-video1 row-cols-3 g-2 g-lg-3">
              <div class="col">
                <!-- check! -->
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="publisher" 
                    @click="SelectPriceShow" />
                  <div v-if="selected">
                    <SelectPrice />
                  </div>
                  <UserAbility />
                </div>
              </div>
              <div
                class="col">
                <div
                  class="p-3 bg-light">
                  <!-- <user-video
                    v-for="sub in subscribers"
                    :key="sub.stream.connection.connectionId"
                    :stream-manager="sub"
                    @click="updateMainVideoStreamManager(sub)" /> -->
                  <user-video
                    :stream-manager="this.subscribers[3]" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[0]" />
                  <UserAbility />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="game-table-el">
                <div v-if="gamePossible">
                  <!-- gamePossible=ture일 때 게임 정보 테이블 화면 -->
                  <GameStartInfo />
                </div>
                <div v-else>
                  <!-- gamePossible=false일 때 대기화면 -->
                  <GameWatingBtns 
                    @gamePossible="gamestart"
                    @leaveSession="leaveSession()" />
                </div>
              </div>
            </div>
            <!-- user-video2 -->
            <div class="row user-video2 row-cols-3 g-2 g-lg-3">
              <div
                class="col">
                <div class="p-3 bg-light first">
                  <user-video
                    :stream-manager="this.subscribers[1]" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[4]" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[2]" />
                  <UserAbility />
                </div>
              </div>
            </div>
          </div>
          <div class="col-2">
            <div class="row">
              <div class="game-log">
                게임로그
              </div>
            </div>
            <div class="row">
              <div class="game-chatting">
                게임채팅
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserPrice from '@/components/GameMain/modules/UserPrice.vue'
import GameWatingBtns from '@/components/GameMain/modules/GameWatingBtns.vue'
import GameStartInfo from './GameStartInfo.vue'
import GameRuleInfo from '@/components/GameMain/modules/GameRuleInfo.vue'
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'
import SelectPrice from '@/components/GameMain/modules/SelectPrice.vue'

import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/GameMain/layouts/UserVideo'; 


axios.defaults.headers.post['Content-Type'] = 'application/json';

//const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_URL = "https://i6b208.p.ssafy.io:5443";
const OPENVIDU_SERVER_SECRET = "ssafy";

export default {
  components: {
    GameWatingBtns,
    GameStartInfo,
    UserPrice,
    GameRuleInfo,
    UserVideo,
    UserAbility,
    SelectPrice,
  },

  data(){
    return{
      gamePossible: false,
      selected: false,

      // openVidu data
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
      mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
    
      // remove List
      removeList: [],

    }
  },
  methods: {
    gamestart() {
      this.gamePossible=true;
    },
    SelectPriceShow() {
      this.selected = !this.selected;
    },
    //// 오픈비두 ////
    joinSession() {
      // --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

      // --- Init a session ---
			this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // 새로운 player 입장
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});
      console.log(this.session)
      console.log('HI')
      console.log(this.subscribers)

      // On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
        this.removeList.push(index);
			});

      // On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
      
      // --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '320x240',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

    leaveSession () {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener('beforeunload', this.leaveSession);
		},

    updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},


		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
  }
}
</script>

<style>
:root {
  height: 100%;
  overflow: hidden;
  background-color: #1f1f1f;
}

#game-main {
  width: 100%;
  background-color: #1f1f1f;
}

.nav-top {
  height: 40px;
}

.game-layout {
  position: relative;
}

.game-layout .game-table-el {
  display: block;
  position: absolute;
  width: 80%;
  height: 35vh;
  margin: 0 auto;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgb(180, 180, 180);
  font-size: 30px;
  border-radius: 20px;
}
.game-layout .user-video1 {
  position: relative;
  top: 10px;
}

.game-layout .user-video1 .col .p-3 {
  height: 25vh;
  border-radius: 20px;
  border: solid black 2px;
}

.game-layout .user-video1 .col .p-3:hover {
  cursor: pointer;
  border-color: tomato;
}

.game-layout .user-video2 {
  position: relative;
  top: 40vh;
}

.game-layout .user-video2 .col .p-3 {
  height: 25vh;
  border-radius: 20px;
}

.game-log {
  width: 100%;
  height: 45vh;
  margin-right: 20px;
  background-color: black;
  margin-bottom: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

.game-chatting {
  width: 100%;
  height: 45vh;
  background-color: rgb(42, 106, 165);
  margin-top: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

</style>