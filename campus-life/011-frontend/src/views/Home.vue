<template>
  <div class="home-container">
    <div
      class="video-list"
      ref="videoListRef"
      @scroll="handleScroll"
    >
      <div
        v-for="video in videoList"
        :key="video.id"
        class="video-item"
      >
        <video
          :src="video.videoUrl"
          :poster="video.coverUrl"
          class="video-player"
          loop
          @click="togglePlay($event)"
        ></video>
        
        <div class="video-info">
          <div class="author-info" @click="goToUser(video.user.id)">
            <el-avatar :src="video.user.avatar" :size="40" />
            <span class="nickname">{{ video.user.nickname }}</span>
            <el-button
              v-if="!video.user.isFollow && video.user.id !== userStore.userId"
              type="primary"
              size="small"
              @click.stop="handleFollow(video.user.id)"
            >
              关注
            </el-button>
          </div>
          
          <div class="title">{{ video.title }}</div>
          <div class="description">{{ video.description }}</div>
          
          <div class="topics">
            <el-tag
              v-for="topic in video.topics"
              :key="topic.id"
              size="small"
              @click="goToTopic(topic.id)"
            >
              {{ topic.topicName }}
            </el-tag>
          </div>
        </div>
        
        <div class="action-bar">
          <div class="action-item" @click="handleLike(video)">
            <el-icon :size="32" :class="{ active: video.isLike }">
              <component :is="video.isLike ? 'StarFilled' : 'Star'" />
            </el-icon>
            <span>{{ video.likeCount }}</span>
          </div>
          
          <div class="action-item" @click="goToDetail(video.id)">
            <el-icon :size="32"><ChatDotRound /></el-icon>
            <span>{{ video.commentCount }}</span>
          </div>
          
          <div class="action-item" @click="handleCollect(video)">
            <el-icon :size="32" :class="{ active: video.isCollect }">
              <component :is="video.isCollect ? 'CollectionTag' : 'Collection'" />
            </el-icon>
            <span>{{ video.collectCount }}</span>
          </div>
          
          <div class="action-item" @click="handleShare(video)">
            <el-icon :size="32"><Share /></el-icon>
            <span>{{ video.shareCount }}</span>
          </div>
        </div>
      </div>
    </div>
    
    <el-empty v-if="!loading && videoList.length === 0" description="暂无视频" />
    
    <div v-if="loading" class="loading">
      <el-icon class="is-loading"><Loading /></el-icon>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getRecommendVideos, recordPlay } from '@/api/video'
import { likeVideo, unlikeVideo, collectVideo, uncollectVideo } from '@/api/video'
import { followUser } from '@/api/user'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const videoList = ref([])
const loading = ref(false)
const page = ref(1)
const videoListRef = ref()

const fetchVideos = async () => {
  if (loading.value) return
  
  loading.value = true
  try {
    const res = await getRecommendVideos({ page: page.value, size: 10 })
    videoList.value.push(...res.data.records)
    page.value++
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const handleScroll = (e) => {
  const { scrollTop, scrollHeight, clientHeight } = e.target
  if (scrollTop + clientHeight >= scrollHeight - 100) {
    fetchVideos()
  }
  
  const videos = document.querySelectorAll('.video-player')
  videos.forEach((video, index) => {
    const rect = video.getBoundingClientRect()
    if (rect.top >= 0 && rect.bottom <= window.innerHeight) {
      if (video.paused) {
        video.play()
        recordPlay(videoList.value[index].id)
      }
    } else {
      if (!video.paused) {
        video.pause()
      }
    }
  })
}

const togglePlay = (e) => {
  const video = e.target
  if (video.paused) {
    video.play()
  } else {
    video.pause()
  }
}

const handleLike = async (video) => {
  try {
    if (video.isLike) {
      await unlikeVideo(video.id)
      video.isLike = false
      video.likeCount--
    } else {
      await likeVideo(video.id)
      video.isLike = true
      video.likeCount++
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleCollect = async (video) => {
  try {
    if (video.isCollect) {
      await uncollectVideo(video.id)
      video.isCollect = false
      video.collectCount--
    } else {
      await collectVideo(video.id)
      video.isCollect = true
      video.collectCount++
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleFollow = async (userId) => {
  try {
    await followUser(userId)
    const video = videoList.value.find(v => v.user.id === userId)
    if (video) {
      video.user.isFollow = true
    }
    ElMessage.success('关注成功')
  } catch (error) {
    ElMessage.error('关注失败')
  }
}

const handleShare = (video) => {
  ElMessage.info('分享功能开发中')
}

const goToUser = (userId) => {
  router.push(`/user/${userId}`)
}

const goToTopic = (topicId) => {
  router.push(`/topic/${topicId}`)
}

const goToDetail = (videoId) => {
  router.push(`/video/${videoId}`)
}

onMounted(() => {
  fetchVideos()
})
</script>

<style scoped>
.home-container {
  height: 100vh;
  overflow: hidden;
  background: #000;
}

.video-list {
  height: 100%;
  overflow-y: auto;
  scroll-snap-type: y mandatory;
  scrollbar-width: none;
}

.video-list::-webkit-scrollbar {
  display: none;
}

.video-item {
  position: relative;
  height: 100vh;
  scroll-snap-align: start;
  display: flex;
  align-items: center;
  justify-content: center;
}

.video-player {
  width: 100%;
  height: 100%;
  object-fit: contain;
  cursor: pointer;
}

.video-info {
  position: absolute;
  bottom: 100px;
  left: 20px;
  right: 100px;
  color: #fff;
  z-index: 10;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
  cursor: pointer;
}

.nickname {
  font-size: 16px;
  font-weight: 500;
}

.title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.description {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 10px;
}

.topics {
  display: flex;
  gap: 8px;
}

.action-bar {
  position: absolute;
  right: 20px;
  bottom: 100px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  z-index: 10;
}

.action-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
  color: #fff;
  cursor: pointer;
  transition: transform 0.2s;
}

.action-item:hover {
  transform: scale(1.1);
}

.action-item span {
  font-size: 12px;
}

.action-item .active {
  color: #ff2d55;
}

.loading {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: #fff;
  font-size: 24px;
}
</style>

