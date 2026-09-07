<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 140</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>电子合同签署与印章审批管理系统</strong><span>面向电子合同签署管理的模板维护、用印审批、签署归档、到期提醒和风险条款一体化管理</span></div>
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
  { index: '/template', label: '合同模板', roles: ['ADMIN', 'LEGAL'] },
  { index: '/counterparty', label: '相对方档案', roles: ['ADMIN', 'LEGAL'] },
  { index: '/signer', label: '签署方档案', roles: ['ADMIN', 'LEGAL'] },
  { index: '/draft', label: '合同草稿', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/seal-apply', label: '用印申请', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/approval', label: '审批流程', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/signing', label: '合同签署', roles: ['ADMIN', 'LEGAL', 'APPROVER'] },
  { index: '/seal-record', label: '用印记录', roles: ['ADMIN', 'LEGAL'] },
  { index: '/archive', label: '归档记录', roles: ['ADMIN', 'LEGAL'] },
  { index: '/reminder', label: '到期提醒', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
  { index: '/risk', label: '风险条款', roles: ['ADMIN', 'LEGAL'] },
  { index: '/notice', label: '合同通知', roles: ['ADMIN', 'LEGAL', 'APPLICANT', 'APPROVER'] },
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






