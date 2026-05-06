<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 139</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/program'>培训项目</el-menu-item>
        <el-menu-item index='/course'>课程目录</el-menu-item>
        <el-menu-item index='/learner'>学员档案</el-menu-item>
        <el-menu-item index='/path'>学习路径</el-menu-item>
        <el-menu-item index='/task'>学习任务</el-menu-item>
        <el-menu-item index='/enroll'>课程报名</el-menu-item>
        <el-menu-item index='/exam'>考核考试</el-menu-item>
        <el-menu-item index='/score'>考试成绩</el-menu-item>
        <el-menu-item index='/skill'>技能标签</el-menu-item>
        <el-menu-item index='/profile'>能力画像</el-menu-item>
        <el-menu-item index='/cert'>认证记录</el-menu-item>
        <el-menu-item index='/reminder'>学习提醒</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>企业培训学习路径与能力画像系统</strong><span>面向企业培训管理的课程学习、路径规划、能力画像、认证记录和学习提醒一体化管理</span></div>
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






