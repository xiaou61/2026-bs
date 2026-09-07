<template>
  <div class="login-container">
    <div class="login-box">
      <h2>用户注册</h2>
      <el-form :model="form" label-width="0">
        <el-form-item><el-input v-model="form.username" placeholder="用户名" prefix-icon="User" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" /></el-form-item>
        <el-form-item><el-input v-model="form.realName" placeholder="真实姓名" /></el-form-item>
        <el-form-item><el-input v-model="form.phone" placeholder="手机号" /></el-form-item>
        <el-form-item><el-input v-model="form.email" placeholder="邮箱" /></el-form-item>
        <el-form-item><el-button type="primary" style="width:100%" @click="handleRegister">注册</el-button></el-form-item>
        <el-form-item><el-link type="primary" @click="router.push('/login')">已有账号？去登录</el-link></el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const form = reactive({ username: '', password: '', realName: '', phone: '', email: '' })
const handleRegister = async () => {
  const res: any = await register(form)
  if (res.code === 200) { ElMessage.success('注册成功'); router.push('/login') }
  else ElMessage.error(res.message)
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background: linear-gradient(135deg, #8B4513 0%, #D2691E 100%); }
.login-box { width: 400px; padding: 40px; background: #fff; border-radius: 8px; box-shadow: 0 4px 20px rgba(0,0,0,0.15); }
h2 { text-align: center; margin-bottom: 30px; color: #8B4513; }
</style>
