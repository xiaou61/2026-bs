<template>
  <div class="login-page">
    <div class="login-panel">
      <div class="brand">
        <span>SERVICE 103</span>
        <h1>智能客服工单质检与知识推荐系统</h1>
        <p>工单处理、质检评分、知识推荐和坐席绩效一体化运营</p>
      </div>
      <el-form :model="form" class="login-form" @keyup.enter="handleLogin">
        <el-form-item><el-input v-model="form.username" size="large" placeholder="用户名" /></el-form-item>
        <el-form-item><el-input v-model="form.password" size="large" type="password" show-password placeholder="密码" /></el-form-item>
        <el-button type="primary" size="large" :loading="loading" @click="handleLogin">登录系统</el-button>
        <div class="accounts">
          <el-tag>admin / 123456</el-tag>
          <el-tag type="success">supervisor / 123456</el-tag>
          <el-tag type="warning">agent / 123456</el-tag>
          <el-tag type="danger">qa / 123456</el-tag>
        </div>
      </el-form>
    </div>
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
    userStore.setLogin(res.data.token, res.data.user)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>
