<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 145</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/complaint'>投诉工单</el-menu-item>
        <el-menu-item index='/category'>监测点位</el-menu-item>
        <el-menu-item index='/source'>噪声源档案</el-menu-item>
        <el-menu-item index='/officer'>执法人员档案</el-menu-item>
        <el-menu-item index='/task'>处置任务</el-menu-item>
        <el-menu-item index='/patrol'>现场巡查</el-menu-item>
        <el-menu-item index='/rectify'>整改通知</el-menu-item>
        <el-menu-item index='/retest'>复测记录</el-menu-item>
        <el-menu-item index='/penalty'>处罚决定</el-menu-item>
        <el-menu-item index='/feedback'>公众回访</el-menu-item>
        <el-menu-item index='/rule'>预警规则</el-menu-item>
        <el-menu-item index='/notice'>公告公示</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>城市噪声投诉监测与执法协同平台</strong><span>面向城市噪声治理管理的投诉受理、点位监测、执法联动和整改复测一体化管理</span></div>
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









