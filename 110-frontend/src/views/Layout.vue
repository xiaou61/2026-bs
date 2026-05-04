<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PRIVACY 110</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/subject'>数据主体</el-menu-item>
        <el-menu-item index='/data-item'>个人数据项</el-menu-item>
        <el-menu-item index='/purpose'>授权目的</el-menu-item>
        <el-menu-item index='/policy'>授权策略</el-menu-item>
        <el-menu-item index='/authorization'>授权记录</el-menu-item>
        <el-menu-item index='/scope'>授权范围</el-menu-item>
        <el-menu-item index='/access-application'>访问申请</el-menu-item>
        <el-menu-item index='/grant'>访问授权</el-menu-item>
        <el-menu-item index='/access-log'>访问日志</el-menu-item>
        <el-menu-item index='/revocation'>撤销申请</el-menu-item>
        <el-menu-item index='/risk-warning'>风险预警</el-menu-item>
        <el-menu-item index='/audit-report'>审计报告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>个人数据隐私授权与访问审计平台</strong><span>授权、访问、撤销、预警、审计闭环管理</span></div>
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
