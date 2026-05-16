<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">农机作业 189</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>乡镇农机作业预约与维修保养跟踪平台</strong><span>作业预约、机手调度、作业派单、维修保养闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER', 'MECHANIC', 'FARMER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/station', label: '农机站点', roles: ['ADMIN', 'STATION'] },
  { index: '/machine', label: '农机档案', roles: ['ADMIN', 'STATION', 'MECHANIC'] },
  { index: '/farmer', label: '农户档案', roles: ['ADMIN', 'STATION', 'DISPATCH', 'FARMER'] },
  { index: '/driver', label: '机手档案', roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER'] },
  { index: '/booking', label: '作业预约', roles: ['ADMIN', 'STATION', 'DISPATCH', 'FARMER'] },
  { index: '/dispatch', label: '机手调度', roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER'] },
  { index: '/workorder', label: '作业派单', roles: ['ADMIN', 'STATION', 'DISPATCH', 'DRIVER', 'FARMER'] },
  { index: '/completion', label: '作业回执', roles: ['ADMIN', 'STATION', 'DRIVER', 'FARMER'] },
  { index: '/maintenance', label: '维修保养', roles: ['ADMIN', 'STATION', 'MECHANIC', 'DRIVER'] },
  { index: '/repair', label: '维修跟踪', roles: ['ADMIN', 'STATION', 'MECHANIC', 'DRIVER'] },
  { index: '/season', label: '季节统计', roles: ['ADMIN', 'STATION', 'DISPATCH'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
