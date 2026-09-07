<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 133</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>实验室耗材采购审批与库存预警系统</strong><span>耗材目录、采购申请、审批订单、出入库台账、库存盘点和预警提醒一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/catalog', label: '耗材目录', roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] },
  { index: '/supplier', label: '供应商档案', roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] },
  { index: '/lab', label: '实验室', roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] },
  { index: '/stock', label: '库存台账', roles: ['ADMIN', 'KEEPER', 'APPROVER'] },
  { index: '/request', label: '采购申请', roles: ['ADMIN', 'TEACHER', 'APPROVER'] },
  { index: '/approval', label: '采购审批', roles: ['ADMIN', 'APPROVER'] },
  { index: '/order', label: '采购订单', roles: ['ADMIN', 'KEEPER', 'APPROVER'] },
  { index: '/inbound', label: '入库记录', roles: ['ADMIN', 'KEEPER', 'APPROVER'] },
  { index: '/outbound', label: '领用记录', roles: ['ADMIN', 'KEEPER', 'APPROVER'] },
  { index: '/check', label: '库存盘点', roles: ['ADMIN', 'KEEPER'] },
  { index: '/rule', label: '预警规则', roles: ['ADMIN', 'KEEPER'] },
  { index: '/warning', label: '库存预警', roles: ['ADMIN', 'KEEPER', 'TEACHER', 'APPROVER'] },
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
