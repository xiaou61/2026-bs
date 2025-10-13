<template>
  <div class="search-container">
    <el-card>
      <el-input
        v-model="keyword"
        placeholder="搜索视频或用户"
        size="large"
        clearable
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
        <template #append>
          <el-button @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
      
      <el-tabs v-model="activeTab" @tab-change="handleSearch">
        <el-tab-pane label="视频" name="video">
          <div class="video-grid">
            <div
              v-for="video in videoList"
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
          <el-empty v-if="videoList.length === 0 && !loading" description="未找到相关视频" />
        </el-tab-pane>
        
        <el-tab-pane label="用户" name="user">
          <div class="user-list">
            <div
              v-for="user in userList"
              :key="user.id"
              class="user-item"
              @click="goToUser(user.id)"
            >
              <el-avatar :src="user.avatar" :size="60" />
              <div class="user-info">
                <div class="nickname">{{ user.nickname }}</div>
                <div class="stats">
                  <span>粉丝 {{ user.fansCount }}</span>
                  <span>作品 {{ user.videoCount }}</span>
                </div>
              </div>
            </div>
          </div>
          <el-empty v-if="userList.length === 0 && !loading" description="未找到相关用户" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { searchVideos, searchUsers } from '@/api/search'

const router = useRouter()
const keyword = ref('')
const activeTab = ref('video')
const videoList = ref([])
const userList = ref([])
const loading = ref(false)

const handleSearch = async () => {
  if (!keyword.value.trim()) {
    return
  }
  
  loading.value = true
  try {
    if (activeTab.value === 'video') {
      const res = await searchVideos({ keyword: keyword.value, page: 1, size: 20 })
      videoList.value = res.data.records
    } else {
      const res = await searchUsers({ keyword: keyword.value, page: 1, size: 20 })
      userList.value = res.data.records
    }
  } catch (error) {
    ElMessage.error('搜索失败')
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
</script>

<style scoped>
.search-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
  margin-top: 20px;
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

.user-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 20px;
}

.user-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.user-item:hover {
  background: #f5f5f5;
}

.user-info {
  flex: 1;
}

.nickname {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 5px;
}

.stats {
  font-size: 12px;
  color: #999;
  display: flex;
  gap: 15px;
}
</style>

