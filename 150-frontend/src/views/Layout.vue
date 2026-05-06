<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 150</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/item'>检查项目</el-menu-item>
        <el-menu-item index='/patient'>患者档案</el-menu-item>
        <el-menu-item index='/doctor'>医生档案</el-menu-item>
        <el-menu-item index='/appointment'>检查预约</el-menu-item>
        <el-menu-item index='/department'>检查科室</el-menu-item>
        <el-menu-item index='/checkin'>签到记录</el-menu-item>
        <el-menu-item index='/report'>检查报告</el-menu-item>
        <el-menu-item index='/alert'>异常预警</el-menu-item>
        <el-menu-item index='/delivery'>报告回传</el-menu-item>
        <el-menu-item index='/advice'>复诊建议</el-menu-item>
        <el-menu-item index='/queue'>队列叫号</el-menu-item>
        <el-menu-item index='/notice'>通知公告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>医院门诊检查预约与报告回传管理系统</strong><span>面向医院门诊检查管理的预约登记、检查排队、报告回传和异常提醒一体化管理</span></div>
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











