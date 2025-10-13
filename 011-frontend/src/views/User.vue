<template>
  <div class="user-container">
    <el-card class="user-card" v-if="userInfo">
      <div class="user-header">
        <el-avatar :src="userInfo.avatar" :size="100" />
        
        <div class="info">
          <h2>{{ userInfo.nickname }}</h2>
          <p class="signature">{{ userInfo.signature || '这个人很懒，什么都没留下' }}</p>
          
          <div class="stats">
            <div class="stat-item">
              <div class="value">{{ userInfo.videoCount }}</div>
              <div class="label">作品</div>
            </div>
            <div class="stat-item">
              <div class="value">{{ userInfo.likeCount }}</div>
              <div class="label">获赞</div>
            </div>
            <div class="stat-item">
              <div class="value">{{ userInfo.fansCount }}</div>
              <div class="label">粉丝</div>
            </div>
            <div class="stat-item">
              <div class="value">{{ userInfo.followCount }}</div>
              <div class="label">关注</div>
            </div>
          </div>
        </div>
        
        <el-button
          v-if="!userInfo.isFollow"
          type="primary"
          @click="handleFollow"
        >
          关注
        </el-button>
        <el-button v-else @click="handleUnfollow">已关注</el-button>
      </div>
    </el-card>
    
    <el-tabs v-model="activeTab" class="tabs" @tab-change="handleTabChange">
      <el-tab-pane label="作品" name="videos">
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
          <div class="title">{{ video.title }}</div>
        </div>
        <el-empty v-if="videos.length === 0" description="暂无作品" />
      </el-tab-pane>
      
      <el-tab-pane label="喜欢" name="likes">
        <div class="video-grid">
          <div
            v-for="video in likedVideos"
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
            <div class="title">{{ video.title }}</div>
          </div>
        </div>
        <el-empty v-if="likedVideos.length === 0" description="暂无喜欢" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getUserInfo as getUserInfoApi, getUserVideos, followUser, unfollowUser } from '@/api/user'

const route = useRoute()
const router = useRouter()
const userId = ref(route.params.id)
const userInfo = ref(null)
const videos = ref([])
const likedVideos = ref([])
const activeTab = ref('videos')

const fetchUserInfo = async () => {
  try {
    const res = await getUserInfoApi(userId.value)
    userInfo.value = res.data
  } catch (error) {
    ElMessage.error('获取用户信息失败')
  }
}

const fetchVideos = async () => {
  try {
    const res = await getUserVideos(userId.value, { page: 1, size: 20 })
    videos.value = res.data.records
  } catch (error) {
    ElMessage.error('获取作品失败')
  }
}

const handleFollow = async () => {
  try {
    await followUser(userId.value)
    userInfo.value.isFollow = true
    userInfo.value.fansCount++
    ElMessage.success('关注成功')
  } catch (error) {
    ElMessage.error('关注失败')
  }
}

const handleUnfollow = async () => {
  try {
    await unfollowUser(userId.value)
    userInfo.value.isFollow = false
    userInfo.value.fansCount--
    ElMessage.success('取消关注成功')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const fetchLikedVideos = async () => {
  try {
    const res = await getUserVideos(userId.value, { page: 1, size: 20 })
    likedVideos.value = res.data.records
  } catch (error) {
    console.error('获取喜欢列表失败:', error)
  }
}

const handleTabChange = (tabName) => {
  if (tabName === 'likes' && likedVideos.value.length === 0) {
    fetchLikedVideos()
  }
}

const goToDetail = (videoId) => {
  router.push(`/video/${videoId}`)
}

onMounted(() => {
  fetchUserInfo()
  fetchVideos()
})
</script>

<style scoped>
.user-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.user-card {
  margin-bottom: 20px;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 30px;
}

.info {
  flex: 1;
}

.info h2 {
  margin: 0 0 10px 0;
}

.signature {
  color: #666;
  margin: 0 0 15px 0;
}

.stats {
  display: flex;
  gap: 30px;
}

.stat-item {
  text-align: center;
}

.stat-item .value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.stat-item .label {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.tabs {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
}

.video-card {
  cursor: pointer;
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

.title {
  margin-top: 8px;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>

