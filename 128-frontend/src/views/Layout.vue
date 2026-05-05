<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 128</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/indicator'>指标库</el-menu-item>
        <el-menu-item index='/template'>披露模板</el-menu-item>
        <el-menu-item index='/period'>报告周期</el-menu-item>
        <el-menu-item index='/submission'>企业填报</el-menu-item>
        <el-menu-item index='/data'>指标数据</el-menu-item>
        <el-menu-item index='/evidence'>佐证材料</el-menu-item>
        <el-menu-item index='/review'>审核任务</el-menu-item>
        <el-menu-item index='/model'>评分模型</el-menu-item>
        <el-menu-item index='/score'>ESG评分</el-menu-item>
        <el-menu-item index='/improvement'>改进任务</el-menu-item>
        <el-menu-item index='/feedback'>利益相关方反馈</el-menu-item>
        <el-menu-item index='/export'>报告导出</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>ESG 数据填报与可视化报告系统</strong><span>指标库、企业填报、佐证材料、审核任务、ESG评分和报告导出一体化管理</span></div>
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
