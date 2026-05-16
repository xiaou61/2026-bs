<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">宿舍能耗 156</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园宿舍能耗监测与节能排名系统</strong><span>能耗监测、异常预警、节能任务、节能排名一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/building', label: '宿舍楼栋', roles: ['ADMIN', 'LOGISTICS'] },
  { index: '/room', label: '宿舍房间', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'STUDENT'] },
  { index: '/meter', label: '能耗表计', roles: ['ADMIN', 'LOGISTICS', 'ENERGY'] },
  { index: '/reading', label: '用电读数', roles: ['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT'] },
  { index: '/bill', label: '能耗账单', roles: ['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT'] },
  { index: '/rule', label: '预警策略', roles: ['ADMIN', 'LOGISTICS', 'ENERGY'] },
  { index: '/alert', label: '异常预警', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY'] },
  { index: '/task', label: '节能任务', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] },
  { index: '/ranking', label: '节能排行', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT'] },
  { index: '/inspection', label: '巡查记录', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'LOGISTICS', 'COUNSELOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'LOGISTICS'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
