<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">图书漂流 175</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园图书漂流借阅与读书打卡平台</strong><span>图书漂流、借阅流转、读书打卡、积分勋章一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/shelf', label: '漂流书架', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'CLUB'] },
  { index: '/book', label: '漂流图书', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB'] },
  { index: '/reader', label: '读者档案', roles: ['ADMIN', 'LIBRARY', 'TEACHER', 'STUDENT'] },
  { index: '/donation', label: '图书捐赠', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB'] },
  { index: '/borrow', label: '借阅记录', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT'] },
  { index: '/return', label: '归还流转', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT'] },
  { index: '/checkin', label: '读书打卡', roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/note', label: '读书笔记', roles: ['ADMIN', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/medal', label: '积分勋章', roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/activity', label: '阅读活动', roles: ['ADMIN', 'LIBRARY', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/notice', label: '消息通知', roles: ['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT', 'CLUB', 'TEACHER'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
