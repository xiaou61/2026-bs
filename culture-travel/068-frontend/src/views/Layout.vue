<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '64px' : '240px'" class="aside">
      <div class="logo">{{ collapse ? '周边游' : '周边游个人管理' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#0f172a" text-color="#cbd5e1" active-text-color="#5eead4">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/user">用户管理</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/spot">景点管理</el-menu-item>
        <el-menu-item v-if="isUser" index="/traveler">常用出行人</el-menu-item>
        <el-menu-item v-if="isUser" index="/favorite">我的收藏</el-menu-item>
        <el-menu-item index="/order">{{ isAdmin ? '订单管理' : '我的订单' }}</el-menu-item>
        <el-menu-item index="/review">{{ isAdmin ? '评价管理' : '我的评价' }}</el-menu-item>
        <el-menu-item index="/complaint">{{ isAdmin ? '投诉处理' : '我的投诉' }}</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-button text @click="collapse = !collapse">{{ collapse ? '展开' : '收起' }}</el-button>
        <div class="right">
          <el-tag type="success">{{ roleText }}</el-tag>
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
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isUser = computed(() => userStore.user?.role === 'USER')
const roleText = computed(() => (isAdmin.value ? '管理员' : '普通用户'))

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
  background: #0f172a;
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
  background: linear-gradient(160deg, #f8fafc, #ecfeff 55%, #f0fdfa);
  padding: 14px;
}
</style>
