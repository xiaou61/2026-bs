<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>开源许可证合规扫描与项目台账系统</h2>
      <p>项目台账、依赖扫描、风险整改和审计报告一体化管理</p>
      <el-form :model="form" @keyup.enter="handleLogin">
        <el-form-item><el-input v-model="form.username" placeholder="账号" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码" show-password /></el-form-item>
        <el-button type="primary" class="login-button" @click="handleLogin" :loading="loading">登录</el-button>
      </el-form>
      <div class="accounts">
        <el-tag>admin / 123456</el-tag>
        <el-tag type="success">compliance / 123456</el-tag>
        <el-tag type="warning">developer / 123456</el-tag>
        <el-tag type="danger">auditor / 123456</el-tag>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({ username: 'admin', password: '123456' })
const handleLogin = async () => {
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>
