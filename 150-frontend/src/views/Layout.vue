<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">OUTPATIENT 150</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>医院门诊检查预约与报告回传管理系统</strong><span>面向门诊检查全流程的预约、签到、报告回传、预警处理与患者服务一体化协同平台</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/item', label: '检查项目', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/patient', label: '患者档案', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/doctor', label: '医生档案', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/appointment', label: '检查预约', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/department', label: '检查科室', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/checkin', label: '签到记录', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/report', label: '检查报告', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/alert', label: '异常预警', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN'] },
  { index: '/delivery', label: '报告回传', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/advice', label: '复诊建议', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/queue', label: '队列叫号', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
  { index: '/notice', label: '通知公告', roles: ['ADMIN', 'DOCTOR', 'TECHNICIAN', 'PATIENT'] },
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
