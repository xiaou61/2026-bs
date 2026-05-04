<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">CLOUD 107</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
    <el-menu-item index="/region">云区域</el-menu-item>
    <el-menu-item index="/asset">主机资产</el-menu-item>
    <el-menu-item index="/group">资源分组</el-menu-item>
    <el-menu-item index="/metric">指标定义</el-menu-item>
    <el-menu-item index="/sample">指标采样</el-menu-item>
    <el-menu-item index="/rule">告警规则</el-menu-item>
    <el-menu-item index="/event">告警事件</el-menu-item>
    <el-menu-item index="/notify">通知记录</el-menu-item>
    <el-menu-item index="/ticket">处置工单</el-menu-item>
    <el-menu-item index="/maintenance">维护窗口</el-menu-item>
    <el-menu-item index="/capacity">容量规划</el-menu-item>
    <el-menu-item index="/widget">看板组件</el-menu-item>
    <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>云服务器资产监控与告警平台</strong><span>主机资产、指标采集、告警规则、处置工单闭环管理</span></div>
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
