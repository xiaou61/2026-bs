<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">PROJECT 197</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>社区家政服务预约与人员信用评价系统</strong><span>业务台账、状态流转、统计看板一体化管理平台</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
  </el-container>
</template>
<script setup>
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const menus = [
  { index: '/dashboard', label: '数据看板' },
  { index: '/user', label: '账号权限' },
  { index: '/record01', label: '服务预约' },
  { index: '/record02', label: '人员排班' },
  { index: '/record03', label: '信用评价' },
  { index: '/record04', label: '投诉处理' },
  { index: '/record05', label: '基础档案' },
  { index: '/record06', label: '人员档案' },
  { index: '/record07', label: '资源台账' },
  { index: '/record08', label: '服务申请' },
  { index: '/record09', label: '审批记录' },
  { index: '/record10', label: '执行记录' },
  { index: '/record11', label: '统计分析' },
  { index: '/record12', label: '通知公告' }
]
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
