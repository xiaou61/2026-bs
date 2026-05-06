<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 137</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/project'>孵化项目</el-menu-item>
        <el-menu-item index='/mentor'>导师档案</el-menu-item>
        <el-menu-item index='/team'>团队档案</el-menu-item>
        <el-menu-item index='/application'>项目申报</el-menu-item>
        <el-menu-item index='/plan'>孵化计划</el-menu-item>
        <el-menu-item index='/coaching'>导师辅导</el-menu-item>
        <el-menu-item index='/review'>路演活动</el-menu-item>
        <el-menu-item index='/score'>路演评分</el-menu-item>
        <el-menu-item index='/funding'>经费记录</el-menu-item>
        <el-menu-item index='/milestone'>里程碑任务</el-menu-item>
        <el-menu-item index='/achievement'>成果展示</el-menu-item>
        <el-menu-item index='/notice'>孵化通知</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>大学生创新创业项目孵化管理平台</strong><span>面向创新创业孵化的项目申报、导师辅导、路演评分、经费跟踪和成果展示一体化管理</span></div>
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




