<template>
  <el-container style="height: 100%">
    <el-header style="height: 60px; display: flex; align-items: center; justify-content: space-between; background: #67c23a; color: white">
      <div style="font-size: 20px; font-weight: bold">教师工作台</div>
      <div style="display: flex; align-items: center; gap: 20px">
        <span>{{ userStore.userInfo?.name }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" style="background: #f5f7fa; padding: 20px 0">
        <el-menu :default-active="route.path" router>
          <el-menu-item index="/teacher/courses">
            <el-icon><Reading /></el-icon>
            <span>我的课程</span>
          </el-menu-item>
          <el-menu-item index="/teacher/grades">
            <el-icon><Edit /></el-icon>
            <span>成绩管理</span>
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

