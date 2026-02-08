<template>
  <div class="login-container">
    <div class="login-box">
      <h2>制造装备物联及生产管理ERP系统</h2>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="Lock" placeholder="请输入密码" type="password" size="large" show-password @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" style="width:100%" :loading="loading" @click="handleLogin">登 录</el-button>
        </el-form-item>
      </el-form>
      <div class="demo-accounts">
        <p>演示账号：admin / manager / operator / inspector 密码均为 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)
const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    userStore.setToken(res.data.token)
    userStore.setUser(res.data.user)
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background: linear-gradient(135deg, #1a237e 0%, #0d47a1 50%, #01579b 100%); }
.login-box { width: 420px; padding: 40px; background: #fff; border-radius: 12px; box-shadow: 0 8px 32px rgba(0,0,0,0.2); }
.login-box h2 { text-align: center; margin-bottom: 30px; color: #1a237e; font-size: 20px; }
.demo-accounts { text-align: center; margin-top: 10px; }
.demo-accounts p { font-size: 12px; color: #999; }
</style>
