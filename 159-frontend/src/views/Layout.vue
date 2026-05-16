<template>
  <el-container class="layout">
    <el-aside width="236px"><div class="logo">医废联单 159</div><el-menu router :default-active="$route.path"><el-menu-item v-for="item in menusForRole" :key="item.index" :index="item.index">{{ item.label }}</el-menu-item></el-menu></el-aside>
    <el-container><el-header><div><strong>医疗废弃物收运联单与闭环监管系统</strong><span>分类收集、称重赋码、转运联单、处置确认一体化监管</span></div><div class="user-box"><el-tag>{{ userStore.user?.role }}</el-tag><span>{{ userStore.user?.nickname }}</span><el-button link type="danger" @click="handleLogout">退出</el-button></div></el-header><el-main><router-view /></el-main></el-container>
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
  { index: '/dashboard', label: '数据看板', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] },
  { index: '/user', label: '账号权限', roles: ['ADMIN'] },
  { index: '/waste', label: '医废来源', roles: ['ADMIN', 'HOSPITAL', 'REGULATOR'] },
  { index: '/category', label: '废物类别', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR'] },
  { index: '/package', label: '包装赋码', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR'] },
  { index: '/order', label: '收集预约', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER'] },
  { index: '/weighing', label: '称重记录', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER'] },
  { index: '/storage', label: '暂存交接', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'REGULATOR'] },
  { index: '/manifest', label: '转运联单', roles: ['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR'] },
  { index: '/track', label: '运输轨迹', roles: ['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR'] },
  { index: '/disposal', label: '处置确认', roles: ['ADMIN', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] },
  { index: '/exception', label: '异常追溯', roles: ['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER', 'DISPOSAL', 'REGULATOR'] },
  { index: '/audit', label: '监管抽查', roles: ['ADMIN', 'REGULATOR'] },
  { index: '/log', label: '操作日志', roles: ['ADMIN', 'REGULATOR'] }
]
const menusForRole = computed(() => menus.filter(item => item.roles.includes(userStore.user?.role)))
const handleLogout = async () => { await logout().catch(() => null); userStore.clear(); router.push('/login') }
</script>
