<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '64px' : '230px'" class="aside">
      <div class="logo">{{ collapse ? '反' : '反欺诈平台' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#111827" text-color="#cbd5e1" active-text-color="#22d3ee">
        <template v-if="isRiskRole">
          <el-menu-item index="/dashboard">风险看板</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/user">用户管理</el-menu-item>
          <el-menu-item v-if="isAdmin" index="/log">操作日志</el-menu-item>
          <el-menu-item index="/blacklist">黑名单管理</el-menu-item>
          <el-menu-item index="/rule">规则管理</el-menu-item>
          <el-menu-item index="/event">风险事件</el-menu-item>
          <el-menu-item index="/alert">风险预警</el-menu-item>
          <el-menu-item index="/case">案件管理</el-menu-item>
          <el-menu-item index="/appeal">申诉处理</el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="/event">我的事件</el-menu-item>
          <el-menu-item index="/my-alert">我的预警</el-menu-item>
          <el-menu-item index="/my-appeal">我的申诉</el-menu-item>
        </template>
        <el-menu-item index="/announcement">公告中心</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-button text @click="collapse = !collapse">{{ collapse ? '展开' : '收起' }}</el-button>
        <div class="right">
          <el-tag>{{ roleLabel }}</el-tag>
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-button link type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapse = ref(false)
const isRiskRole = computed(() => ['ADMIN', 'ANALYST'].includes(userStore.user?.role))
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const roleLabel = computed(() => ({ ADMIN: '管理员', ANALYST: '分析员', USER: '用户' }[userStore.user?.role] || '用户'))

const handleLogout = async () => {
  try {
    await logout()
  } catch (e) {
  }
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.aside {
  background: #111827;
  transition: all 0.2s;
}

.logo {
  height: 60px;
  color: #f8fafc;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e2e8f0;
  background: #fff;
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.main {
  background: #f1f5f9;
  padding: 14px;
}
</style>
