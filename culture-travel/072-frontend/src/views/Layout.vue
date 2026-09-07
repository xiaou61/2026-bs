<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">🏔️ 哈尔滨文旅</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409eff">
        <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
        <el-sub-menu index="tourism">
          <template #title><el-icon><Location /></el-icon>旅游服务</template>
          <el-menu-item index="/spots">景点浏览</el-menu-item>
          <el-menu-item index="/routes">旅游路线</el-menu-item>
          <el-menu-item index="/hotels">住宿推荐</el-menu-item>
          <el-menu-item index="/restaurants">美食攻略</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="activities">
          <template #title><el-icon><Calendar /></el-icon>活动互动</template>
          <el-menu-item index="/activities">文旅活动</el-menu-item>
          <el-menu-item index="/notes">游记分享</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="user">
          <template #title><el-icon><User /></el-icon>个人中心</template>
          <el-menu-item index="/ticket/orders">我的订单</el-menu-item>
          <el-menu-item index="/favorites">我的收藏</el-menu-item>
          <el-menu-item index="/wallet">我的钱包</el-menu-item>
          <el-menu-item index="/profile">个人资料</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/announcements"><el-icon><Bell /></el-icon>系统公告</el-menu-item>
        <template v-if="userStore.isAdmin()">
          <el-sub-menu index="admin">
            <template #title><el-icon><Setting /></el-icon>系统管理</template>
            <el-menu-item index="/admin/users">用户管理</el-menu-item>
            <el-menu-item index="/admin/spots">景点管理</el-menu-item>
            <el-menu-item index="/admin/orders">订单管理</el-menu-item>
            <el-menu-item index="/reviews">评价管理</el-menu-item>
          </el-sub-menu>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <span>欢迎来到冰雪之城哈尔滨 ❄️</span>
        </div>
        <div class="header-right">
          <span class="user-info">{{ userStore.user?.nickname || userStore.user?.username }}</span>
          <el-tag v-if="userStore.isAdmin()" type="danger" size="small">管理员</el-tag>
          <el-button link @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.aside {
  background: #304156;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background: #263445;
}
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0,0,0,0.08);
  padding: 0 20px;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}
.user-info {
  color: #333;
  font-weight: 500;
}
.main {
  background: #f5f7fa;
  padding: 20px;
}
</style>
