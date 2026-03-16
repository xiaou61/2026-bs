<template>
  <el-container class="admin-layout">
    <el-aside width="240px" class="admin-aside">
      <div class="admin-brand">
        <div class="display-title">AURORA CMS</div>
        <div>壁纸社区管理后台</div>
      </div>
      <el-menu :default-active="activeMenu" router class="admin-menu">
        <el-menu-item index="/admin/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据看板</span>
        </el-menu-item>
        <el-menu-item index="/admin/category">
          <el-icon><Collection /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tag">
          <el-icon><PriceTag /></el-icon>
          <span>标签管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/wallpaper">
          <el-icon><Picture /></el-icon>
          <span>壁纸管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/audit">
          <el-icon><Finished /></el-icon>
          <span>审核管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/banner">
          <el-icon><Monitor /></el-icon>
          <span>轮播管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/notice">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="admin-header">
        <div class="header-left">
          <router-link to="/">返回前台</router-link>
        </div>
        <div class="header-right">
          <span>{{ userStore.userInfo?.realName || userStore.userInfo?.username }}</span>
          <el-button text @click="handleLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { Bell, Collection, DataAnalysis, Finished, Monitor, Picture, PriceTag } from '@element-plus/icons-vue'
import { logout } from '../../api'
import { useUserStore } from '../../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleLogout = () => {
  ElMessageBox.confirm('确定退出当前账号吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await logout()
    } catch (e) {}
    userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
  background: #f3f6fb;
  color: #18212f;
}

.admin-aside {
  background: #091322;
  color: #fff;
}

.admin-brand {
  padding: 28px 24px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  color: rgba(255, 255, 255, 0.72);
}

.admin-brand .display-title {
  margin-bottom: 8px;
  color: #fff;
  font-size: 24px;
}

.admin-menu {
  border-right: none;
  background: transparent;
}

.admin-menu :deep(.el-menu-item) {
  color: rgba(255, 255, 255, 0.72);
}

.admin-menu :deep(.el-menu-item.is-active) {
  color: #7dd3fc;
  background: rgba(125, 211, 252, 0.08);
}

.admin-header {
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 12px 24px rgba(15, 23, 42, 0.05);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 14px;
}

.admin-main {
  padding: 24px;
}
</style>
