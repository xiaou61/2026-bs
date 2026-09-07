<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">物业报修 180</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>物业报修派单与服务满意度评价平台</strong><span>报修工单、派单分配、上门处理、费用支付和满意评价闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PROPERTY', 'OWNER', 'DISPATCH', 'WORKER', 'FINANCE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '小区区域', roles: ['ADMIN', 'PROPERTY', 'DISPATCH'] },
  { index: '/owner', label: '业主档案', roles: ['ADMIN', 'PROPERTY', 'OWNER', 'FINANCE'] },
  { index: '/category', label: '报修分类', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER'] },
  { index: '/repair', label: '报修工单', roles: ['ADMIN', 'PROPERTY', 'OWNER', 'DISPATCH', 'WORKER'] },
  { index: '/dispatch', label: '派单分配', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER'] },
  { index: '/service', label: '上门处理', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'WORKER', 'OWNER'] },
  { index: '/material', label: '物料使用', roles: ['ADMIN', 'PROPERTY', 'WORKER', 'FINANCE'] },
  { index: '/fee', label: '费用登记', roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'WORKER', 'OWNER'] },
  { index: '/payment', label: '支付记录', roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER'] },
  { index: '/review', label: '满意评价', roles: ['ADMIN', 'PROPERTY', 'OWNER', 'WORKER', 'FINANCE'] },
  { index: '/complaint', label: '投诉回访', roles: ['ADMIN', 'PROPERTY', 'FINANCE', 'OWNER', 'WORKER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
