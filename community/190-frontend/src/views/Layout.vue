<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">楼宇访修 190</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>智慧楼宇访修协同与设备保养提醒系统</strong><span>访修工单、维修派工、设备保养与故障预警协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'INSPECTOR', 'TENANT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/building', label: '楼宇档案', roles: ['ADMIN', 'PROPERTY', 'INSPECTOR'] },
  { index: '/equipment', label: '设备档案', roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] },
  { index: '/tenant', label: '入驻档案', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TENANT'] },
  { index: '/ticket', label: '访修工单', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'TENANT'] },
  { index: '/repair', label: '维修派工', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN'] },
  { index: '/plan', label: '保养计划', roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] },
  { index: '/task', label: '保养任务', roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] },
  { index: '/alert', label: '故障预警', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TECHNICIAN', 'INSPECTOR'] },
  { index: '/inspection', label: '巡检记录', roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR'] },
  { index: '/spare', label: '备件库存', roles: ['ADMIN', 'PROPERTY', 'TECHNICIAN'] },
  { index: '/feedback', label: '服务评价', roles: ['ADMIN', 'PROPERTY', 'DISPATCH', 'TENANT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
