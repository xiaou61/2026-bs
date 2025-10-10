<template>
  <div class="main-layout">
    <el-container>
      <el-aside width="250px" class="sidebar">
        <div class="user-info">
          <el-avatar :size="50" :src="userStore.userInfo?.avatar || ''">
            {{ userStore.userInfo?.nickname?.[0] || 'U' }}
          </el-avatar>
          <div class="user-details">
            <div class="nickname">{{ userStore.userInfo?.nickname }}</div>
            <div class="status">在线</div>
          </div>
        </div>
        
        <el-menu 
          :default-active="activeMenu" 
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/chat">
            <el-icon><ChatDotRound /></el-icon>
            <span>聊天</span>
          </el-menu-item>
          <el-menu-item index="/friends">
            <el-icon><User /></el-icon>
            <span>好友</span>
          </el-menu-item>
          <el-menu-item index="/notifications">
            <el-icon><Bell /></el-icon>
            <span>通知</span>
            <el-badge 
              v-if="notifyCount > 0" 
              :value="notifyCount" 
              class="menu-badge"
            />
          </el-menu-item>
          <el-menu-item index="/profile">
            <el-icon><Setting /></el-icon>
            <span>设置</span>
          </el-menu-item>
        </el-menu>
        
        <div class="logout-btn">
          <el-button @click="handleLogout" type="danger" plain style="width: 100%">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </el-aside>
      
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import websocket from '@/utils/websocket'
import { getUnreadCount } from '@/api/notification'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const notifyCount = ref(0)

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  websocket.disconnect()
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}

const loadUnreadCounts = async () => {
  try {
    const count = await getUnreadCount()
    notifyCount.value = count || 0
  } catch (error) {
    console.error('加载未读数失败', error)
  }
}

onMounted(() => {
  loadUnreadCounts()
  
  if (userStore.userInfo && userStore.token) {
    websocket.connect(userStore.userInfo.id, userStore.token)
    
    websocket.onNotification((notification) => {
      notifyCount.value++
      ElMessage.info(notification.title)
    })
  }
})

onUnmounted(() => {
  websocket.disconnect()
})
</script>

<style scoped>
.main-layout {
  height: 100vh;
  background-color: #f5f5f5;
}

.el-container {
  height: 100%;
}

.sidebar {
  background-color: #2c3e50;
  color: white;
  display: flex;
  flex-direction: column;
}

.user-info {
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.user-details {
  flex: 1;
}

.nickname {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.status {
  font-size: 12px;
  color: #2ecc71;
}

.sidebar-menu {
  flex: 1;
  border: none;
  background-color: transparent;
}

.sidebar-menu .el-menu-item {
  color: rgba(255, 255, 255, 0.7);
  position: relative;
}

.sidebar-menu .el-menu-item.is-active {
  background-color: rgba(255, 255, 255, 0.1) !important;
  color: white;
}

.sidebar-menu .el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.05) !important;
  color: white;
}

.menu-badge {
  position: absolute;
  right: 20px;
}

.logout-btn {
  padding: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.main-content {
  padding: 0;
  background-color: white;
}
</style>

