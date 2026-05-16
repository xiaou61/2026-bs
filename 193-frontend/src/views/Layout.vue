<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">创新实验班 193</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园创新实验班选拔与导师跟踪管理系统</strong><span>报名选拔、导师匹配、培养计划与过程跟踪协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'MENTOR', 'COUNSELOR', 'STUDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/program', label: '实验班项目', roles: ['ADMIN', 'ACADEMIC'] },
  { index: '/student', label: '学生档案', roles: ['ADMIN', 'ACADEMIC', 'COUNSELOR', 'STUDENT'] },
  { index: '/mentor', label: '导师档案', roles: ['ADMIN', 'ACADEMIC', 'MENTOR'] },
  { index: '/notice', label: '选拔公告', roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'STUDENT'] },
  { index: '/application', label: '报名选拔', roles: ['ADMIN', 'ACADEMIC', 'REVIEWER', 'COUNSELOR', 'STUDENT'] },
  { index: '/review', label: '资格评审', roles: ['ADMIN', 'ACADEMIC', 'REVIEWER'] },
  { index: '/interview', label: '面试考核', roles: ['ADMIN', 'ACADEMIC', 'REVIEWER'] },
  { index: '/match', label: '导师匹配', roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT'] },
  { index: '/plan', label: '培养计划', roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR', 'STUDENT'] },
  { index: '/tracking', label: '过程跟踪', roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'COUNSELOR'] },
  { index: '/achievement', label: '成果档案', roles: ['ADMIN', 'ACADEMIC', 'MENTOR', 'STUDENT'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
