<template>
  <div class="ov-video-wrap">
    <div v-if="streamManager">
      <ov-video :stream-manager="streamManager" />
      <div><p>{{ clientData }}</p></div>
    </div>
  </div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>

<style>
	.ov-video-wrap {
    position: relative;
    height: 90%;
    width: 100%;
    top: 50%;  /* 화면 수직정렬 위치 조정 */
    left: 50%;
    transform: translate(-50%, -52%);
}
</style>
