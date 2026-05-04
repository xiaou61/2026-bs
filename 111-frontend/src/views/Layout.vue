<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PHISHING 111</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/employee'>员工档案</el-menu-item>
        <el-menu-item index='/department'>部门分组</el-menu-item>
        <el-menu-item index='/template'>邮件模板</el-menu-item>
        <el-menu-item index='/campaign'>演练活动</el-menu-item>
        <el-menu-item index='/target'>目标名单</el-menu-item>
        <el-menu-item index='/send-record'>发送记录</el-menu-item>
        <el-menu-item index='/click'>点击追踪</el-menu-item>
        <el-menu-item index='/course'>培训课程</el-menu-item>
        <el-menu-item index='/exam'>培训考试</el-menu-item>
        <el-menu-item index='/question'>考试题目</el-menu-item>
        <el-menu-item index='/attempt'>考试记录</el-menu-item>
        <el-menu-item index='/risk-score'>风险评分</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>网络钓鱼邮件演练与安全意识培训平台</strong><span>邮件演练、点击追踪、课程考试、风险评分闭环管理</span></div>
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
