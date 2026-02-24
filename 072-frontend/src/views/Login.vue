<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>🏔️ 哈尔滨文旅平台</h1>
        <p>冰雪之城 · 东方小巴黎</p>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="User" placeholder="用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="Lock" placeholder="密码" type="password" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" @click="handleLogin" :loading="loading" style="width: 100%">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="large" @click="showRegister = true" style="width: 100%">注册新账号</el-button>
        </el-form-item>
      </el-form>
      <div class="test-accounts">
        <p>测试账号：admin/123456 | user/123456</p>
      </div>
    </div>
    <el-dialog v-model="showRegister" title="用户注册" width="400px">
      <el-form :model="registerForm" ref="registerFormRef">
        <el-form-item label="用户名" required>
          <el-input v-model="registerForm.username" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="registerForm.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="registerForm.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRegister = false">取消</el-button>
        <el-button type="primary" @click="handleRegister" :loading="loading">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const registerFormRef = ref()
const loading = ref(false)
const showRegister = ref(false)

const form = reactive({ username: '', password: '' })
const registerForm = reactive({ username: '', password: '', nickname: '', phone: '' })
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
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  if (!registerForm.username || !registerForm.password) {
    ElMessage.warning('请填写用户名和密码')
    return
  }
  loading.value = true
  try {
    await register(registerForm)
    ElMessage.success('注册成功，请登录')
    showRegister.value = false
    form.username = registerForm.username
    form.password = registerForm.password
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.2);
}
.login-header {
  text-align: center;
  margin-bottom: 30px;
}
.login-header h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}
.login-header p {
  color: #999;
  font-size: 14px;
}
.test-accounts {
  text-align: center;
  color: #999;
  font-size: 12px;
  margin-top: 15px;
}
</style>
