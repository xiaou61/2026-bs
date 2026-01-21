<template>
  <div class="login-page">
    <div class="login-box">
      <h2>剧本杀创作与预约平台</h2>
      <el-form :model="form" @submit.prevent="handleLogin">
        <el-form-item><el-input v-model="form.username" placeholder="用户名" prefix-icon="User" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" /></el-form-item>
        <el-form-item><el-button type="primary" native-type="submit" style="width: 100%">登录</el-button></el-form-item>
      </el-form>
      <div style="text-align: center;"><router-link to="/register">没有账号？立即注册</router-link></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: '', password: '' })

const handleLogin = async () => {
  const res: any = await login(form)
  if (res.code === 200) {
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    router.push(userStore.getRedirectPath())
  } else {
    ElMessage.error(res.msg || '登录失败')
  }
}
</script>

<style scoped>
.login-page { min-height: 100vh; display: flex; justify-content: center; align-items: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-box { width: 400px; padding: 40px; background: #fff; border-radius: 8px; box-shadow: 0 4px 20px rgba(0,0,0,0.2); }
.login-box h2 { text-align: center; margin-bottom: 30px; color: #333; }
</style>
