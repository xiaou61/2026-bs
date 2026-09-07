<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">AGRI TRACE 113</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item v-for="item in visibleMenus" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>区块链农产品质量溯源与监管平台</strong><span>批次追踪、质检报告、区块存证、流通监管闭环管理</span></div>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/farm', label: '种植基地', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/farmer', label: '农户档案', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/category', label: '产品分类', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/batch', label: '产品批次', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/planting', label: '种植记录', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/material', label: '农资投入', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/inspection', label: '质检报告', roles: ['ADMIN', 'REGULATOR', 'INSPECTOR'] },
  { index: '/block', label: '区块存证', roles: ['ADMIN', 'REGULATOR', 'INSPECTOR'] },
  { index: '/node', label: '流通节点', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/logistics', label: '物流记录', roles: ['ADMIN', 'REGULATOR', 'FARMER', 'INSPECTOR'] },
  { index: '/recall', label: '召回事件', roles: ['ADMIN', 'REGULATOR'] },
  { index: '/regulation', label: '监管检查', roles: ['ADMIN', 'REGULATOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'REGULATOR'] }
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
