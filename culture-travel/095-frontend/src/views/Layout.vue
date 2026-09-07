<template>
  <div class="layout">
    <el-container>
      <el-aside width="236px" class="aside">
        <div class="logo">足球联赛 095</div>
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/dashboard">首页</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/user">账号管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/league">联赛管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/season">赛季管理</el-menu-item>
          <el-menu-item index="/club">{{ isFan ? '俱乐部浏览' : '俱乐部管理' }}</el-menu-item>
          <el-menu-item index="/team">{{ isFan ? '球队图鉴' : '球队管理' }}</el-menu-item>
          <el-menu-item v-if="!isFan" index="/coach">教练管理</el-menu-item>
          <el-menu-item index="/player">{{ isFan ? '球员观察' : '球员管理' }}</el-menu-item>
          <el-menu-item v-if="!isFan" index="/venue">球场管理</el-menu-item>
          <el-menu-item index="/match">{{ isFan ? '赛程中心' : '赛程管理' }}</el-menu-item>
          <el-menu-item index="/standing">积分榜</el-menu-item>
          <el-menu-item v-if="isFan" index="/follow">我的关注</el-menu-item>
          <el-menu-item index="/news">资讯公告</el-menu-item>
          <el-menu-item v-if="!isFan" index="/statistics">统计看板</el-menu-item>
          <el-menu-item index="/profile">个人中心</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="header">
          <div class="title">荣耀绿茵足球联赛平台</div>
          <div class="right">
            <span>{{ roleText }}</span>
            <span>{{ user?.nickname || user?.username }}</span>
            <el-button type="danger" link @click="handleLogout">退出</el-button>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { logout } from '../api'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const user = computed(() => userStore.user)
const role = computed(() => (user.value?.role || '').toUpperCase())
const isAdmin = computed(() => role.value === 'ADMIN')
const isFan = computed(() => role.value === 'FAN')
const roleTextMap = {
  ADMIN: '当前角色：管理员',
  MANAGER: '当前角色：俱乐部经理',
  FAN: '当前角色：球迷'
}
const roleText = computed(() => roleTextMap[role.value] || '当前角色：访客')

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
  }
  userStore.clearLogin()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.aside {
  background: linear-gradient(180deg, #0f2547 0%, #163b6a 45%, #1d4f87 100%);
  color: #fff;
}

.logo {
  height: 58px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 1px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.14);
}

:deep(.el-menu) {
  border-right: none;
  background: transparent;
}

:deep(.el-menu-item) {
  color: #dbe7f8;
}

:deep(.el-menu-item.is-active) {
  background: rgba(255, 255, 255, 0.12);
  color: #fff;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #ebeef5;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #14365d;
}

.right {
  display: flex;
  align-items: center;
  gap: 14px;
  color: #475467;
}
</style>
