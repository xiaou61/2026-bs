<template>
  <el-container style="height: 100vh">
    <el-aside :width="collapse ? '68px' : '240px'" class="aside">
      <div class="logo">{{ collapse ? '医' : '线上医院挂号系统' }}</div>
      <el-menu :default-active="$route.path" :collapse="collapse" router background-color="#0f172a" text-color="#cbd5e1" active-text-color="#38bdf8">
        <el-menu-item index="/dashboard">首页看板</el-menu-item>
        <template v-if="role === 'ADMIN'">
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/department">科室管理</el-menu-item>
          <el-menu-item index="/doctor">医生管理</el-menu-item>
          <el-menu-item index="/schedule">排班管理</el-menu-item>
          <el-menu-item index="/appointment">挂号管理</el-menu-item>
          <el-menu-item index="/order">订单管理</el-menu-item>
          <el-menu-item index="/review">评价管理</el-menu-item>
          <el-menu-item index="/banner">轮播管理</el-menu-item>
          <el-menu-item index="/statistics">统计分析</el-menu-item>
        </template>
        <template v-else-if="role === 'DOCTOR'">
          <el-menu-item index="/my-schedule">我的排班</el-menu-item>
          <el-menu-item index="/doctor-appointment">预约患者</el-menu-item>
        </template>
        <template v-else>
          <el-menu-item index="/doctor-market">医生大厅</el-menu-item>
          <el-menu-item index="/my-appointment">我的挂号</el-menu-item>
          <el-menu-item index="/my-order">支付订单</el-menu-item>
          <el-menu-item index="/card">就诊卡</el-menu-item>
        </template>
        <el-menu-item index="/notice">公告中心</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button text @click="collapse = !collapse">{{ collapse ? '展开' : '收起' }}</el-button>
          <span class="welcome">欢迎使用线上医院挂号系统</span>
        </div>
        <div class="right">
          <el-tag type="success">{{ roleLabel }}</el-tag>
          <span>{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-button type="danger" link @click="handleLogout">退出</el-button>
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
const role = computed(() => userStore.user?.role)
const roleLabel = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'DOCTOR') return '医生'
  return '患者'
})

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
  background: linear-gradient(180deg, #0f172a 0%, #111827 100%);
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

.header-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.welcome {
  color: #475569;
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.main {
  background: #f8fafc;
  padding: 14px;
}
</style>
