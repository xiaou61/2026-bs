<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">SERVICE 103</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
        <el-menu-item index="/customer">客户档案</el-menu-item>
        <el-menu-item index="/channel">服务渠道</el-menu-item>
        <el-menu-item index="/category">知识分类</el-menu-item>
        <el-menu-item index="/article">知识文章</el-menu-item>
        <el-menu-item index="/order">客服工单</el-menu-item>
        <el-menu-item index="/message">会话消息</el-menu-item>
        <el-menu-item index="/assignment">派单记录</el-menu-item>
        <el-menu-item index="/rule">质检规则</el-menu-item>
        <el-menu-item index="/quality-task">质检任务</el-menu-item>
        <el-menu-item index="/quality-result">质检结果</el-menu-item>
        <el-menu-item index="/recommend">知识推荐</el-menu-item>
        <el-menu-item index="/performance">坐席绩效</el-menu-item>
        <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>智能客服工单质检与知识推荐系统</strong><span>工单、质检、知识、绩效闭环管理</span></div>
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
