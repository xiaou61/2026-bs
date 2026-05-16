<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 138</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>在线考试反作弊行为分析与证据管理系统</strong><span>面向在线考试反作弊的计划编排、行为识别、证据留存、复核处理和告警通知一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/plan', label: '考试计划', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE'] },
  { index: '/invigilator', label: '监考档案', roles: ['ADMIN', 'INVIGILATOR'] },
  { index: '/candidate', label: '考生档案', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
  { index: '/session', label: '考试场次', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
  { index: '/behavior', label: '异常行为', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
  { index: '/evidence', label: '证据记录', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
  { index: '/task', label: '复核任务', roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] },
  { index: '/decision', label: '复核结论', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/rule', label: '预警规则', roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] },
  { index: '/device', label: '设备监测', roles: ['ADMIN', 'INVIGILATOR', 'REVIEWER'] },
  { index: '/appeal', label: '违规申诉', roles: ['ADMIN', 'CANDIDATE', 'REVIEWER'] },
  { index: '/notice', label: '告警通知', roles: ['ADMIN', 'INVIGILATOR', 'CANDIDATE', 'REVIEWER'] },
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




