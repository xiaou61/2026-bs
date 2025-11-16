<template>
  <div class="not-found">
    <div class="content">
      <h1>404</h1>
      <h2>页面未找到</h2>
      <p>抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goBack">返回首页</el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const goBack = () => {
  if (userStore.isLoggedIn) {
    if (userStore.isAdmin) {
      router.push('/admin/dashboard')
    } else {
      router.push('/student/home')
    }
  } else {
    router.push('/login')
  }
}
</script>

<style lang="scss" scoped>
.not-found {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  
  .content {
    text-align: center;
    color: white;
    
    h1 {
      font-size: 120px;
      margin: 0;
      font-weight: bold;
    }
    
    h2 {
      font-size: 32px;
      margin: 20px 0;
    }
    
    p {
      font-size: 18px;
      margin-bottom: 30px;
    }
  }
}
</style>