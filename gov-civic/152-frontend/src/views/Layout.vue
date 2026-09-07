<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">危险作业 152</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>工厂危险作业审批与监护巡检管理系统</strong><span>作业票、审批链路、监护记录、隐患闭环一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '作业区域', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/hazard', label: '风险源台账', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/worker', label: '作业人员档案', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/work-ticket', label: '作业票申请', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/approval', label: '审批链路', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/briefing', label: '安全交底', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/guardian', label: '监护安排', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/monitor', label: '监护记录', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/danger', label: '隐患闭环', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/gas', label: '气体检测', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/plan', label: '应急预案', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SAFETY', 'APPROVER', 'GUARDIAN', 'WORKER'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
