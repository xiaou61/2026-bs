<template>
  <el-container style="height: 100%">
    <el-header style="height: 60px; display: flex; align-items: center; justify-content: space-between; background: #e6a23c; color: white">
      <div style="font-size: 20px; font-weight: bold">系统管理后台</div>
      <div style="display: flex; align-items: center; gap: 20px">
        <span>{{ userStore.userInfo?.name }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" style="background: #f5f7fa; padding: 20px 0">
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/admin/students">
            <el-icon><User /></el-icon>
            <span>学生管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/teachers">
            <el-icon><Avatar /></el-icon>
            <span>教师管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/courses">
            <el-icon><Reading /></el-icon>
            <span>课程管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/notices">
            <el-icon><Bell /></el-icon>
            <span>公告管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/config">
            <el-icon><Setting /></el-icon>
            <span>系统配置</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
}
</script>

