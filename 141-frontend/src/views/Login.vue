<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>固定资产 RFID 盘点与借用归还系统</h2>
      <p>面向企业培训学习场景的课程学习、存放位置、折旧记录和闲置处置平台</p>
      <el-form :model="form" label-width="70px" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password /></el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
      </el-form>
      <div class="account-list">
        <el-tag>admin / 123456</el-tag>
        <el-tag>trainer / 123456</el-tag>
        <el-tag>employee / 123456</el-tag>
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







