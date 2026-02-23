<template>
  <div class="login-wrap">
    <el-card class="login-card">
      <h2 class="title">大学生一体化服务平台</h2>
      <el-tabs v-model="tab">
        <el-tab-pane label="登录" name="login">
          <el-form ref="loginRef" :model="loginForm" :rules="loginRules">
            <el-form-item prop="username"><el-input v-model="loginForm.username" placeholder="用户名" maxlength="50" /></el-form-item>
            <el-form-item prop="password"><el-input v-model="loginForm.password" type="password" show-password placeholder="密码" maxlength="100" /></el-form-item>
            <el-button type="primary" style="width: 100%" :loading="loading" @click="handleLogin">登录</el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form ref="regRef" :model="regForm" :rules="regRules">
            <el-form-item prop="username"><el-input v-model="regForm.username" placeholder="用户名" maxlength="50" /></el-form-item>
            <el-form-item prop="password"><el-input v-model="regForm.password" type="password" show-password placeholder="密码" maxlength="100" /></el-form-item>
            <el-form-item prop="nickname"><el-input v-model="regForm.nickname" placeholder="昵称" maxlength="50" /></el-form-item>
            <el-form-item prop="college"><el-input v-model="regForm.college" placeholder="学院" maxlength="100" /></el-form-item>
            <el-form-item prop="major"><el-input v-model="regForm.major" placeholder="专业" maxlength="100" /></el-form-item>
            <el-form-item prop="grade"><el-input v-model="regForm.grade" placeholder="年级" maxlength="20" /></el-form-item>
            <el-button type="success" style="width: 100%" :loading="loading" @click="handleRegister">注册学生账号</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const tab = ref('login')
const loginRef = ref()
const regRef = ref()

const loginForm = reactive({ username: 'admin', password: '123456' })
const regForm = reactive({ username: '', password: '', nickname: '', college: '', major: '', grade: '' })

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const regRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setUser(res.data.user, res.data.token)
    ElMessage.success('登录成功')
    if (res.data.user?.role === 'ADMIN' || res.data.user?.role === 'TEACHER') {
      router.push('/dashboard')
    } else {
      router.push('/course')
    }
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await regRef.value.validate()
  loading.value = true
  try {
    await register(regForm)
    ElMessage.success('注册成功，请登录')
    tab.value = 'login'
    Object.assign(regForm, { username: '', password: '', nickname: '', college: '', major: '', grade: '' })
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-wrap {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(130deg, #0f766e, #0ea5e9, #2563eb);
}

.login-card {
  width: 460px;
  border-radius: 14px;
}

.title {
  margin: 0 0 18px;
  text-align: center;
  color: #0f172a;
}
</style>
