<template>
  <el-container class="layout">
    <el-aside width="240px">
      <div class="logo">
        <span class="logo-icon">🏃</span>
        <div class="logo-text">
          <strong>运动康复</strong>
          <span>训练计划与体测评估</span>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="sidebar-menu" background-color="#1B5E20" text-color="#C8E6C9" active-text-color="#FFD54F">
        <el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">
          <span class="menu-icon">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <strong>运动康复训练计划与体测评估管理系统</strong>
          <span>体测评估、风险提醒、训练计划与康复反馈协同</span>
        </div>
        <div class="user-box">
          <el-tag type="success" effect="dark">{{ userStore.user?.role }}</el-tag>
          <span class="user-name">{{ userStore.user?.nickname }}</span>
          <el-button link type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>
<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
  { index: '/dashboard', label: '数据看板', icon: '📊', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/user', label: '账号权限', icon: '👤', roles: ['ADMIN'] },
  { index: '/center', label: '康复中心', icon: '🏥', roles: ['ADMIN', 'CENTER'] },
  { index: '/member', label: '会员档案', icon: '👥', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/coach', label: '教练档案', icon: '🏋️', roles: ['ADMIN', 'CENTER', 'COACH'] },
  { index: '/item', label: '体测项目', icon: '📋', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST'] },
  { index: '/assessment', label: '体测评估', icon: '📈', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] },
  { index: '/risk', label: '风险提醒', icon: '⚠️', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'COACH'] },
  { index: '/plan', label: '训练计划', icon: '📝', roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/session', label: '训练安排', icon: '📅', roles: ['ADMIN', 'CENTER', 'COACH', 'THERAPIST', 'MEMBER'] },
  { index: '/checkin', label: '训练打卡', icon: '✅', roles: ['ADMIN', 'CENTER', 'COACH', 'MEMBER'] },
  { index: '/feedback', label: '康复反馈', icon: '💬', roles: ['ADMIN', 'CENTER', 'THERAPIST', 'COACH', 'MEMBER'] },
  { index: '/reassessment', label: '复评记录', icon: '🔄', roles: ['ADMIN', 'CENTER', 'ASSESSOR', 'THERAPIST', 'MEMBER'] },
  { index: '/log', label: '操作日志', icon: '📝', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
<style scoped>
.layout { height: 100vh; }
.layout .el-aside {
  background: linear-gradient(180deg, #1B5E20 0%, #2E7D32 100%);
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.15);
  overflow-y: auto;
}
.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 20px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}
.logo-icon { font-size: 32px; }
.logo-text { display: flex; flex-direction: column; }
.logo-text strong { color: #FFD54F; font-size: 16px; letter-spacing: 2px; }
.logo-text span { color: #A5D6A7; font-size: 11px; }
.sidebar-menu {
  border-right: none !important;
  padding: 8px 0;
}
.sidebar-menu .el-menu-item {
  height: 48px;
  line-height: 48px;
  margin: 2px 8px;
  border-radius: 8px;
  transition: all 0.3s ease;
}
.sidebar-menu .el-menu-item:hover {
  background: rgba(255, 255, 255, 0.1) !important;
}
.sidebar-menu .el-menu-item.is-active {
  background: linear-gradient(135deg, #388E3C, #43A047) !important;
  color: #FFD54F !important;
  font-weight: 600;
}
.menu-icon { margin-right: 10px; font-size: 18px; }
.layout .el-header {
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.08);
  border-bottom: 2px solid #E8F5E9;
}
.header-left strong {
  color: #1B5E20;
  font-size: 16px;
  letter-spacing: 1px;
}
.header-left span {
  color: #4CAF50;
  font-size: 12px;
  margin-left: 12px;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
}
.user-name { color: #1B5E20; font-weight: 500; }
.layout .el-main {
  background: #F1F8E9;
  padding: 20px;
  overflow-y: auto;
}
</style>
