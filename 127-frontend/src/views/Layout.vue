<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 127</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/company'>企业档案</el-menu-item>
        <el-menu-item index='/factor'>排放因子</el-menu-item>
        <el-menu-item index='/period'>核算周期</el-menu-item>
        <el-menu-item index='/consumption'>能源消耗</el-menu-item>
        <el-menu-item index='/emission'>排放记录</el-menu-item>
        <el-menu-item index='/task'>减排任务</el-menu-item>
        <el-menu-item index='/measure'>减排措施</el-menu-item>
        <el-menu-item index='/quota'>碳配额</el-menu-item>
        <el-menu-item index='/report'>核查报告</el-menu-item>
        <el-menu-item index='/attachment'>佐证附件</el-menu-item>
        <el-menu-item index='/rule'>预警规则</el-menu-item>
        <el-menu-item index='/warning'>碳排预警</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>企业碳排放核算与减排任务管理系统</strong><span>排放因子、能源消耗、碳核算、减排任务、核查报告和预警处置一体化管理</span></div>
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
