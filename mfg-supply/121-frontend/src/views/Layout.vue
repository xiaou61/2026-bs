<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 121</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>无人机巡检任务调度与缺陷上报平台</strong><span>航线规划、任务派发、飞行记录、缺陷整改和风险看板一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'PILOT', 'ENGINEER', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/drone', label: '无人机设备', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/pilot', label: '飞手档案', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/zone', label: '巡检区域', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/route', label: '航线规划', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/task', label: '巡检任务', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/flight', label: '飞行记录', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/defect', label: '缺陷报告', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/image', label: '缺陷图片', roles: ['ADMIN', 'PILOT', 'ENGINEER'] },
  { index: '/rectify', label: '整改工单', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/station', label: '电池站点', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/maintenance', label: '维保记录', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
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
