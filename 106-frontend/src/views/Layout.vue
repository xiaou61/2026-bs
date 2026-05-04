<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">DEVOPS 106</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
    <el-menu-item index="/environment">发布环境</el-menu-item>
    <el-menu-item index="/service">应用服务</el-menu-item>
    <el-menu-item index="/pipeline">流水线</el-menu-item>
    <el-menu-item index="/release-plan">发布计划</el-menu-item>
    <el-menu-item index="/release-order">发布单</el-menu-item>
    <el-menu-item index="/approval-flow">审批流程</el-menu-item>
    <el-menu-item index="/approval-record">审批记录</el-menu-item>
    <el-menu-item index="/artifact">版本制品</el-menu-item>
    <el-menu-item index="/deploy-task">部署任务</el-menu-item>
    <el-menu-item index="/rollback-plan">回滚预案</el-menu-item>
    <el-menu-item index="/rollback-record">回滚记录</el-menu-item>
    <el-menu-item index="/checklist">变更检查</el-menu-item>
    <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>DevOps 发布审批与回滚管理系统</strong><span>发布计划、审批、部署、回滚闭环管理</span></div>
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
