<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>在线心理评测与咨询系统</h2>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" @submit.prevent="handleLogin">
            <el-form-item>
              <el-input v-model="loginForm.username" placeholder="用户名" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="loginForm.password" type="password" placeholder="密码" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" @submit.prevent="handleRegister">
            <el-form-item>
              <el-input v-model="registerForm.username" placeholder="用户名" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.password" type="password" placeholder="密码" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.nickname" placeholder="昵称" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.phone" placeholder="手机号" />
            </el-form-item>
            <el-form-item>
              <el-input v-model="registerForm.email" placeholder="邮箱" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleRegister" style="width: 100%">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '@/api/auth'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('login')

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  password: '',
  nickname: '',
  phone: '',
  email: ''
})

const handleLogin = async () => {
  try {
    const res = await login(loginForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error('登录失败')
  }
}

const handleRegister = async () => {
  try {
    const res = await register(registerForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('注册成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error('注册失败')
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  padding: 20px;
}

h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
</style>
