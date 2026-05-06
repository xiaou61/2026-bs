<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 143</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/project'>服务项目</el-menu-item>
        <el-menu-item index='/category'>服务分类</el-menu-item>
        <el-menu-item index='/resident'>居民档案</el-menu-item>
        <el-menu-item index='/volunteer'>志愿者档案</el-menu-item>
        <el-menu-item index='/booking'>服务预约</el-menu-item>
        <el-menu-item index='/checkin'>服务签到</el-menu-item>
        <el-menu-item index='/account'>时长账户</el-menu-item>
        <el-menu-item index='/exchange'>互助兑换</el-menu-item>
        <el-menu-item index='/feedback'>评价反馈</el-menu-item>
        <el-menu-item index='/activity'>公益活动</el-menu-item>
        <el-menu-item index='/rule'>积分规则</el-menu-item>
        <el-menu-item index='/notice'>站内通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>社区公益时间银行互助服务平台</strong><span>面向社区互助服务管理的服务预约、时长存取、公益活动和互助兑换一体化管理</span></div>
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








