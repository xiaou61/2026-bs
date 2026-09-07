<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 144</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>无障碍出行路线规划与志愿协助平台</strong><span>面向无障碍出行服务管理的路线规划、志愿协助、行程记录和消息通知一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/route', label: '无障碍路线', roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/facility', label: '设施点位', roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/traveler', label: '出行用户', roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] },
  { index: '/request', label: '协助预约', roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] },
  { index: '/volunteer', label: '志愿者档案', roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/plan', label: '路线方案', roles: ['ADMIN', 'DISPATCHER'] },
  { index: '/task', label: '协助任务', roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/checkin', label: '服务签到', roles: ['ADMIN', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/feedback', label: '评价反馈', roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] },
  { index: '/contact', label: '应急联系人', roles: ['ADMIN', 'TRAVELER'] },
  { index: '/trip', label: '行程记录', roles: ['ADMIN', 'TRAVELER', 'DISPATCHER'] },
  { index: '/notice', label: '消息通知', roles: ['ADMIN', 'TRAVELER', 'VOLUNTEER', 'DISPATCHER'] },
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
