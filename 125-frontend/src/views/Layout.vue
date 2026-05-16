<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 125</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智慧停车诱导与空位预测平台</strong><span>停车场、车位传感、预约导航、空位预测、故障报修和收费对账一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/lot', label: '停车场', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/area', label: '停车区域', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/space', label: '车位档案', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/sensor', label: '车位传感器', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/vehicle', label: '车主车辆', roles: ['ADMIN', 'OPERATOR', 'GUARD'] },
  { index: '/reservation', label: '预约订单', roles: ['ADMIN', 'OPERATOR', 'GUARD'] },
  { index: '/record', label: '停车记录', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/payment', label: '支付记录', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/prediction', label: '空位预测', roles: ['ADMIN', 'OPERATOR', 'ANALYST'] },
  { index: '/screen', label: '诱导屏幕', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/route', label: '导航路线', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
  { index: '/fault', label: '故障报修', roles: ['ADMIN', 'OPERATOR', 'GUARD', 'ANALYST'] },
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
