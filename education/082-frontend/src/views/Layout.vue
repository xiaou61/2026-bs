<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">公考学习平台</div>
      <el-menu :default-active="activeMenu" router>
        <el-menu-item index="/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据看板</span>
        </el-menu-item>
        <el-menu-item index="/user">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/subject">
          <el-icon><CollectionTag /></el-icon>
          <span>学科管理</span>
        </el-menu-item>
        <el-menu-item index="/course">
          <el-icon><Reading /></el-icon>
          <span>课程管理</span>
        </el-menu-item>
        <el-menu-item index="/bank">
          <el-icon><Folder /></el-icon>
          <span>题库管理</span>
        </el-menu-item>
        <el-menu-item index="/question">
          <el-icon><EditPen /></el-icon>
          <span>试题管理</span>
        </el-menu-item>
        <el-menu-item index="/paper">
          <el-icon><Document /></el-icon>
          <span>试卷管理</span>
        </el-menu-item>
        <el-menu-item index="/exam-record">
          <el-icon><Memo /></el-icon>
          <span>考试记录</span>
        </el-menu-item>
        <el-menu-item index="/plan">
          <el-icon><Calendar /></el-icon>
          <span>学习计划</span>
        </el-menu-item>
        <el-menu-item index="/notice">
          <el-icon><Bell /></el-icon>
          <span>公告通知</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          <span>欢迎，{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
          <el-button size="small" type="danger" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '../store/user'
import {
  Bell,
  Calendar,
  CollectionTag,
  DataAnalysis,
  Document,
  EditPen,
  Folder,
  Memo,
  Reading,
  User
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background: #1f2d3d;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  background: #16202a;
}

.el-menu {
  border: none;
  background: #1f2d3d;
}

.el-menu-item {
  color: #c7d2de;
}

.el-menu-item:hover,
.el-menu-item.is-active {
  background: #16202a !important;
  color: #409eff !important;
}

.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.header-content {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>
