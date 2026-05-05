<template>
  <div class="login">
    <el-card class="login-card">
      <div class="login-title">
        <h2>直播电商选品排期与售后工单系统</h2>
        <p>选品入池、直播排期、样品话术、订单履约、售后退款、主播绩效闭环</p>
      </div>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">登录</el-button>
      </el-form>
      <div class="account-tags">
        <el-tag>admin / 123456</el-tag>
        <el-tag type="success">operator / 123456</el-tag>
        <el-tag type="warning">service / 123456</el-tag>
        <el-tag type="danger">merchant / 123456</el-tag>
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
