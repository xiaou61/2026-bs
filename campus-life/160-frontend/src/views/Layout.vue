<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">社团财资 160</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>校园社团活动预算报销与物资借用系统</strong><span>活动立项、预算报销、票据归档、物资借还一体化管理</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'WAREHOUSE', 'MEMBER'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/club', label: '社团档案', roles: ['ADMIN', 'UNION'] },
  { index: '/member', label: '成员档案', roles: ['ADMIN', 'UNION', 'CLUB'] },
  { index: '/activity', label: '活动立项', roles: ['ADMIN', 'UNION', 'CLUB', 'MEMBER'] },
  { index: '/budget', label: '预算申请', roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER'] },
  { index: '/lineitem', label: '预算明细', roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER'] },
  { index: '/approval', label: '审批记录', roles: ['ADMIN', 'UNION'] },
  { index: '/reimbursement', label: '报销申请', roles: ['ADMIN', 'UNION', 'CLUB', 'TREASURER', 'MEMBER'] },
  { index: '/receipt', label: '票据归档', roles: ['ADMIN', 'UNION', 'TREASURER'] },
  { index: '/asset', label: '物资台账', roles: ['ADMIN', 'UNION', 'WAREHOUSE'] },
  { index: '/borrow', label: '物资借用', roles: ['ADMIN', 'UNION', 'CLUB', 'WAREHOUSE', 'MEMBER'] },
  { index: '/returncheck', label: '归还验收', roles: ['ADMIN', 'UNION', 'WAREHOUSE', 'CLUB'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'UNION'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
