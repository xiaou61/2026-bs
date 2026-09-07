<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CROSS BORDER 115</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>跨境电商清关订单与汇率结算平台</strong><span>订单清关、单证税费、汇率结算、物流对账一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'CUSTOMS', 'FINANCE', 'OPERATOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/merchant', label: '商家店铺', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/customer', label: '客户档案', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/sku', label: '商品SKU', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/order', label: '跨境订单', roles: ['ADMIN', 'CUSTOMS', 'FINANCE', 'OPERATOR'] },
  { index: '/declaration', label: '清关申报', roles: ['ADMIN', 'CUSTOMS', 'OPERATOR'] },
  { index: '/document', label: '清关单证', roles: ['ADMIN', 'CUSTOMS'] },
  { index: '/tax', label: '税费记录', roles: ['ADMIN', 'CUSTOMS', 'FINANCE'] },
  { index: '/rate', label: '汇率牌价', roles: ['ADMIN', 'FINANCE'] },
  { index: '/settlement', label: '结算账单', roles: ['ADMIN', 'FINANCE'] },
  { index: '/payment', label: '支付记录', roles: ['ADMIN', 'FINANCE'] },
  { index: '/logistics', label: '物流跟踪', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/reconciliation', label: '订单对账', roles: ['ADMIN', 'FINANCE'] },
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
