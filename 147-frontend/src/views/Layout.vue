<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 147</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/case'>咨询个案</el-menu-item>
        <el-menu-item index='/room'>咨询室管理</el-menu-item>
        <el-menu-item index='/student'>学生档案</el-menu-item>
        <el-menu-item index='/duty'>值班安排</el-menu-item>
        <el-menu-item index='/appointment'>预约申请</el-menu-item>
        <el-menu-item index='/record'>咨询记录</el-menu-item>
        <el-menu-item index='/questionnaire'>测评问卷</el-menu-item>
        <el-menu-item index='/risk'>风险评估</el-menu-item>
        <el-menu-item index='/intervention'>危机干预</el-menu-item>
        <el-menu-item index='/family'>家校沟通</el-menu-item>
        <el-menu-item index='/followup'>随访计划</el-menu-item>
        <el-menu-item index='/notice'>通知公告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>校园心理咨询预约与危机干预管理系统</strong><span>面向校园心理服务管理的咨询预约、个案评估、危机干预和回访跟踪一体化管理</span></div>
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










