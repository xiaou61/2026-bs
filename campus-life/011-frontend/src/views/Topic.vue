<template>
  <div class="topic-container">
    <el-card class="topic-card" v-if="topic">
      <div class="topic-header">
        <img v-if="topic.coverUrl" :src="topic.coverUrl" class="topic-cover" />
        <div class="topic-info">
          <h2>{{ topic.topicName }}</h2>
          <p>{{ topic.description }}</p>
          <div class="stats">
            <span>{{ topic.videoCount }} 个视频</span>
            <span>{{ topic.viewCount }} 次浏览</span>
          </div>
        </div>
      </div>
    </el-card>
    
    <div class="video-section">
      <div class="video-grid">
        <div
          v-for="video in videos"
          :key="video.id"
          class="video-card"
          @click="goToDetail(video.id)"
        >
          <div class="video-cover">
            <img :src="video.coverUrl" alt="">
            <div class="video-stats">
              <span><el-icon><View /></el-icon> {{ video.playCount }}</span>
              <span><el-icon><Star /></el-icon> {{ video.likeCount }}</span>
            </div>
          </div>
          <div class="video-info">
            <div class="title">{{ video.title }}</div>
            <div class="author">
              <el-avatar :src="video.user.avatar" :size="24" />
              <span>{{ video.user.nickname }}</span>
            </div>
          </div>
        </div>
      </div>
      
      <el-empty v-if="videos.length === 0" description="暂无视频" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getTopicDetail, getTopicVideos } from '@/api/topic'

const route = useRoute()
const router = useRouter()
const topicId = ref(route.params.id)
const topic = ref(null)
const videos = ref([])

const fetchTopic = async () => {
  try {
    const res = await getTopicDetail(topicId.value)
    topic.value = res.data
  } catch (error) {
    ElMessage.error('获取话题失败')
  }
}

const fetchVideos = async () => {
  try {
    const res = await getTopicVideos(topicId.value, { page: 1, size: 20 })
    videos.value = res.data.records
  } catch (error) {
    ElMessage.error('获取视频失败')
  }
}

const goToDetail = (videoId) => {
  router.push(`/video/${videoId}`)
}

onMounted(() => {
  fetchTopic()
  fetchVideos()
})
</script>

<style scoped>
.topic-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.topic-card {
  margin-bottom: 20px;
}

.topic-header {
  display: flex;
  gap: 20px;
}

.topic-cover {
  width: 150px;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
}

.topic-info {
  flex: 1;
}

.topic-info h2 {
  margin: 0 0 10px 0;
  color: #409eff;
}

.topic-info p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
}

.stats {
  display: flex;
  gap: 20px;
  color: #999;
  font-size: 14px;
}

.video-section {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
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

.title {
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.author {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: #666;
}
</style>

