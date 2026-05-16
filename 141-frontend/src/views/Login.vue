<template>
  <div class="login">
    <el-card class="login-card">
      <div class="login-title">
        <h2>固定资产 RFID 盘点与借用归还系统</h2>
        <p>面向固定资产管理场景的 RFID 盘点、借用归还、维修折旧和预警通知协同平台</p>
      </div>
      <el-form :model="form" label-width="70px" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="account-tags">
        <el-tag>admin / 123456</el-tag>
        <el-tag>assetadmin / 123456</el-tag>
        <el-tag>borrower / 123456</el-tag>
        <el-tag>auditor / 123456</el-tag>
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
const ROLE_HOME = {
  ADMIN: '/dashboard',
  ASSET_ADMIN: '/asset',
  BORROWER: '/borrow-apply',
  AUDITOR: '/inventory-record'
}
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const submit = async () => {
  const res = await login(form)
  userStore.setAuth(res.data.token, res.data.user)
  ElMessage.success('登录成功')
  router.push(ROLE_HOME[res.data.user?.role] || '/dashboard')
}
</script>







