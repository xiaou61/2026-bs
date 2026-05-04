<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">AGRI TRACE 113</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/farm'>种植基地</el-menu-item>
        <el-menu-item index='/farmer'>农户档案</el-menu-item>
        <el-menu-item index='/category'>产品分类</el-menu-item>
        <el-menu-item index='/batch'>产品批次</el-menu-item>
        <el-menu-item index='/planting'>种植记录</el-menu-item>
        <el-menu-item index='/material'>农资投入</el-menu-item>
        <el-menu-item index='/inspection'>质检报告</el-menu-item>
        <el-menu-item index='/block'>区块存证</el-menu-item>
        <el-menu-item index='/node'>流通节点</el-menu-item>
        <el-menu-item index='/logistics'>物流记录</el-menu-item>
        <el-menu-item index='/recall'>召回事件</el-menu-item>
        <el-menu-item index='/regulation'>监管检查</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
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
import { useRouter } from 'vue-router'
import { logout } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const handleLogout = async () => {
  await logout().catch(() => null)
  userStore.clear()
  router.push('/login')
}
</script>
