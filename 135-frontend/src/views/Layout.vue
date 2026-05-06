<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 135</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/conference'>会议信息</el-menu-item>
        <el-menu-item index='/call'>征稿通知</el-menu-item>
        <el-menu-item index='/author'>作者档案</el-menu-item>
        <el-menu-item index='/paper'>论文投稿</el-menu-item>
        <el-menu-item index='/reviewer'>审稿专家</el-menu-item>
        <el-menu-item index='/assignment'>审稿分配</el-menu-item>
        <el-menu-item index='/review'>盲审记录</el-menu-item>
        <el-menu-item index='/notice'>录用通知</el-menu-item>
        <el-menu-item index='/guest'>参会报名</el-menu-item>
        <el-menu-item index='/venue'>会场安排</el-menu-item>
        <el-menu-item index='/agenda'>会议日程</el-menu-item>
        <el-menu-item index='/checkin'>签到记录</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>学术会议投稿评审与日程管理系统</strong><span>面向会务管理的征稿发布、论文盲审、录用通知、会场排期与签到服务一体化管理</span></div>
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


