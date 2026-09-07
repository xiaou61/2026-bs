<template>
  <div class="following-container">
    <div class="video-grid">
      <div
        v-for="video in videoList"
        :key="video.id"
        class="video-card"
        @click="goToDetail(video.id)"
      >
        <div class="video-cover">
          <img :src="video.coverUrl" alt="">
          <div class="play-icon">
            <el-icon><VideoPlay /></el-icon>
          </div>
          <div class="video-stats">
            <span><el-icon><View /></el-icon> {{ video.playCount }}</span>
            <span><el-icon><Star /></el-icon> {{ video.likeCount }}</span>
          </div>
        </div>
        
        <div class="video-info">
          <div class="author-info" @click.stop="goToUser(video.user.id)">
            <el-avatar :src="video.user.avatar" :size="30" />
            <span class="nickname">{{ video.user.nickname }}</span>
          </div>
          <div class="title">{{ video.title }}</div>
        </div>
      </div>
    </div>
    
    <el-empty v-if="!loading && videoList.length === 0" description="快去关注喜欢的创作者吧" />
    
    <div v-if="loading" class="loading">
      <el-icon class="is-loading"><Loading /></el-icon>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getFollowingVideos } from '@/api/video'

const router = useRouter()
const videoList = ref([])
const loading = ref(false)
const page = ref(1)

const fetchVideos = async () => {
  loading.value = true
  try {
    const res = await getFollowingVideos({ page: page.value, size: 20 })
    videoList.value = res.data.records
  } catch (error) {
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

const goToDetail = (videoId) => {
  router.push(`/video/${videoId}`)
}

const goToUser = (userId) => {
  router.push(`/user/${userId}`)
}

onMounted(() => {
  fetchVideos()
})
</script>

<style scoped>
.following-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.video-card {
  cursor: pointer;
  transition: transform 0.2s;
}

.video-card:hover {
  transform: translateY(-5px);
}

.video-cover {
  position: relative;
  width: 100%;
  padding-bottom: 133%;
  background: #f5f5f5;
  border-radius: 8px;
  overflow: hidden;
}

.video-cover img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.play-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 40px;
  color: #fff;
  opacity: 0;
  transition: opacity 0.3s;
}

.video-card:hover .play-icon {
  opacity: 1;
}

.video-stats {
  position: absolute;
  bottom: 10px;
  left: 10px;
  right: 10px;
  display: flex;
  justify-content: space-between;
  color: #fff;
  font-size: 12px;
}

.video-stats span {
  display: flex;
  align-items: center;
  gap: 4px;
}

.video-info {
  margin-top: 10px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
}

.nickname {
  font-size: 14px;
  color: #666;
}

.title {
  font-size: 14px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.loading {
  text-align: center;
  padding: 20px;
  font-size: 24px;
  color: #999;
}
</style>

