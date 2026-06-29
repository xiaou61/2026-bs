<template>
  <el-container class="layout">
    <el-aside width="240px">
      <div class="logo">
        <span class="logo-icon">💊</span>
        <div class="logo-text">
          <strong>药店慢病管理</strong>
          <span>处方审核与续方提醒</span>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="sidebar-menu" background-color="#0077B6" text-color="#CAF0F8" active-text-color="#FFD54F">
        <el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">
          <span class="menu-icon">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <strong>药店处方审核与慢病续方提醒管理系统</strong>
          <span>处方审核、购药记录、续方提醒与风险复核协同</span>
        </div>
        <div class="user-box">
          <el-tag type="primary" effect="dark">{{ userStore.user?.role }}</el-tag>
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
  { index: '/dashboard', label: '数据看板', icon: '📊', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/user', label: '账号权限', icon: '👤', roles: ['ADMIN'] },
  { index: '/store', label: '药店门店', icon: '🏥', roles: ['ADMIN', 'PHARMACY'] },
  { index: '/customer', label: '顾客档案', icon: '🤒', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/medicine', label: '药品目录', icon: '💊', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK'] },
  { index: '/prescription', label: '处方登记', icon: '📋', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] },
  { index: '/review', label: '处方审核', icon: '🔍', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] },
  { index: '/risk', label: '风险复核', icon: '⚠️', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST'] },
  { index: '/purchase', label: '购药记录', icon: '🛒', roles: ['ADMIN', 'PHARMACY', 'CLERK', 'CUSTOMER'] },
  { index: '/guide', label: '用药指导', icon: '📖', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'CLERK', 'CUSTOMER'] },
  { index: '/plan', label: '慢病方案', icon: '📑', roles: ['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/reminder', label: '续方提醒', icon: '⏰', roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/followup', label: '随访记录', icon: '📞', roles: ['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER'] },
  { index: '/log', label: '操作日志', icon: '📝', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
<style scoped>
.layout { height: 100vh; }
.layout .el-aside {
  background: linear-gradient(180deg, #0077B6 0%, #0096C7 100%);
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
.logo-text span { color: #CAF0F8; font-size: 11px; }
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
  background: linear-gradient(135deg, #00B4D8, #48CAE4) !important;
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
  box-shadow: 0 2px 8px rgba(0, 119, 182, 0.08);
  border-bottom: 2px solid #E0F7FA;
}
.header-left strong {
  color: #0077B6;
  font-size: 16px;
  letter-spacing: 1px;
}
.header-left span {
  color: #00B4D8;
  font-size: 12px;
  margin-left: 12px;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
}
.user-name { color: #0077B6; font-weight: 500; }
.layout .el-main {
  background: #F0F9FF;
  padding: 20px;
  overflow-y: auto;
}
</style>
