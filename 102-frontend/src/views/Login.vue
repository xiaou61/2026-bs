<template>
  <div class="login-page">
    <div class="login-panel">
      <div class="brand">
        <span>LEGAL 102</span>
        <h1>法律咨询案件进度与智能文书管理系统</h1>
        <p>案件台账、进度节点、咨询记录、智能文书、预约费用和审计看板一体化管理</p>
      </div>
      <el-form :model="form" class="login-form" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="form.username" size="large" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" size="large" type="password" show-password placeholder="密码" />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" @click="handleLogin">登录系统</el-button>
        <div class="accounts">
          <el-tag>admin / 123456</el-tag>
          <el-tag type="success">lawyer / 123456</el-tag>
          <el-tag type="warning">assistant / 123456</el-tag>
          <el-tag type="danger">client / 123456</el-tag>
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

const getHomePath = (role) => {
  if (role === 'ADMIN') return '/dashboard'
  if (role === 'LAWYER') return '/case'
  if (role === 'ASSISTANT') return '/client'
  if (role === 'CLIENT') return '/case'
  return '/login'
}

const handleLogin = async () => {
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data.token, res.data.user)
    ElMessage.success('登录成功')
    router.push(getHomePath(res.data.user?.role))
  } finally {
    loading.value = false
  }
}
</script>
