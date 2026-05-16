<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 119</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>设备备件寿命预测与维保决策系统</strong><span>基础档案、任务流转、异常处理、统计看板和审计日志一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER', 'ANALYST'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/asset', label: '设备资产', roles: ['ADMIN', 'DEVICE_ADMIN'] },
  { index: '/catalog', label: '备件目录', roles: ['ADMIN', 'DEVICE_ADMIN'] },
  { index: '/stock', label: '备件库存', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/inbound', label: '入库记录', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/outbound', label: '出库领用', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/usage', label: '使用记录', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/metric', label: '运行指标', roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] },
  { index: '/failure', label: '故障记录', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/prediction', label: '寿命预测', roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] },
  { index: '/plan', label: '维保计划', roles: ['ADMIN', 'DEVICE_ADMIN', 'MAINTAINER'] },
  { index: '/purchase', label: '采购申请', roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST'] },
  { index: '/warning', label: '风险预警', roles: ['ADMIN', 'DEVICE_ADMIN', 'ANALYST', 'MAINTAINER'] },
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
