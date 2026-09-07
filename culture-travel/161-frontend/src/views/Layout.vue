<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">景区寻回 161</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>景区失物招领与游客寻回协同平台</strong><span>失物登记、游客认领、身份核验、归还交接一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC', 'BROADCAST', 'VISITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '景区区域', roles: ['ADMIN', 'SCENIC', 'SERVICE'] },
  { index: '/lost', label: '失物登记', roles: ['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR'] },
  { index: '/found', label: '拾物上报', roles: ['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR'] },
  { index: '/claim', label: '游客认领', roles: ['ADMIN', 'SERVICE', 'VISITOR'] },
  { index: '/verify', label: '身份核验', roles: ['ADMIN', 'SERVICE'] },
  { index: '/trace', label: '位置追踪', roles: ['ADMIN', 'SECURITY', 'SCENIC', 'SERVICE'] },
  { index: '/custody', label: '暂存保管', roles: ['ADMIN', 'SERVICE', 'SECURITY'] },
  { index: '/notice', label: '通知协同', roles: ['ADMIN', 'SERVICE', 'BROADCAST', 'SCENIC'] },
  { index: '/handover', label: '归还交接', roles: ['ADMIN', 'SERVICE', 'VISITOR'] },
  { index: '/search', label: '寻回任务', roles: ['ADMIN', 'SERVICE', 'SECURITY', 'SCENIC'] },
  { index: '/feedback', label: '回访评价', roles: ['ADMIN', 'SERVICE', 'VISITOR', 'BROADCAST'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SCENIC'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
