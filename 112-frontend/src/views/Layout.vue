<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">ZERO TRUST 112</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/device'>设备资产</el-menu-item>
        <el-menu-item index='/employee'>员工账号</el-menu-item>
        <el-menu-item index='/idp'>身份源</el-menu-item>
        <el-menu-item index='/risk-model'>风险模型</el-menu-item>
        <el-menu-item index='/assessment'>风险评估</el-menu-item>
        <el-menu-item index='/policy'>访问策略</el-menu-item>
        <el-menu-item index='/rule'>策略规则</el-menu-item>
        <el-menu-item index='/application'>准入申请</el-menu-item>
        <el-menu-item index='/session'>访问会话</el-menu-item>
        <el-menu-item index='/segment'>网络分区</el-menu-item>
        <el-menu-item index='/certificate'>设备证书</el-menu-item>
        <el-menu-item index='/audit-event'>审计事件</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>零信任设备准入与访问控制管理系统</strong><span>设备准入、风险评估、访问策略、审计事件闭环管理</span></div>
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
