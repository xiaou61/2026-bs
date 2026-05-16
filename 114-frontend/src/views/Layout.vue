<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">COLD CHAIN 114</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>供应链冷链温控追踪与异常预警平台</strong><span>温控记录、运输轨迹、异常告警、责任追溯闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/warehouse', label: '冷链仓点', roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] },
  { index: '/carrier', label: '承运企业', roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] },
  { index: '/device', label: '温控设备', roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] },
  { index: '/cargo', label: '冷链货品', roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] },
  { index: '/order', label: '运输订单', roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] },
  { index: '/temperature', label: '温控记录', roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] },
  { index: '/track', label: '运输轨迹', roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] },
  { index: '/rule', label: '告警规则', roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] },
  { index: '/alert', label: '异常告警', roles: ['ADMIN', 'DISPATCHER', 'CARRIER', 'SUPERVISOR'] },
  { index: '/task', label: '处置任务', roles: ['ADMIN', 'DISPATCHER', 'SUPERVISOR'] },
  { index: '/responsibility', label: '责任追溯', roles: ['ADMIN', 'SUPERVISOR'] },
  { index: '/maintenance', label: '设备维护', roles: ['ADMIN', 'CARRIER', 'SUPERVISOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'SUPERVISOR'] }
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
