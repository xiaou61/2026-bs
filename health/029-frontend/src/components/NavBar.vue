<template>
  <el-header class="navbar">
    <div class="navbar-content">
      <div class="logo">
        <h2>🌿 中药食疗平台</h2>
      </div>
      
      <el-menu
        mode="horizontal"
        :default-active="activeIndex"
        @select="handleMenuSelect"
        class="navbar-menu"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/recipes">食谱</el-menu-item>
        <el-menu-item index="/ingredients">食材</el-menu-item>
        <el-menu-item index="/topics">社区</el-menu-item>
      </el-menu>

      <div class="navbar-right">
        <template v-if="userStore.isLoggedIn">
          <el-button type="primary" @click="goToCreateRecipe">发布食谱</el-button>
          <el-button @click="goToCreateTopic">发起话题</el-button>
          
          <el-dropdown @command="handleUserCommand">
            <span class="el-dropdown-link">
              {{ userStore.userInfo?.username || '用户' }}
              <el-icon class="el-icon--right">
                <arrow-down />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人资料</el-dropdown-item>
                <el-dropdown-item command="my-recipes">我的食谱</el-dropdown-item>
                <el-dropdown-item command="my-topics">我的话题</el-dropdown-item>
                <el-dropdown-item command="collections">我的收藏</el-dropdown-item>
                <el-divider />
                <el-dropdown-item command="logout">登出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        
        <template v-else>
          <el-button @click="goToLogin">登录</el-button>
          <el-button type="primary" @click="goToRegister">注册</el-button>
        </template>
      </div>
    </div>
  </el-header>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ArrowDown } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeIndex = ref('/')

const handleMenuSelect = (index) => {
  router.push(index)
}

const goToCreateRecipe = () => {
  router.push({ name: 'create-recipe' })
}

const goToCreateTopic = () => {
  router.push({ name: 'create-topic' })
}

const goToLogin = () => {
  router.push({ name: 'login' })
}

const goToRegister = () => {
  router.push({ name: 'register' })
}

const handleUserCommand = async (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push({ name: 'home' })
    ElMessage.success('已登出')
  } else {
    router.push({ name: command })
  }
}

onMounted(() => {
  // 根据当前路由更新 active menu
  const routePath = route.path
  if (routePath.startsWith('/recipes')) {
    activeIndex.value = '/recipes'
  } else if (routePath.startsWith('/ingredients')) {
    activeIndex.value = '/ingredients'
  } else if (routePath.startsWith('/topics')) {
    activeIndex.value = '/topics'
  } else {
    activeIndex.value = '/'
  }
})
</script>

<style scoped>
.navbar {
  display: flex;
  align-items: center;
  padding: 0 20px;
  background-color: #ffffff;
  border-bottom: 1px solid #eee;
}

.navbar-content {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  min-width: 200px;
  font-size: 18px;
  font-weight: bold;
  color: #27ae60;
}

.logo h2 {
  margin: 0;
  font-size: 18px;
}

.navbar-menu {
  flex: 1;
  margin: 0 20px;
  border: none;
  justify-content: flex-start;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 300px;
  justify-content: flex-end;
}

.el-dropdown-link {
  color: #333;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
}

:deep(.el-menu) {
  background-color: transparent;
}

:deep(.el-menu-item) {
  color: #666;
}

:deep(.el-menu-item.is-active) {
  color: #27ae60 !important;
  border-bottom-color: #27ae60;
}
</style>
