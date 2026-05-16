<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 124</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>电动车充电桩预约与运维管理系统</strong><span>站点桩位、预约充电、故障报修、维修工单、收益统计和能耗监测一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/station', label: '充电站点', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] },
  { index: '/pile', label: '充电桩位', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] },
  { index: '/vehicle', label: '用户车辆', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/appointment', label: '预约订单', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/session', label: '充电记录', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/fault', label: '故障报修', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER', 'OWNER'] },
  { index: '/repair', label: '维修工单', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] },
  { index: '/plan', label: '保养计划', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] },
  { index: '/price', label: '电价策略', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/payment', label: '支付记录', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/revenue', label: '收益统计', roles: ['ADMIN', 'OPERATOR', 'OWNER'] },
  { index: '/energy', label: '能耗监测', roles: ['ADMIN', 'OPERATOR', 'MAINTAINER'] },
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
