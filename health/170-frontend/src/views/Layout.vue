<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">养老护理 170</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>养老机构床位分配与护理记录管理系统</strong><span>入住评估、床位分配、护理记录、家属查询一体化协同</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/area', label: '护理区域', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] },
  { index: '/room', label: '房间档案', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] },
  { index: '/bed', label: '床位档案', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] },
  { index: '/elder', label: '老人档案', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] },
  { index: '/family', label: '家属档案', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'FAMILY'] },
  { index: '/assessment', label: '入住评估', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] },
  { index: '/allocation', label: '床位分配', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR'] },
  { index: '/plan', label: '护理计划', roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'DIRECTOR'] },
  { index: '/care', label: '护理记录', roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] },
  { index: '/query', label: '家属查询', roles: ['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] },
  { index: '/exception', label: '异常上报', roles: ['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'DIRECTOR'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
