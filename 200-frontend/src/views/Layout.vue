<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">非遗工坊 200</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>非遗工坊课程预约与作品展销平台</strong><span>课程预约、工坊排期、作品展销与结算协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'CURATOR', 'SALES', 'VISITOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/workshop', label: '工坊档案', roles: ['ADMIN', 'WORKSHOP'] },
  { index: '/inheritor', label: '传承人档案', roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] },
  { index: '/course', label: '课程目录', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/schedule', label: '工坊排期', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/booking', label: '课程预约', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/review', label: '预约审核', roles: ['ADMIN', 'WORKSHOP', 'TEACHER'] },
  { index: '/checkin', label: '课程签到', roles: ['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR'] },
  { index: '/artwork', label: '作品档案', roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES'] },
  { index: '/showcase', label: '作品展销', roles: ['ADMIN', 'WORKSHOP', 'CURATOR', 'SALES', 'VISITOR'] },
  { index: '/order', label: '展销订单', roles: ['ADMIN', 'WORKSHOP', 'SALES', 'VISITOR'] },
  { index: '/settlement', label: '展销结算', roles: ['ADMIN', 'WORKSHOP', 'SALES'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
