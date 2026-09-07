<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">饮食管理</div>
      <el-menu :default-active="activeMenu" router>
        <el-menu-item index="/dashboard">
          <el-icon><DataLine /></el-icon>
          <span>数据概览</span>
        </el-menu-item>
        <el-menu-item index="/diet-record">
          <el-icon><Edit /></el-icon>
          <span>饮食记录</span>
        </el-menu-item>
        <el-menu-item index="/food-library">
          <el-icon><FoodBox /></el-icon>
          <span>食物库</span>
        </el-menu-item>
        <el-menu-item index="/nutrition">
          <el-icon><PieChart /></el-icon>
          <span>营养分析</span>
        </el-menu-item>
        <el-menu-item index="/health">
          <el-icon><TrendCharts /></el-icon>
          <span>健康数据</span>
        </el-menu-item>
        <el-menu-item index="/goal">
          <el-icon><Flag /></el-icon>
          <span>营养目标</span>
        </el-menu-item>
        <el-menu-item index="/recipe">
          <el-icon><Notebook /></el-icon>
          <span>食谱推荐</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-content">
          <span>个人饮食管理系统</span>
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              用户
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const activeMenu = computed(() => route.path)

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    ElMessage.success('退出成功')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  background: #1f2d3d;
}

.el-menu {
  border: none;
  background: #304156;
}

:deep(.el-menu-item) {
  color: #bfcbd9;
}

:deep(.el-menu-item.is-active) {
  background: #409eff !important;
  color: #fff;
}

:deep(.el-menu-item:hover) {
  background: #263445;
  color: #fff;
}

.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
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

.user-info {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.el-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>
