<template>
  <div class="profile-container">
    <el-card class="profile-card" v-if="userInfo">
      <div class="profile-header">
        <el-avatar :src="userInfo.avatar" :size="100" />
        
        <div class="user-info">
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
          
          <div class="level-info">
            <el-tag type="warning">Lv{{ userInfo.level }}</el-tag>
            <span class="points">积分：{{ userInfo.points }}</span>
          </div>
        </div>
        
        <el-button type="primary" @click="showEditDialog = true">编辑资料</el-button>
        <el-button @click="handleLogout">退出登录</el-button>
      </div>
    </el-card>
    
    <el-tabs v-model="activeTab" class="tabs" @tab-change="handleTabChange">
      <el-tab-pane label="我的作品" name="videos">
        <div class="video-grid">
          <div
            v-for="video in myVideos"
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
        <el-empty v-if="myVideos.length === 0" description="还没有作品" />
      </el-tab-pane>
      
      <el-tab-pane label="我的喜欢" name="likes">
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
        <el-empty v-if="likedVideos.length === 0" description="还没有喜欢的视频" />
      </el-tab-pane>
      
      <el-tab-pane label="我的收藏" name="collects">
        <div class="video-grid">
          <div
            v-for="video in collectedVideos"
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
        <el-empty v-if="collectedVideos.length === 0" description="还没有收藏的视频" />
      </el-tab-pane>
      
      <el-tab-pane label="积分明细" name="points">
        <el-table :data="pointsLog" stripe>
          <el-table-column prop="changeType" label="类型" width="100">
            <template #default="{ row }">
              <el-tag v-if="row.changePoints > 0" type="success">{{ row.changeType }}</el-tag>
              <el-tag v-else type="danger">{{ row.changeType }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="reason" label="原因" />
          <el-table-column prop="changePoints" label="变动" width="100">
            <template #default="{ row }">
              <span :class="row.changePoints > 0 ? 'increase' : 'decrease'">
                {{ row.changePoints > 0 ? '+' : '' }}{{ row.changePoints }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="afterPoints" label="余额" width="100" />
          <el-table-column prop="createTime" label="时间" width="180" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
    
    <el-dialog v-model="showEditDialog" title="编辑资料" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :http-request="handleAvatarUpload"
            accept="image/*"
          >
            <el-avatar :src="editForm.avatar" :size="80" />
          </el-upload>
        </el-form-item>
        
        <el-form-item label="昵称">
          <el-input v-model="editForm.nickname" />
        </el-form-item>
        
        <el-form-item label="签名">
          <el-input v-model="editForm.signature" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdate">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserVideos, updateProfile, uploadAvatar } from '@/api/user'
import { getPointsLog } from '@/api/user'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const userInfo = ref(null)
const myVideos = ref([])
const likedVideos = ref([])
const collectedVideos = ref([])
const pointsLog = ref([])
const activeTab = ref('videos')
const showEditDialog = ref(false)
const editForm = ref({
  nickname: '',
  signature: '',
  avatar: ''
})

const fetchUserInfo = async () => {
  try {
    await userStore.getUserInfo()
    userInfo.value = userStore.userInfo
    editForm.value = { ...userInfo.value }
  } catch (error) {
    ElMessage.error('获取用户信息失败')
  }
}

const fetchMyVideos = async () => {
  if (!userStore.userId) {
    return
  }
  
  try {
    const res = await getUserVideos(userStore.userId, { page: 1, size: 20 })
    myVideos.value = res.data.records
  } catch (error) {
    ElMessage.error('获取作品失败')
  }
}

const fetchLikedVideos = async () => {
  if (!userStore.userId) {
    return
  }
  
  try {
    const res = await getUserVideos(userStore.userId, { page: 1, size: 20 })
    likedVideos.value = res.data.records
  } catch (error) {
    console.error('获取喜欢列表失败:', error)
  }
}

const fetchCollectedVideos = async () => {
  if (!userStore.userId) {
    return
  }
  
  try {
    const res = await getUserVideos(userStore.userId, { page: 1, size: 20 })
    collectedVideos.value = res.data.records
  } catch (error) {
    console.error('获取收藏列表失败:', error)
  }
}

const fetchPointsLog = async () => {
  if (!userStore.userId) {
    return
  }
  
  try {
    const res = await getPointsLog()
    pointsLog.value = res.data
  } catch (error) {
    ElMessage.error('获取积分明细失败')
  }
}

const handleAvatarUpload = async (options) => {
  try {
    const res = await uploadAvatar(options.file)
    editForm.value.avatar = res.data
    ElMessage.success('头像上传成功')
  } catch (error) {
    ElMessage.error('头像上传失败')
  }
}

const handleUpdate = async () => {
  try {
    await updateProfile(editForm.value)
    userStore.updateUserInfo(editForm.value)
    userInfo.value = { ...userInfo.value, ...editForm.value }
    showEditDialog.value = false
    ElMessage.success('更新成功')
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      type: 'warning'
    })
    userStore.removeToken()
    router.push('/login')
    ElMessage.success('已退出登录')
  } catch (error) {
  }
}

const goToDetail = (videoId) => {
  router.push(`/video/${videoId}`)
}

const handleTabChange = (tabName) => {
  if (tabName === 'likes' && likedVideos.value.length === 0) {
    fetchLikedVideos()
  } else if (tabName === 'collects' && collectedVideos.value.length === 0) {
    fetchCollectedVideos()
  }
}

onMounted(async () => {
  await fetchUserInfo()
  fetchMyVideos()
  fetchPointsLog()
})
</script>

<style scoped>
.profile-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.profile-card {
  margin-bottom: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 30px;
}

.user-info {
  flex: 1;
}

.user-info h2 {
  margin: 0 0 10px 0;
}

.signature {
  color: #666;
  margin: 0 0 15px 0;
}

.stats {
  display: flex;
  gap: 30px;
  margin-bottom: 15px;
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

.level-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.points {
  color: #666;
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

.avatar-uploader {
  cursor: pointer;
}

.increase {
  color: #67c23a;
}

.decrease {
  color: #f56c6c;
}
</style>

