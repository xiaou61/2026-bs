<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 146</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/plan'>抽检计划</el-menu-item>
        <el-menu-item index='/food'>食品档案</el-menu-item>
        <el-menu-item index='/merchant'>商户档案</el-menu-item>
        <el-menu-item index='/task'>抽样任务</el-menu-item>
        <el-menu-item index='/agency'>检测机构</el-menu-item>
        <el-menu-item index='/sample'>样品信息</el-menu-item>
        <el-menu-item index='/result'>检测结果</el-menu-item>
        <el-menu-item index='/recheck'>复检申请</el-menu-item>
        <el-menu-item index='/disposal'>处置记录</el-menu-item>
        <el-menu-item index='/report'>结果公示</el-menu-item>
        <el-menu-item index='/warning'>风险预警</el-menu-item>
        <el-menu-item index='/notice'>通知公告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>食品安全抽检任务与结果公示平台</strong><span>面向食品安全监管管理的抽检任务、样品检测、风险预警和结果公示一体化管理</span></div>
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









