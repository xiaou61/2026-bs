<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 122</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智慧工地安全巡检与隐患整改系统</strong><span>工地项目、巡检计划、隐患上报、整改验收和安全培训一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'INSPECTOR', 'TEAM_LEADER', 'SUPERVISOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/project', label: '工地项目', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/team', label: '施工班组', roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] },
  { index: '/inspector', label: '安全员档案', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/plan', label: '巡检计划', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/task', label: '巡检任务', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/hazard', label: '隐患上报', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/rectify', label: '整改工单', roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] },
  { index: '/acceptance', label: '验收记录', roles: ['ADMIN', 'SUPERVISOR'] },
  { index: '/training', label: '安全培训', roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] },
  { index: '/equipment', label: '设备检查', roles: ['ADMIN', 'INSPECTOR', 'SUPERVISOR'] },
  { index: '/supply', label: '防护用品', roles: ['ADMIN', 'TEAM_LEADER', 'SUPERVISOR'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'SUPERVISOR'] },
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
