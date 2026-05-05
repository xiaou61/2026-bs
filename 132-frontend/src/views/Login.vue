<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>医疗器械借用消毒与追踪管理系统</h2>
      <p>面向医疗器械管理的借用归还、消毒追踪、二维码流转和风险预警平台</p>
      <el-form :model="form" label-width="70px" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="account-list">
        <el-tag>admin / 123456</el-tag>
        <el-tag>nurse / 123456</el-tag>
        <el-tag>sterilizer / 123456</el-tag>
        <el-tag>manager / 123456</el-tag>
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
const submit = async () => {
  const res = await login(form)
  userStore.setAuth(res.data.token, res.data.user)
  ElMessage.success('登录成功')
  router.push('/dashboard')
}
</script>
