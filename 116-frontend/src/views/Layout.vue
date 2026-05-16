<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LIVE COMMERCE 116</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>直播电商选品排期与售后工单系统</strong><span>选品排期、直播订单、售后工单、退款绩效一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'OPERATOR', 'SERVICE', 'MERCHANT'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/channel', label: '直播渠道', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/anchor', label: '主播档案', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/supplier', label: '供应商品牌', roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] },
  { index: '/selection', label: '选品池', roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] },
  { index: '/session', label: '直播场次', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/schedule', label: '排期计划', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/sample', label: '样品申请', roles: ['ADMIN', 'OPERATOR', 'MERCHANT'] },
  { index: '/script', label: '话术脚本', roles: ['ADMIN', 'OPERATOR'] },
  { index: '/order', label: '直播订单', roles: ['ADMIN', 'OPERATOR', 'SERVICE'] },
  { index: '/ticket', label: '售后工单', roles: ['ADMIN', 'SERVICE'] },
  { index: '/refund', label: '退款记录', roles: ['ADMIN', 'SERVICE'] },
  { index: '/performance', label: '主播绩效', roles: ['ADMIN', 'OPERATOR'] },
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
