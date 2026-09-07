<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">便民服务中心 195</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>便民服务中心事项预约与窗口评价平台</strong><span>事项预约、窗口叫号、办理进度与满意评价协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CENTER', 'WINDOW', 'REVIEW', 'SUPERVISE', 'CITIZEN'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/item', label: '事项档案', roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] },
  { index: '/window', label: '窗口档案', roles: ['ADMIN', 'CENTER', 'WINDOW'] },
  { index: '/roster', label: '人员排班', roles: ['ADMIN', 'CENTER', 'WINDOW'] },
  { index: '/appointment', label: '事项预约', roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] },
  { index: '/queue', label: '窗口叫号', roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] },
  { index: '/review', label: '材料审核', roles: ['ADMIN', 'CENTER', 'REVIEW', 'WINDOW'] },
  { index: '/progress', label: '办理进度', roles: ['ADMIN', 'CENTER', 'WINDOW', 'REVIEW', 'CITIZEN'] },
  { index: '/notice', label: '通知提醒', roles: ['ADMIN', 'CENTER', 'WINDOW', 'CITIZEN'] },
  { index: '/evaluation', label: '满意评价', roles: ['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN'] },
  { index: '/complaint', label: '投诉处理', roles: ['ADMIN', 'CENTER', 'SUPERVISE', 'CITIZEN'] },
  { index: '/performance', label: '绩效统计', roles: ['ADMIN', 'CENTER', 'SUPERVISE'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
