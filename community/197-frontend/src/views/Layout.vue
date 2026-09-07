<template>
  <el-container class="layout">
    <el-aside width="240px">
      <div class="logo">
        <span class="logo-icon">🧹</span>
        <div class="logo-text">
          <strong>家政信用服务</strong>
          <span>社区家政预约评价</span>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="sidebar-menu" background-color="#E65100" text-color="#FFE0B2" active-text-color="#FFD54F">
        <el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">
          <span class="menu-icon">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <strong>社区家政服务预约与人员信用评价系统</strong>
          <span>服务预约、人员排班、上门记录与信用评价协同</span>
        </div>
        <div class="user-box">
          <el-tag type="warning" effect="dark">{{ userStore.user?.role }}</el-tag>
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
  { index: '/dashboard', label: '数据看板', icon: '📊', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY', 'RESIDENT'] },
  { index: '/user', label: '账号权限', icon: '👤', roles: ['ADMIN'] },
  { index: '/station', label: '服务站点', icon: '🏠', roles: ['ADMIN', 'AGENCY'] },
  { index: '/resident', label: '居民档案', icon: '👨‍👩‍👧', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/worker', label: '人员档案', icon: '👷', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'QUALITY'] },
  { index: '/service', label: '服务项目', icon: '🧼', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/booking', label: '服务预约', icon: '📅', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'RESIDENT'] },
  { index: '/review', label: '预约审核', icon: '✅', roles: ['ADMIN', 'AGENCY', 'DISPATCH'] },
  { index: '/dispatch', label: '人员排班', icon: '📋', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER'] },
  { index: '/record', label: '上门记录', icon: '🚪', roles: ['ADMIN', 'AGENCY', 'DISPATCH', 'WORKER', 'RESIDENT'] },
  { index: '/evaluation', label: '信用评价', icon: '⭐', roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] },
  { index: '/complaint', label: '投诉处理', icon: '⚠️', roles: ['ADMIN', 'AGENCY', 'QUALITY', 'RESIDENT'] },
  { index: '/settlement', label: '费用结算', icon: '💰', roles: ['ADMIN', 'AGENCY', 'DISPATCH'] },
  { index: '/log', label: '操作日志', icon: '📝', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
<style scoped>
.layout { height: 100vh; }
.layout .el-aside {
  background: linear-gradient(180deg, #E65100 0%, #F57C00 100%);
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
.logo-text span { color: #FFE0B2; font-size: 11px; }
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
  background: linear-gradient(135deg, #FF9800, #FFB74D) !important;
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
  box-shadow: 0 2px 8px rgba(230, 81, 0, 0.08);
  border-bottom: 2px solid #FFF3E0;
}
.header-left strong {
  color: #E65100;
  font-size: 16px;
  letter-spacing: 1px;
}
.header-left span {
  color: #F57C00;
  font-size: 12px;
  margin-left: 12px;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
}
.user-name { color: #E65100; font-weight: 500; }
.layout .el-main {
  background: #FFF8F0;
  padding: 20px;
  overflow-y: auto;
}
</style>
