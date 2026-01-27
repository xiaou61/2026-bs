<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">救灾调度系统</div>
      <el-menu :default-active="route.path" router background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><DataLine /></el-icon><span>系统首页</span></el-menu-item>
        <el-menu-item index="/disaster"><el-icon><Warning /></el-icon><span>灾情管理</span></el-menu-item>
        <el-sub-menu index="material">
          <template #title><el-icon><Box /></el-icon><span>物资管理</span></template>
          <el-menu-item index="/category">物资分类</el-menu-item>
          <el-menu-item index="/material">物资列表</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="warehouse">
          <template #title><el-icon><House /></el-icon><span>仓库管理</span></template>
          <el-menu-item index="/warehouse">仓库列表</el-menu-item>
          <el-menu-item index="/stock">库存管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/dispatch"><el-icon><Van /></el-icon><span>调度管理</span></el-menu-item>
        <el-menu-item index="/task"><el-icon><List /></el-icon><span>救援任务</span></el-menu-item>
        <el-menu-item index="/notice"><el-icon><Bell /></el-icon><span>公告管理</span></el-menu-item>
        <el-menu-item index="/user" v-if="userStore.user?.role === 'ADMIN'"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <span>{{ userStore.user?.realName }}</span>
          <el-dropdown @command="handleCommand">
            <el-avatar :size="36">{{ userStore.user?.realName?.charAt(0) }}</el-avatar>
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
}
.el-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  border-bottom: 1px solid #eee;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}
.el-main {
  background-color: #f0f2f5;
}
</style>
