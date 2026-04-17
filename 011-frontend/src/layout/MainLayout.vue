<template>
  <div class="main-layout">
    <el-container>
      <el-aside width="200px" class="sidebar">
        <div class="logo">
          <h2>📹 短视频</h2>
        </div>
        
        <el-menu
          :default-active="activeMenu"
          router
          class="menu"
        >
          <el-menu-item index="/">
            <el-icon><VideoCamera /></el-icon>
            <span>推荐</span>
          </el-menu-item>
          
          <el-menu-item index="/following">
            <el-icon><User /></el-icon>
            <span>关注</span>
          </el-menu-item>
          
          <el-menu-item index="/search">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-menu-item>
          
          <el-menu-item index="/publish">
            <el-icon><Plus /></el-icon>
            <span>发布</span>
          </el-menu-item>
          
          <el-menu-item index="/notification">
            <el-icon><Bell /></el-icon>
            <span>消息</span>
            <el-badge :value="unreadCount" :hidden="unreadCount === 0" class="notification-badge" />
          </el-menu-item>
          
          <el-menu-item index="/profile">
            <el-icon><UserFilled /></el-icon>
            <span>我的</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getUnreadCount } from '@/api/notification'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()
const unreadCount = ref(0)

const activeMenu = computed(() => {
  const path = route.path
  if (
    path.startsWith('/user/') ||
    path.startsWith('/video/') ||
    path.startsWith('/topic/')
  ) {
    return '/'
  }
  if (
    path.startsWith('/drafts') ||
    path.startsWith('/creator-center') ||
    path.startsWith('/points-mall')
  ) {
    return '/profile'
  }
  return path
})

const fetchUnreadCount = async () => {
  try {
    const res = await getUnreadCount()
    unreadCount.value = res.data
  } catch (error) {
    console.error('获取未读消息数失败:', error)
  }
}

onMounted(async () => {
  if (!userStore.userInfo) {
    try {
      await userStore.getUserInfo()
    } catch (error) {
      console.error('获取用户信息失败:', error)
    }
  }
  
  fetchUnreadCount()
  setInterval(fetchUnreadCount, 30000)
})
</script>

<style scoped>
.main-layout {
  height: 100vh;
  overflow: hidden;
}

.el-container {
  height: 100%;
}

.sidebar {
  background: #fff;
  border-right: 1px solid #f0f0f0;
  display: flex;
  flex-direction: column;
}

.logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #f0f0f0;
}

.logo h2 {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.menu {
  flex: 1;
  border: none;
}

.el-menu-item {
  height: 56px;
  line-height: 56px;
  font-size: 16px;
}

.notification-badge {
  margin-left: 10px;
}

.main-content {
  padding: 0;
  overflow: hidden;
  background: #f5f5f5;
}
</style>

