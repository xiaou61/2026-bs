<template>
  <div class="login-wrap">
    <el-card class="login-card">
      <h2 class="title">手工艺品销售系统</h2>
      <el-tabs v-model="tab">
        <el-tab-pane label="登录" name="login">
          <el-form ref="loginRef" :model="loginForm" :rules="loginRules">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="用户名" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" show-password placeholder="密码" maxlength="100" />
            </el-form-item>
            <el-button type="primary" style="width: 100%" :loading="loading" @click="handleLogin">登录</el-button>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form ref="regRef" :model="regForm" :rules="regRules">
            <el-form-item prop="username">
              <el-input v-model="regForm.username" placeholder="用户名" maxlength="50" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="regForm.password" type="password" show-password placeholder="密码" maxlength="100" />
            </el-form-item>
            <el-form-item prop="nickname">
              <el-input v-model="regForm.nickname" placeholder="昵称" maxlength="50" />
            </el-form-item>
            <el-button type="success" style="width: 100%" :loading="loading" @click="handleRegister">注册</el-button>
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
const regForm = reactive({ username: '', password: '', nickname: '' })

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { max: 50, message: '用户名不能超过50字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { max: 100, message: '密码不能超过100字符', trigger: 'blur' }
  ]
}

const regRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { max: 50, message: '用户名不能超过50字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { max: 100, message: '密码不能超过100字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { max: 50, message: '昵称不能超过50字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  await loginRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setUser(res.data.user, res.data.token)
    ElMessage.success('登录成功')
    router.push('/')
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
    Object.assign(regForm, { username: '', password: '', nickname: '' })
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
  background: linear-gradient(135deg, #13293d, #006494, #247ba0);
}

.login-card {
  width: 420px;
  border-radius: 14px;
}

.title {
  text-align: center;
  margin: 0 0 20px;
  color: #184e77;
}
</style>

