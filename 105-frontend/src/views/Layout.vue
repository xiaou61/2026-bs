<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">API 105</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index="/user">账号权限</el-menu-item>
    <el-menu-item index="/project">接口项目</el-menu-item>
    <el-menu-item index="/group">接口分组</el-menu-item>
    <el-menu-item index="/endpoint">接口定义</el-menu-item>
    <el-menu-item index="/request-param">请求参数</el-menu-item>
    <el-menu-item index="/response-field">响应字段</el-menu-item>
    <el-menu-item index="/mock-rule">Mock规则</el-menu-item>
    <el-menu-item index="/test-case">测试用例</el-menu-item>
    <el-menu-item index="/test-step">用例步骤</el-menu-item>
    <el-menu-item index="/environment">环境配置</el-menu-item>
    <el-menu-item index="/execution">执行记录</el-menu-item>
    <el-menu-item index="/execution-result">结果明细</el-menu-item>
    <el-menu-item index="/document">文档快照</el-menu-item>
    <el-menu-item index="/log">操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>API 接口文档生成与测试用例管理平台</strong><span>接口、Mock、用例、执行、文档全链路协同</span></div>
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
