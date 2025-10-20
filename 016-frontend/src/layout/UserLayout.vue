<template>
  <el-container class="layout-container">
    <el-header class="header">
      <div class="header-content">
        <div class="logo">
          <el-icon><van /></el-icon>
          <span>校园快递代领</span>
        </div>
        <el-menu
          mode="horizontal"
          :default-active="activeIndex"
          router
          class="header-menu"
        >
          <el-menu-item index="/home">订单广场</el-menu-item>
          <el-menu-item index="/my-orders">我的订单</el-menu-item>
          <el-menu-item index="/wallet">我的钱包</el-menu-item>
        </el-menu>
        <div class="header-right">
          <el-badge :value="unreadCount" :hidden="unreadCount === 0" class="badge-item">
            <el-button :icon="Bell" circle @click="$router.push('/notifications')" />
          </el-badge>
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="32" :icon="UserFilled" />
              <span class="username">{{ userStore.userInfo?.username }}</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
    <el-button
      type="primary"
      :icon="Plus"
      circle
      size="large"
      class="fab-button"
      @click="$router.push('/publish')"
    />
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../stores/user'
import { getUnreadCount } from '../api/notification'
import { Plus, Bell, UserFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const unreadCount = ref(0)

const activeIndex = computed(() => route.path)

const loadUnreadCount = async () => {
  try {
    const count = await getUnreadCount()
    unreadCount.value = count
  } catch (error) {
    console.error('获取未读消息数失败', error)
  }
}

const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}

onMounted(() => {
  loadUnreadCount()
  setInterval(loadUnreadCount, 30000)
})
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  max-width: 1400px;
  margin: 0 auto;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: bold;
  color: #409eff;
  cursor: pointer;
}

.header-menu {
  flex: 1;
  border: none;
  margin: 0 40px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.badge-item {
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.username {
  font-size: 14px;
  color: #333;
}

.main-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 24px;
  width: 100%;
}

.fab-button {
  position: fixed;
  right: 40px;
  bottom: 40px;
  z-index: 999;
  width: 56px;
  height: 56px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.3);
}
</style>

