<template>
  <div id="game-main">
    <div class="container-fluid">
      <!-- gamePossible=ture일 때 금액 화면 -->
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
      <div class="row">
        <div class="nav-top col">
          <div v-if="gamePossible">
            <InputPrice />
          </div>
          <div v-else>
            <GameRuleInfo />
          </div>
        </div>
        <!-- 게임 대기 화면 -->
        <div class="row">
          <div class="game-table-layout col-10">
            <div class="row ">
              <div class="game-table-el">
                <div v-if="gamePossible">
                  <!-- gamePossible=ture일 때 게임 정보 테이블 화면 -->
                  <GameStartInfo />
                </div>
                <div v-else>
                  <!-- gamePossible=false일 때 대기화면 -->
                  <GameWatingBtns 
                    @gamePossible="gamestart" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-2">
            <div class="row">
              <div class="game-log">
                게임로그
=======
      <div class="nav-top">
        <GameRuleInfo />
        <div v-if="gamePossible">
          <InputPrice />
        </div>
      </div>
      <div class="video-container">
        <div
          id="join"
          v-if="!session">
          <div id="img-div">
            <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
          </div>
          <div
            id="join-dialog"
            class="jumbotron vertical-center">
            <h1>Join a video session</h1>
            <div class="form-group">
              <p>
                <label>Participant</label>
                <input
                  v-model="myUserName"
                  class="form-control"
                  type="text"
                  required />
              </p>
              <p>
                <label>Session</label>
                <input
                  v-model="mySessionId"
                  class="form-control"
                  type="text"
                  required />
              </p>
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
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="publisher"
                    @click="updateMainVideoStreamManager(publisher)" />
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
                    :stream-manager="this.subscribers[3]"
                    @click="updateMainVideoStreamManager(sub)" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[0]"
                    @click="updateMainVideoStreamManager(sub)" />
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
                    @gamePossible="gamestart" />
                </div>
              </div>
            </div>
            <!-- user-video2 -->
            <div class="row user-video2 row-cols-3 g-2 g-lg-3">
              <div
                class="col">
                <div class="p-3 bg-light first">
                  <user-video
                    :stream-manager="this.subscribers[1]"
                    @click="updateMainVideoStreamManager(sub)" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[4]"
                    @click="updateMainVideoStreamManager(sub)" />
                  <UserAbility />
                </div>
              </div>
              <div class="col">
                <div class="p-3 bg-light">
                  <user-video
                    :stream-manager="this.subscribers[2]"
                    @click="updateMainVideoStreamManager(sub)" />
                  <UserAbility />
                </div>
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
              </div>
            </div>
            <div class="row ">
              <div class="game-chatting">
                게임채팅
              </div>
            </div>  
          </div>
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
=======
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
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InputPrice from '@/components/GameMain/modules/InputPrice.vue'
import GameWatingBtns from '@/components/GameMain/modules/GameWatingBtns.vue'
import GameStartInfo from './GameStartInfo.vue'
import GameRuleInfo from '@/components/GameMain/modules/GameRuleInfo.vue'
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
=======
import UserAbility from '@/components/GameMain/modules/UserAbility.vue'

import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/camera/UserVideo'; 
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue


axios.defaults.headers.post['Content-Type'] = 'application/json';

//const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_URL = "https://i6b208.p.ssafy.io:5443";
const OPENVIDU_SERVER_SECRET = "ssafy";

export default {
  components: {
    GameWatingBtns,
    GameStartInfo,
    InputPrice,
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
    GameRuleInfo
=======
    GameRuleInfo,
    UserVideo,
    UserAbility,
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
  },

  data(){
    return{
      gamePossible: false,
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

      mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
    }
  },
  methods: {
    gamestart() {
      this.gamePossible=true
    },
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
=======

    joinSession() {
      // --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

      // --- Init a session ---
			this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

      // On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
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

    updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
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

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
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
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
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
  top: 48vh;
}

.game-layout .user-video2 .col .p-3 {
  height: 180px;
  border-radius: 20px;
}

.game-log {
  width: 100%;
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
  height: 50vh;
=======
  height: 45vh;
  margin-right: 20px;
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
  background-color: black;
  margin-bottom: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

.game-chatting {
  width: 100%;
<<<<<<< HEAD:frontend/src/views/GameMain/GameView.vue
  height: 50vh;
=======
  height: 45vh;
>>>>>>> FE-game-main:src/views/GameMain/GameView.vue
  background-color: rgb(42, 106, 165);
  margin-top: 20px;
  color: white;
  font-size: 30px;
  border-radius: 20px;
}

</style>