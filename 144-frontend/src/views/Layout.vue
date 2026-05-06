<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 144</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/route'>无障碍路线</el-menu-item>
        <el-menu-item index='/facility'>设施点位</el-menu-item>
        <el-menu-item index='/traveler'>出行用户</el-menu-item>
        <el-menu-item index='/request'>协助预约</el-menu-item>
        <el-menu-item index='/volunteer'>志愿者档案</el-menu-item>
        <el-menu-item index='/plan'>路线方案</el-menu-item>
        <el-menu-item index='/task'>协助任务</el-menu-item>
        <el-menu-item index='/checkin'>服务签到</el-menu-item>
        <el-menu-item index='/feedback'>评价反馈</el-menu-item>
        <el-menu-item index='/contact'>应急联系人</el-menu-item>
        <el-menu-item index='/trip'>行程记录</el-menu-item>
        <el-menu-item index='/notice'>消息通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>无障碍出行路线规划与志愿协助平台</strong><span>面向无障碍出行服务管理的路线规划、志愿协助、行程记录和消息通知一体化管理</span></div>
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








