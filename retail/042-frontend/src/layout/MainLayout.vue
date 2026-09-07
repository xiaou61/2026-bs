<template>
  <div class="main-layout">
    <el-header class="header">
      <div class="header-content">
        <router-link to="/" class="logo">
          <el-icon><HomeFilled /></el-icon>
          房屋租赁管理系统
        </router-link>
        <el-menu mode="horizontal" :ellipsis="false" class="nav-menu">
          <el-menu-item index="/">
            <router-link to="/">首页</router-link>
          </el-menu-item>
          <el-menu-item index="/house">
            <router-link to="/house">房源列表</router-link>
          </el-menu-item>
        </el-menu>
        <div class="user-area">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown trigger="click">
              <span class="user-info">
                <el-avatar :size="32">{{ userStore.userInfo?.realName?.[0] || userStore.userInfo?.username?.[0] }}</el-avatar>
                <span class="username">{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="goToPanel">进入控制台</el-dropdown-item>
                  <el-dropdown-item @click="$router.push('/profile')">个人设置</el-dropdown-item>
                  <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button type="primary" @click="$router.push('/login')">登录</el-button>
            <el-button @click="$router.push('/register')">注册</el-button>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>© 2026 房屋租赁管理系统 - 毕业设计项目</p>
    </el-footer>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()

const goToPanel = () => {
  const role = userStore.userInfo?.role
  if (role === 'admin') router.push('/admin')
  else if (role === 'landlord') router.push('/landlord')
  else router.push('/tenant')
}

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 0;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  height: 60px;
  padding: 0 20px;
}

.logo {
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
  display: flex;
  align-items: center;
  gap: 8px;
  white-space: nowrap;
}

.nav-menu {
  flex: 1;
  border-bottom: none;
  margin-left: 40px;
}

.nav-menu a {
  color: inherit;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.username {
  color: #333;
}

.main {
  flex: 1;
  padding: 20px;
  background: #f5f5f5;
}

.footer {
  text-align: center;
  color: #999;
  background: #fff;
  border-top: 1px solid #eee;
}
</style>
