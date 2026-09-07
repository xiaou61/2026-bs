<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">写作竞赛管理</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard">
          <el-icon><DataLine /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/user" v-if="userStore.userInfo?.role === 0">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/category" v-if="userStore.userInfo?.role === 0">
          <el-icon><Menu /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/competition" v-if="userStore.userInfo?.role === 0">
          <el-icon><Trophy /></el-icon>
          <span>竞赛管理</span>
        </el-menu-item>
        <el-menu-item index="/work" v-if="userStore.userInfo?.role === 0">
          <el-icon><Document /></el-icon>
          <span>作品管理</span>
        </el-menu-item>
        <el-menu-item index="/score" v-if="userStore.userInfo?.role <= 1">
          <el-icon><Edit /></el-icon>
          <span>评分管理</span>
        </el-menu-item>
        <el-menu-item index="/notice" v-if="userStore.userInfo?.role === 0">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
        <el-menu-item index="/award" v-if="userStore.userInfo?.role === 0">
          <el-icon><Medal /></el-icon>
          <span>获奖管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <span>{{ userStore.userInfo?.nickname }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="36" :src="userStore.userInfo?.avatar || ''" />
            <template #dropdown>
              <el-dropdown-menu>
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
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100%;
}
.el-aside {
  background-color: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background-color: #263445;
}
.el-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}
.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}
.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
