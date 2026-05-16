<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">教培课消 158</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校外培训机构课消统计与退费审批系统</strong><span>排课签到、课消统计、退费审批、财务流水一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/branch', label: '校区档案', roles: ['ADMIN', 'PRINCIPAL'] },
  { index: '/course', label: '课程产品', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'FINANCE'] },
  { index: '/student', label: '学员档案', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'PARENT'] },
  { index: '/teacher', label: '教师档案', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] },
  { index: '/classgroup', label: '班级台账', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] },
  { index: '/schedule', label: '排课计划', roles: ['ADMIN', 'PRINCIPAL', 'ACADEMIC', 'TEACHER'] },
  { index: '/attendance', label: '上课考勤', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'PARENT'] },
  { index: '/consumption', label: '课消记录', roles: ['ADMIN', 'ACADEMIC', 'TEACHER', 'FINANCE', 'PARENT'] },
  { index: '/refund', label: '退费申请', roles: ['ADMIN', 'PRINCIPAL', 'FINANCE', 'PARENT'] },
  { index: '/approval', label: '退费审批', roles: ['ADMIN', 'PRINCIPAL', 'FINANCE'] },
  { index: '/ledger', label: '财务流水', roles: ['ADMIN', 'PRINCIPAL', 'FINANCE'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'PRINCIPAL'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
