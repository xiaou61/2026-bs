<template>
  <el-container class="layout">
    <el-aside width="240px">
      <div class="logo">
        <span class="logo-icon">🔋</span>
        <div class="logo-text">
          <strong>充电宝运营</strong>
          <span>投放巡检与收益结算</span>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="sidebar-menu" background-color="#01579B" text-color="#B3E5FC" active-text-color="#FFD54F">
        <el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">
          <span class="menu-icon">{{ item.icon }}</span>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <strong>城市共享充电宝投放巡检与收益结算系统</strong>
          <span>点位投放、设备巡检、异常回收与收益结算协同</span>
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
  { index: '/dashboard', label: '数据看板', icon: '📊', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR', 'FINANCE', 'MERCHANT'] },
  { index: '/user', label: '账号权限', icon: '👤', roles: ['ADMIN'] },
  { index: '/site', label: '投放点位', icon: '📍', roles: ['ADMIN', 'OPERATOR', 'SITE', 'MERCHANT'] },
  { index: '/cabinet', label: '设备柜档案', icon: '🗄️', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] },
  { index: '/device', label: '充电宝档案', icon: '🔋', roles: ['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR'] },
  { index: '/plan', label: '点位投放', icon: '🚀', roles: ['ADMIN', 'OPERATOR', 'SITE'] },
  { index: '/inspection', label: '设备巡检', icon: '🔍', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/repair', label: '故障维修', icon: '🔧', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/recycle', label: '异常回收', icon: '♻️', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR', 'SITE'] },
  { index: '/order', label: '租借订单', icon: '📱', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/income', label: '商户收益', icon: '💵', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/settlement', label: '收益结算', icon: '💰', roles: ['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT'] },
  { index: '/transfer', label: '库存调拨', icon: '🚚', roles: ['ADMIN', 'OPERATOR', 'INSPECTOR'] },
  { index: '/log', label: '操作日志', icon: '📝', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
<style scoped>
.layout { height: 100vh; }
.layout .el-aside {
  background: linear-gradient(180deg, #01579B 0%, #0277BD 100%);
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
.logo-text span { color: '#B3E5FC'; font-size: 11px; }
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
  background: linear-gradient(135deg, #0288D1, #29B6F6) !important;
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
  box-shadow: 0 2px 8px rgba(2, 119, 189, 0.08);
  border-bottom: 2px solid #E1F5FE;
}
.header-left strong {
  color: #01579B;
  font-size: 16px;
  letter-spacing: 1px;
}
.header-left span {
  color: #0288D1;
  font-size: 12px;
  margin-left: 12px;
}
.user-box {
  display: flex;
  align-items: center;
  gap: 12px;
}
.user-name { color: #01579B; font-weight: 500; }
.layout .el-main {
  background: #F0F9FF;
  padding: 20px;
  overflow-y: auto;
}
</style>
