<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 136</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/topic'>课题发布</el-menu-item>
        <el-menu-item index='/teacher'>导师档案</el-menu-item>
        <el-menu-item index='/student'>学生档案</el-menu-item>
        <el-menu-item index='/application'>课题申请</el-menu-item>
        <el-menu-item index='/review'>双选审核</el-menu-item>
        <el-menu-item index='/selection'>双选确认</el-menu-item>
        <el-menu-item index='/taskbook'>任务书下达</el-menu-item>
        <el-menu-item index='/proposal'>开题材料</el-menu-item>
        <el-menu-item index='/defense'>开题答辩</el-menu-item>
        <el-menu-item index='/midterm'>中期检查</el-menu-item>
        <el-menu-item index='/guidance'>指导记录</el-menu-item>
        <el-menu-item index='/notice'>节点通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>导师课题双选与开题过程管理系统</strong><span>课题发布、志愿申请、双选确认、任务书下达、开题答辩和节点提醒一体化管理</span></div>
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


