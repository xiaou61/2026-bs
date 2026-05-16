<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 148</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>社区养老服务派单与健康随访管理系统</strong><span>面向社区养老服务管理的服务派单、上门照护、健康随访和预警提醒一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/package', label: '服务套餐', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/elder', label: '老人档案', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/caregiver', label: '护理档案', roles: ['ADMIN', 'CONSULTANT'] },
  { index: '/order', label: '服务工单', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/team', label: '服务团队', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER'] },
  { index: '/checkin', label: '上门签到', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER'] },
  { index: '/record', label: '服务记录', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/assessment', label: '健康评估', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/reminder', label: '用药提醒', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/family', label: '家属回访', roles: ['ADMIN', 'CONSULTANT', 'FAMILY'] },
  { index: '/alert', label: '预警事件', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'CONSULTANT', 'CAREGIVER', 'FAMILY'] },
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
