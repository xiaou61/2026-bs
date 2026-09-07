<template>
  <el-container class="layout">
    <el-aside width="240px">
      <div class="logo">
        <span class="logo-icon">🎭</span>
        <div class="logo-text">
          <strong>非遗工坊</strong>
          <span>课程预约与作品展销</span>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="sidebar-menu" background-color="#5D4037" text-color="#EFEBE9" active-text-color="#FFD54F">
        <el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">
          <span class="menu-icon">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <strong>非遗工坊课程预约与作品展销平台</strong>
          <span>课程预约、工坊排期、作品展销与结算协同</span>
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
  { index: '/dashboard', label: '数据看板', icon: '📊', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'CURATOR', 'SALES', 'VISITOR'] },
  { index: '/user', label: '账号权限', icon: '👤', roles: ['ADMIN'] },
  { index: '/workshop', label: '工坊档案', icon: '🏛️', roles: ['ADMIN', 'WORKSHOP'] },
  { index: '/inheritor', label: '传承人档案', icon: '👨‍🏫', roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] },
  { index: '/course', label: '课程目录', icon: '📚', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/schedule', label: '工坊排期', icon: '📅', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/booking', label: '课程预约', icon: '🎫', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/review', label: '预约审核', icon: '✅', roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] },
  { index: '/checkin', label: '课程签到', icon: '📋', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/artwork', label: '作品档案', icon: '🎨', roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES'] },
  { index: '/showcase', label: '作品展销', icon: '🖼️', roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES', 'VISITOR'] },
  { index: '/order', label: '展销订单', icon: '🛒', roles: ['ADMIN', 'WORKSHOP', 'SALES', 'VISITOR'] },
  { index: '/settlement', label: '展销结算', icon: '💰', roles: ['ADMIN', 'WORKSHOP', 'SALES'] },
  { index: '/log', label: '操作日志', icon: '📝', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
<style scoped>
.layout { height: 100vh; }
.layout .el-aside {
  background: linear-gradient(180deg, #5D4037 0%, #4E342E 100%);
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
.logo-text span { color: #BCAAA4; font-size: 11px; }
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
  background: linear-gradient(135deg, #8B4513, #A0522D) !important;
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
  box-shadow: 0 2px 8px rgba(139, 69, 19, 0.08);
  border-bottom: 2px solid #EFEBE9;
}
.header-left strong {
  color: #5D4037;
  font-size: 16px;
  letter-spacing: 1px;
}
.header-left span {
  color: #8D6E63;
  font-size: 12px;
  margin-left: 12px;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
}
.user-name { color: #5D4037; font-weight: 500; }
.layout .el-main {
  background: #FAF6F1;
  padding: 20px;
  overflow-y: auto;
}
</style>
