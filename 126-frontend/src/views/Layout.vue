<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 126</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>家庭能源用电分析与节能建议平台</strong><span>家庭档案、智能电表、设备用电、账单预算、异常预警和节能建议一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/home', label: '家庭档案', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/member', label: '家庭成员', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/meter', label: '智能电表', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/device', label: '用电设备', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/reading', label: '用电读数', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/bill', label: '电费账单', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/usage', label: '设备用电', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/budget', label: '能耗预算', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/suggestion', label: '节能建议', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/alert', label: '异常预警', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
  { index: '/carbon', label: '碳排统计', roles: ['ADMIN', 'RESIDENT', 'ANALYST'] },
  { index: '/ticket', label: '维修工单', roles: ['ADMIN', 'RESIDENT', 'ANALYST', 'MAINTAINER'] },
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
