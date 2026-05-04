<template>
  <div class="login">
    <el-card class="login-card">
      <div class="login-title">
        <h2>个人数据隐私授权与访问审计平台</h2>
        <p>授权目的、访问申请、撤销请求、风险预警、审计报告闭环</p>
      </div>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">登录</el-button>
      </el-form>
      <div style="margin-top: 16px; display: flex; gap: 8px; flex-wrap: wrap">
        <el-tag>admin / 123456</el-tag>
        <el-tag type="success">privacy / 123456</el-tag>
        <el-tag type="warning">datauser / 123456</el-tag>
        <el-tag type="danger">auditor / 123456</el-tag>
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
