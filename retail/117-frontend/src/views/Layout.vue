<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LOCAL VOUCHER 117</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>本地生活服务券核销与商户结算系统</strong><span>服务券、核销、结算、打款和申诉统计一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'MERCHANT', 'CASHIER', 'FINANCE'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/merchant', label: '商户档案', roles: ['ADMIN', 'MERCHANT'] },
  { index: '/store', label: '门店网点', roles: ['ADMIN', 'MERCHANT'] },
  { index: '/consumer', label: '用户档案', roles: ['ADMIN'] },
  { index: '/template', label: '券模板', roles: ['ADMIN'] },
  { index: '/activity', label: '营销活动', roles: ['ADMIN'] },
  { index: '/coupon', label: '用户领券', roles: ['ADMIN', 'MERCHANT'] },
  { index: '/verification', label: '核销记录', roles: ['ADMIN', 'MERCHANT', 'CASHIER'] },
  { index: '/settlement', label: '商户结算', roles: ['ADMIN', 'MERCHANT', 'FINANCE'] },
  { index: '/detail', label: '结算明细', roles: ['ADMIN', 'MERCHANT', 'FINANCE'] },
  { index: '/transfer', label: '打款记录', roles: ['ADMIN', 'MERCHANT', 'FINANCE'] },
  { index: '/complaint', label: '申诉工单', roles: ['ADMIN', 'MERCHANT', 'FINANCE'] },
  { index: '/statRecord', label: '活动统计', roles: ['ADMIN', 'FINANCE'] },
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
