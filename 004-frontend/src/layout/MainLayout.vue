<template>
  <div class="main-layout">
    <el-container class="layout-container">
      <!-- 侧边栏 -->
      <el-aside class="sidebar">
        <!-- 用户信息 -->
        <div class="user-info">
          <el-avatar :size="50" class="user-avatar">
            {{ userStore.userInfo?.nickname?.[0] || 'U' }}
          </el-avatar>
          <div class="user-details">
            <div class="nickname">{{ userStore.userInfo?.nickname }}</div>
            <div class="status">
              <span class="status-dot"></span>
              在线
            </div>
          </div>
        </div>
        
        <!-- 菜单 -->
        <el-menu 
          :default-active="activeMenu" 
          router
          class="sidebar-menu"
        >
          <el-menu-item index="/chat" class="menu-item">
            <el-icon><ChatDotRound /></el-icon>
            <template #title>
              聊天
              <el-badge 
                v-if="notifyCount > 0" 
                :value="notifyCount" 
                class="menu-badge"
              />
            </template>
          </el-menu-item>
          <el-menu-item index="/friends" class="menu-item">
            <el-icon><User /></el-icon>
            <template #title>好友</template>
          </el-menu-item>
          <el-menu-item index="/notifications" class="menu-item">
            <el-icon><Bell /></el-icon>
            <template #title>通知</template>
          </el-menu-item>
          <el-menu-item index="/profile" class="menu-item">
            <el-icon><Setting /></el-icon>
            <template #title>设置</template>
          </el-menu-item>
        </el-menu>
        
        <!-- 退出按钮 -->
        <div class="logout-btn">
          <el-button @click="handleLogout" class="logout-button">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </el-aside>
      
      <!-- 主内容区 -->
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
  background-color: #F5F5F5;
}

.layout-container {
  height: 100%;
}

/* 侧边栏 */
.sidebar {
  background: linear-gradient(180deg, #FF6D00 0%, #E65100 50%, #4A148C 100%);
  color: white;
  display: flex;
  flex-direction: column;
  width: 260px;
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
}

/* 用户信息 */
.user-info {
  padding: 24px 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
}

.user-avatar {
  background: linear-gradient(135deg, #FFFFFF 0%, #FFF3E0 100%);
  color: #FF6D00;
  font-weight: 700;
  font-size: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.user-details {
  flex: 1;
}

.nickname {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 6px;
  font-family: var(--font-heading);
}

.status {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  gap: 6px;
}

.status-dot {
  width: 8px;
  height: 8px;
  background: #4CAF50;
  border-radius: 50%;
  display: inline-block;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(76, 175, 80, 0.7);
  }
  70% {
    box-shadow: 0 0 0 6px rgba(76, 175, 80, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(76, 175, 80, 0);
  }
}

/* 菜单 */
.sidebar-menu {
  flex: 1;
  border: none !important;
  background: transparent !important;
  padding: 16px 0;
}

.menu-item {
  height: 50px !important;
  line-height: 50px !important;
  margin: 4px 12px;
  border-radius: 12px !important;
  color: rgba(255, 255, 255, 0.7) !important;
  transition: all 0.3s ease !important;
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.15) !important;
  color: white !important;
}

.menu-item.is-active {
  background: rgba(255, 255, 255, 0.25) !important;
  color: white !important;
  font-weight: 600;
}

.menu-badge {
  margin-left: 8px;
}

/* 退出按钮 */
.logout-btn {
  padding: 16px 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.15);
}

.logout-button {
  width: 100%;
  background: rgba(255, 255, 255, 0.15) !important;
  border: none !important;
  color: white !important;
  border-radius: 10px;
  height: 44px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.logout-button:hover {
  background: rgba(255, 255, 255, 0.25) !important;
}

/* 主内容区 */
.main-content {
  padding: 0;
  background-color: #F5F5F5;
}
</style>

