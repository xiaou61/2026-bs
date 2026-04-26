<template>
  <div class="login-wrap">
    <el-card class="login-card">
      <h2 class="title">线上医院挂号系统</h2>
      <p class="subtitle">管理员、医生、患者三角色统一入口</p>
      <el-tabs v-model="tab">
        <el-tab-pane label="登录" name="login">
          <el-form ref="loginRef" :model="loginForm" :rules="loginRules">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="用户名" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" show-password placeholder="密码" maxlength="100" />
            </el-form-item>
            <div class="account-tip">默认账号：admin / doctor01 / patient01，密码均为 123456</div>
            <el-button type="primary" style="width: 100%" :loading="loading" @click="handleLogin">登录</el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="患者注册" name="register">
          <el-form ref="regRef" :model="regForm" :rules="regRules">
            <el-form-item prop="username">
              <el-input v-model="regForm.username" placeholder="登录账号" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="regForm.password" type="password" show-password placeholder="登录密码" maxlength="100" />
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input v-model="regForm.nickname" placeholder="姓名或昵称" maxlength="50" />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="regForm.phone" placeholder="手机号" maxlength="20" />
            </el-form-item>
            <el-button type="success" style="width: 100%" :loading="loading" @click="handleRegister">注册患者账号</el-button>
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
const regForm = reactive({ username: '', password: '', nickname: '', phone: '' })

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const regRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入姓名或昵称', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setUser(res.data.user, res.data.token)
    ElMessage.success('登录成功')
    router.push('/dashboard')
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
    Object.assign(regForm, { username: '', password: '', nickname: '', phone: '' })
    tab.value = 'login'
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
  background: linear-gradient(135deg, #0f172a, #0ea5e9, #14b8a6);
}

.login-card {
  width: 440px;
  border-radius: 16px;
}

.title {
  margin: 0;
  text-align: center;
  color: #0f172a;
}

.subtitle {
  text-align: center;
  color: #475569;
  margin: 8px 0 20px;
}

.account-tip {
  font-size: 12px;
  color: #64748b;
  margin-bottom: 14px;
}
</style>
