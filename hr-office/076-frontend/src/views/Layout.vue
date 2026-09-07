<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '64px' : '220px'" class="aside">
      <div class="logo">{{ collapse ? '修' : '企业信息管理系统' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#1f2937" text-color="#cbd5e1" active-text-color="#38bdf8">
        <el-menu-item index="/dashboard">首页看板</el-menu-item>
        <template v-if="isAdmin">
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/category">分类管理</el-menu-item>
          <el-menu-item index="/item">信息管理</el-menu-item>
          <el-menu-item index="/order">处理管理</el-menu-item>
          <el-menu-item index="/review">评价管理</el-menu-item>
          <el-menu-item index="/complaint">申诉管理</el-menu-item>
        </template>
        <el-menu-item v-else index="/item">我的信息</el-menu-item>
        <el-menu-item index="/market">企业信息</el-menu-item>
        <el-menu-item index="/my-order">我的处理</el-menu-item>
        <el-menu-item v-if="!isAdmin" index="/sale-order">处理工单</el-menu-item>
        <el-menu-item index="/favorite">我的收藏</el-menu-item>
        <el-menu-item index="/my-complaint">我的申诉</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
        <el-menu-item index="/announcement">公告中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-button text @click="collapse = !collapse">{{ collapse ? '展开' : '收起' }}</el-button>
        <div class="right">
          <el-tag>{{ isAdmin ? '管理员' : '用户' }}</el-tag>
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-button type="danger" link @click="logout">退出</el-button>
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
import { logout as logoutApi } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const collapse = ref(false)
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')

const logout = async () => {
  try {
    await logoutApi()
  } catch (e) {
  }
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.aside {
  background: #1f2937;
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





