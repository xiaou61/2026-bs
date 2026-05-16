<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 135</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>学术会议投稿评审与日程管理系统</strong><span>面向会务管理的征稿发布、论文盲审、录用通知、会场排期与签到服务一体化管理</span></div>
        <div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div>
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/conference', label: '会议信息', roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] },
  { index: '/call', label: '征稿通知', roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] },
  { index: '/author', label: '作者档案', roles: ['ADMIN', 'MANAGER', 'REVIEWER', 'SECRETARY'] },
  { index: '/paper', label: '论文投稿', roles: ['ADMIN', 'MANAGER', 'REVIEWER'] },
  { index: '/reviewer', label: '审稿专家', roles: ['ADMIN', 'MANAGER', 'REVIEWER'] },
  { index: '/assignment', label: '审稿分配', roles: ['ADMIN', 'MANAGER'] },
  { index: '/review', label: '盲审记录', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/notice', label: '录用通知', roles: ['ADMIN', 'MANAGER', 'SECRETARY'] },
  { index: '/guest', label: '参会报名', roles: ['ADMIN', 'MANAGER', 'SECRETARY'] },
  { index: '/venue', label: '会场安排', roles: ['ADMIN', 'SECRETARY'] },
  { index: '/agenda', label: '会议日程', roles: ['ADMIN', 'SECRETARY'] },
  { index: '/checkin', label: '签到记录', roles: ['ADMIN', 'SECRETARY'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN'] }
]
const visibleMenus = computed(() => {
  const role = userStore.user?.role
  return menus.filter(item => item.roles.includes(role))
})
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>


