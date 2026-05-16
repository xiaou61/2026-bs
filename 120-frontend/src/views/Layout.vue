<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 120</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>数字孪生园区设备巡检管理系统</strong><span>基础档案、任务流转、异常处理、统计看板和审计日志一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER', 'MANAGER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/building', label: '园区楼宇', roles: ['ADMIN', 'MANAGER'] },
  { index: '/device', label: '孪生设备', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/route', label: '巡检路线', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] },
  { index: '/point', label: '巡检点位', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] },
  { index: '/task', label: '巡检任务', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] },
  { index: '/record', label: '巡检记录', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] },
  { index: '/defect', label: '缺陷报告', roles: ['ADMIN', 'INSPECTOR', 'ENGINEER'] },
  { index: '/workOrder', label: '维修工单', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/sensor', label: '传感数据', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/model', label: '孪生模型', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
  { index: '/energy', label: '能耗监测', roles: ['ADMIN', 'MANAGER'] },
  { index: '/schedule', label: '保养计划', roles: ['ADMIN', 'ENGINEER', 'MANAGER'] },
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
