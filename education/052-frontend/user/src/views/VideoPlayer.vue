<template>
  <div class="video-player" v-if="video">
    <div class="player-area">
      <video ref="videoRef" :src="video.url" controls @timeupdate="onTimeUpdate" @ended="onEnded"></video>
    </div>
    <div class="video-info">
      <h2>{{ video.title }}</h2>
      <el-button @click="$router.back()"><el-icon><ArrowLeft /></el-icon> 返回课程</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { learnApi } from '../api'

const route = useRoute()
const video = ref(null)
const videoRef = ref(null)
const progress = ref(0)
let saveTimer = null

onMounted(async () => {
  const res = await learnApi.getVideoInfo(route.params.id)
  video.value = res.video
  progress.value = res.progress || 0
  setTimeout(() => {
    if (videoRef.value && progress.value > 0) {
      videoRef.value.currentTime = progress.value
    }
  }, 500)
  saveTimer = setInterval(saveProgress, 30000)
})

onBeforeUnmount(() => {
  saveProgress()
  if (saveTimer) clearInterval(saveTimer)
})

const onTimeUpdate = () => {
  if (videoRef.value) {
    progress.value = Math.floor(videoRef.value.currentTime)
  }
}

const onEnded = () => {
  saveProgress()
}

const saveProgress = async () => {
  if (video.value && progress.value > 0) {
    await learnApi.saveProgress({ videoId: video.value.id, progress: progress.value })
  }
}
</script>

<style scoped>
.video-player { background: #000; min-height: calc(100vh - 140px); display: flex; flex-direction: column; }
.player-area { flex: 1; display: flex; justify-content: center; align-items: center; }
video { max-width: 100%; max-height: 70vh; }
.video-info { background: #fff; padding: 20px; display: flex; justify-content: space-between; align-items: center; }
.video-info h2 { margin: 0; }
</style>
