<template>
  <el-container class="layout">
    <el-aside width="236px">
      <div class="logo">PROJECT 149</div>
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/dashboard">数据看板</el-menu-item>
        <el-menu-item index='/user'>账号权限</el-menu-item>
        <el-menu-item index='/asset'>设备档案</el-menu-item>
        <el-menu-item index='/lab'>实验室档案</el-menu-item>
        <el-menu-item index='/category'>设备分类</el-menu-item>
        <el-menu-item index='/borrow-user'>借用人档案</el-menu-item>
        <el-menu-item index='/reservation'>预约申请</el-menu-item>
        <el-menu-item index='/borrow-record'>借用记录</el-menu-item>
        <el-menu-item index='/usage'>使用登记</el-menu-item>
        <el-menu-item index='/violation'>违规记录</el-menu-item>
        <el-menu-item index='/maintenance'>维修工单</el-menu-item>
        <el-menu-item index='/return-confirm'>归还确认</el-menu-item>
        <el-menu-item index='/inspection'>巡检计划</el-menu-item>
        <el-menu-item index='/notice'>通知公告</el-menu-item>
        <el-menu-item index='/log'>操作日志</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div><strong>高校实验设备共享预约与违规使用追踪系统</strong><span>面向高校实验设备管理的共享预约、借用审批、违规追踪和维护复核一体化管理</span></div>
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











