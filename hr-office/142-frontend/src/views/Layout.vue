<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 142</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>车辆保险理赔材料审核与进度跟踪系统</strong><span>面向车辆保险理赔管理的报案受理、材料审核、定损赔付和进度跟踪一体化管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/policy', label: '保险保单', roles: ['ADMIN', 'LEGAL'] },
  { index: '/vehicle', label: '车辆档案', roles: ['ADMIN', 'LEGAL'] },
  { index: '/customer', label: '客户档案', roles: ['ADMIN', 'LEGAL'] },
  { index: '/claim', label: '理赔申请', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/accident', label: '事故报案', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/material', label: '材料清单', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/review', label: '材料审核', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/assessment', label: '定损记录', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/compensation', label: '赔付记录', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/progress', label: '进度跟踪', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/followup', label: '回访记录', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/notice', label: '消息通知', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
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

