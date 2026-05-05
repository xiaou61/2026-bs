<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>无人机巡检任务调度与缺陷上报平台</h2>
      <p>面向无人机巡检业务的任务调度、缺陷上报、整改闭环和统计分析平台</p>
      <el-form :model="form" label-width="70px" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="account-list">
        <el-tag>admin / 123456</el-tag>
        <el-tag>pilot / 123456</el-tag>
        <el-tag>engineer / 123456</el-tag>
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
