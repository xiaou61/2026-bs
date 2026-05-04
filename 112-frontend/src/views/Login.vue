<template>
  <div class="login">
    <el-card class="login-card">
      <div class="login-title">
        <h2>零信任设备准入与访问控制管理系统</h2>
        <p>设备准入、身份校验、风险评估、访问策略、审计事件闭环</p>
      </div>
      <el-form :model="form" label-width="70px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">登录</el-button>
      </el-form>
      <div style="margin-top: 16px; display: flex; gap: 8px; flex-wrap: wrap">
        <el-tag>admin / 123456</el-tag>
        <el-tag type="success">security / 123456</el-tag>
        <el-tag type="warning">network / 123456</el-tag>
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
