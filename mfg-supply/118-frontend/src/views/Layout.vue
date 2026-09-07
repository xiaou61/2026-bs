<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">AGV WAREHOUSE 118</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智能仓储 AGV 任务调度与库位优化系统</strong><span>仓储区域、库位推荐、AGV调度、路径规划、出入库和维保告警一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'DISPATCHER', 'KEEPER', 'MAINTAINER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/zone', label: '仓库区域', roles: ['ADMIN'] },
  { index: '/location', label: '库位档案', roles: ['ADMIN', 'KEEPER'] },
  { index: '/agv', label: 'AGV车辆', roles: ['ADMIN', 'DISPATCHER', 'MAINTAINER'] },
  { index: '/station', label: '充电站点', roles: ['ADMIN', 'MAINTAINER'] },
  { index: '/inventory', label: '库存物料', roles: ['ADMIN', 'KEEPER'] },
  { index: '/inbound', label: '入库订单', roles: ['ADMIN', 'DISPATCHER', 'KEEPER'] },
  { index: '/outbound', label: '出库订单', roles: ['ADMIN', 'DISPATCHER', 'KEEPER'] },
  { index: '/task', label: 'AGV任务', roles: ['ADMIN', 'DISPATCHER', 'KEEPER'] },
  { index: '/route', label: '路径规划', roles: ['ADMIN', 'DISPATCHER'] },
  { index: '/recommendation', label: '库位推荐', roles: ['ADMIN', 'DISPATCHER', 'KEEPER'] },
  { index: '/maintenance', label: '设备维保', roles: ['ADMIN', 'MAINTAINER'] },
  { index: '/alert', label: '异常告警', roles: ['ADMIN', 'DISPATCHER', 'MAINTAINER'] },
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
