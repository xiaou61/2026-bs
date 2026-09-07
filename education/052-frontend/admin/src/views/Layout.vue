<template>
  <el-container class="layout">
    <el-aside width="200px" class="aside">
      <div class="logo">网课管理后台</div>
      <el-menu :default-active="$route.path" router background-color="#001529" text-color="#fff" active-text-color="#1890ff">
        <el-menu-item index="/dashboard"><el-icon><DataLine /></el-icon>数据统计</el-menu-item>
        <el-menu-item index="/user"><el-icon><User /></el-icon>用户管理</el-menu-item>
        <el-menu-item index="/category"><el-icon><Menu /></el-icon>分类管理</el-menu-item>
        <el-menu-item index="/course"><el-icon><VideoPlay /></el-icon>课程管理</el-menu-item>
        <el-menu-item index="/comment"><el-icon><ChatDotRound /></el-icon>评论管理</el-menu-item>
        <el-menu-item index="/banner"><el-icon><Picture /></el-icon>轮播图管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span></span>
        <el-dropdown>
          <span class="user-info">{{ user?.nickname }} <el-icon><ArrowDown /></el-icon></span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const user = ref(null)

onMounted(() => {
  const userStr = localStorage.getItem('adminUser')
  if (userStr) user.value = JSON.parse(userStr)
})

const logout = () => {
  localStorage.removeItem('adminToken')
  localStorage.removeItem('adminUser')
  router.push('/login')
}
</script>

<style scoped>
.layout { height: 100vh; }
.aside { background: #001529; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 16px; font-weight: bold; }
.header { background: #fff; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
.user-info { cursor: pointer; display: flex; align-items: center; gap: 5px; }
.main { background: #f0f2f5; }
</style>
