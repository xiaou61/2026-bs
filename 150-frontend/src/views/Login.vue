<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>医院门诊检查预约与报告回传管理系统</h2>
      <p>覆盖门诊检查预约、签到叫号、报告回传、异常提醒和复诊建议的多角色业务协同平台</p>
      <el-form :model="form" label-width="70px" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="account-list">
        <el-tag>admin / 123456</el-tag>
        <el-tag>doctor / 123456</el-tag>
        <el-tag>tech / 123456</el-tag>
        <el-tag>patient / 123456</el-tag>
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
  router.push('/')
}
</script>
