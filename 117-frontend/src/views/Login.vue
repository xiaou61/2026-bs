<template>
  <div class="login">
    <el-card class="login-card">
      <div class="login-title">
        <h2>本地生活服务券核销与商户结算系统</h2>
        <p>服务券发放、到店核销、结算明细、商户打款、申诉统计闭环</p>
      </div>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">登录</el-button>
      </el-form>
      <div class="account-tags">
        <el-tag>admin / 123456</el-tag>
        <el-tag type="success">merchant / 123456</el-tag>
        <el-tag type="warning">cashier / 123456</el-tag>
        <el-tag type="danger">finance / 123456</el-tag>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const handleLogin = async () => {
  const res = await login(form)
  userStore.setLogin(res.data)
  ElMessage.success('登录成功')
  router.push('/dashboard')
}
</script>
