<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 132</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/device'>器械档案</el-menu-item>
        <el-menu-item index='/category'>器械分类</el-menu-item>
        <el-menu-item index='/department'>科室信息</el-menu-item>
        <el-menu-item index='/request'>借用申请</el-menu-item>
        <el-menu-item index='/borrow'>借用记录</el-menu-item>
        <el-menu-item index='/return'>归还记录</el-menu-item>
        <el-menu-item index='/batch'>消毒批次</el-menu-item>
        <el-menu-item index='/sterilization'>消毒记录</el-menu-item>
        <el-menu-item index='/trace'>二维码追踪</el-menu-item>
        <el-menu-item index='/maintenance'>维护记录</el-menu-item>
        <el-menu-item index='/inspection'>巡检任务</el-menu-item>
        <el-menu-item index='/alert'>风险预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>医疗器械借用消毒与追踪管理系统</strong><span>器械档案、借用申请、归还记录、消毒批次、二维码追踪和风险预警一体化管理</span></div>
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
