<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 131</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>药品不良反应上报与随访管理系统</strong><span>患者档案、药品目录、不良反应上报、风险评估、随访记录和病例复核一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/patient', label: '患者档案', roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] },
  { index: '/drug', label: '药品目录', roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] },
  { index: '/reporter', label: '上报人档案', roles: ['ADMIN', 'REPORTER'] },
  { index: '/report', label: '不良反应上报', roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] },
  { index: '/symptom', label: '反应症状', roles: ['ADMIN', 'REPORTER', 'REVIEWER', 'DOCTOR'] },
  { index: '/risk', label: '风险评估', roles: ['ADMIN', 'REVIEWER', 'DOCTOR'] },
  { index: '/plan', label: '随访计划', roles: ['ADMIN', 'DOCTOR'] },
  { index: '/followup', label: '随访记录', roles: ['ADMIN', 'DOCTOR', 'REVIEWER'] },
  { index: '/review', label: '病例复核', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/advice', label: '处置建议', roles: ['ADMIN', 'DOCTOR'] },
  { index: '/department', label: '科室信息', roles: ['ADMIN', 'REVIEWER'] },
  { index: '/statistic', label: '统计报表', roles: ['ADMIN', 'REVIEWER', 'DOCTOR'] },
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
