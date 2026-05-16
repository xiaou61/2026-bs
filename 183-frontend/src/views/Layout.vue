<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">实验动物 183</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>实验动物饲养排班与伦理审批管理系统</strong><span>饲养排班、伦理审批、实验登记、异常告警闭环协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'ETHICS', 'VET'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/room', label: '动物房间', roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] },
  { index: '/animal', label: '实验动物档案', roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET'] },
  { index: '/schedule', label: '饲养排班', roles: ['ADMIN', 'LAB', 'BREEDER'] },
  { index: '/feeding', label: '饲养记录', roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] },
  { index: '/ethics', label: '伦理申请', roles: ['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS'] },
  { index: '/review', label: '伦理审批', roles: ['ADMIN', 'ETHICS', 'RESEARCHER'] },
  { index: '/experiment', label: '实验登记', roles: ['ADMIN', 'LAB', 'RESEARCHER', 'ETHICS'] },
  { index: '/health', label: '健康巡检', roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] },
  { index: '/alert', label: '异常告警', roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER', 'VET'] },
  { index: '/treatment', label: '兽医处置', roles: ['ADMIN', 'LAB', 'BREEDER', 'VET'] },
  { index: '/material', label: '耗材使用', roles: ['ADMIN', 'LAB', 'BREEDER', 'RESEARCHER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
