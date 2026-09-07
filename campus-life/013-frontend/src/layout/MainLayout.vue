<template>
  <div class="main-layout">
    <el-container>
      <el-header class="header">
        <div class="header-content">
          <div class="logo" @click="$router.push('/')">
            <el-icon><Share /></el-icon>
            <span>校园共享</span>
          </div>
          
          <el-menu
            :default-active="activeMenu"
            mode="horizontal"
            :ellipsis="false"
            @select="handleMenuSelect"
          >
            <el-menu-item index="/home">首页</el-menu-item>
            <el-menu-item index="/map">地图</el-menu-item>
            <el-menu-item index="/idle">闲置物品</el-menu-item>
            <el-menu-item index="/skill">技能服务</el-menu-item>
          </el-menu>

          <div class="header-actions">
            <el-badge :value="unreadCount" :hidden="unreadCount === 0">
              <el-button text @click="$router.push('/message')">
                <el-icon><Bell /></el-icon>
              </el-button>
            </el-badge>

            <el-dropdown v-if="userStore.token" @command="handleCommand">
              <div class="user-info">
                <el-avatar :size="32">{{ userStore.userInfo?.nickname?.charAt(0) }}</el-avatar>
                <span>{{ userStore.userInfo?.nickname }}</span>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                  <el-dropdown-item command="order">我的订单</el-dropdown-item>
                  <el-dropdown-item command="my-publish">我的发布</el-dropdown-item>
                  <el-dropdown-item command="wallet">我的钱包</el-dropdown-item>
                  <el-dropdown-item command="credit">信用中心</el-dropdown-item>
                  <el-dropdown-item divided v-if="userStore.userInfo?.role === 'ADMIN'" command="admin">管理后台</el-dropdown-item>
                  <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>

            <el-button v-else type="primary" @click="$router.push('/login')">登录</el-button>
          </div>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { getUnreadCount } from '@/api/notification'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const unreadCount = ref(0)

const activeMenu = computed(() => {
  return route.path
})

const loadUnreadCount = async () => {
  if (userStore.token) {
    try {
      const res = await getUnreadCount()
      unreadCount.value = res.data
    } catch (error) {
      console.error(error)
    }
  }
}

const handleMenuSelect = (index) => {
  router.push(index)
}

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    ElMessage.success('退出成功')
    router.push('/login')
  } else if (command === 'admin') {
    router.push('/admin')
  } else {
    router.push(`/${command}`)
  }
}

onMounted(() => {
  loadUnreadCount()
  setInterval(loadUnreadCount, 30000)
})
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  padding: 0;
  height: 60px;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
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

.el-menu {
  flex: 1;
  border: none;
  margin: 0 40px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 12px;
  border-radius: 4px;
}

.user-info:hover {
  background: #f5f7fa;
}

.main-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
}
</style>

