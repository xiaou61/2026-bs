<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">LICENSE 104</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
    <el-menu-item index="/team">组织团队</el-menu-item>
    <el-menu-item index="/repository">项目台账</el-menu-item>
    <el-menu-item index="/branch">分支版本</el-menu-item>
    <el-menu-item index="/dependency">依赖台账</el-menu-item>
    <el-menu-item index="/policy">许可证策略</el-menu-item>
    <el-menu-item index="/baseline">合规基线</el-menu-item>
    <el-menu-item index="/scan-task">扫描任务</el-menu-item>
    <el-menu-item index="/scan-result">扫描结果</el-menu-item>
    <el-menu-item index="/issue">风险问题</el-menu-item>
    <el-menu-item index="/rectification">整改任务</el-menu-item>
    <el-menu-item index="/report">审计报告</el-menu-item>
    <el-menu-item index="/approval">审批记录</el-menu-item>
    <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>开源许可证合规扫描与项目台账系统</strong><span>依赖、许可证、风险、整改、报告闭环治理</span></div>
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
