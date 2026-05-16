<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">证书培训 168</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>在线职业培训证书考试与续证提醒系统</strong><span>课程报名、学习进度、考试安排、证书发放、续证提醒一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE', 'EXAMINER', 'CERTIFIER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/course', label: '培训课程', roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] },
  { index: '/trainee', label: '学员档案', roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] },
  { index: '/instructor', label: '讲师档案', roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR'] },
  { index: '/enrollment', label: '课程报名', roles: ['ADMIN', 'TRAINING', 'TRAINEE'] },
  { index: '/progress', label: '学习进度', roles: ['ADMIN', 'TRAINING', 'INSTRUCTOR', 'TRAINEE'] },
  { index: '/exam', label: '考试安排', roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE'] },
  { index: '/score', label: '考试成绩', roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'INSTRUCTOR', 'TRAINEE'] },
  { index: '/certificate', label: '证书发放', roles: ['ADMIN', 'TRAINING', 'EXAMINER', 'CERTIFIER', 'TRAINEE'] },
  { index: '/ledger', label: '证书台账', roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] },
  { index: '/renewal', label: '续证申请', roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] },
  { index: '/reminder', label: '提醒通知', roles: ['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'TRAINING'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
