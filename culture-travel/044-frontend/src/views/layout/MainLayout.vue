<template>
  <div class="main-layout">
    <el-header class="header">
      <div class="header-content">
        <router-link to="/" class="logo">
          <el-icon><HomeFilled /></el-icon>
          <span>特色民宿</span>
        </router-link>
        <div class="search-box">
          <el-input v-model="keyword" placeholder="搜索民宿" @keyup.enter="handleSearch">
            <template #append>
              <el-button @click="handleSearch"><el-icon><Search /></el-icon></el-button>
            </template>
          </el-input>
        </div>
        <div class="nav-right">
          <template v-if="userStore.isLogin">
            <router-link v-if="userStore.isHost" to="/host" class="nav-link">房东中心</router-link>
            <router-link to="/booking" class="nav-link">我的订单</router-link>
            <router-link to="/favorite" class="nav-link">我的收藏</router-link>
            <el-dropdown>
              <span class="user-info">
                <el-avatar :size="32" :src="userStore.userInfo?.avatar || ''">
                  {{ userStore.userInfo?.nickname?.charAt(0) }}
                </el-avatar>
                <span>{{ userStore.userInfo?.nickname }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="$router.push('/profile')">个人中心</el-dropdown-item>
                  <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login" class="nav-link">登录</router-link>
            <router-link to="/register" class="nav-link">注册</router-link>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>© 2024 特色民宿预订平台 - 毕业设计作品</p>
    </el-footer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const keyword = ref('')

const handleSearch = () => {
  if (keyword.value.trim()) {
    router.push({ name: 'search', query: { keyword: keyword.value } })
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped lang="scss">
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}
.header-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 60px;
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
}
.search-box {
  width: 400px;
}
.nav-right {
  display: flex;
  align-items: center;
  gap: 20px;
}
.nav-link {
  color: #333;
  &:hover { color: #409eff; }
}
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}
.main {
  flex: 1;
  padding: 0;
}
.footer {
  background: #333;
  color: #fff;
  text-align: center;
  padding: 20px;
}
</style>
