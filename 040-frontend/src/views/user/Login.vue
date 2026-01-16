<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="title">社区车辆违停处理系统</h2>
      
      <el-tabs v-model="activeTab" class="tabs">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="用户名" prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef">
            <el-form-item prop="username">
              <el-input v-model="registerForm.username" placeholder="用户名" prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="registerForm.password" type="password" placeholder="密码" prefix-icon="Lock" show-password />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="registerForm.phone" placeholder="手机号" prefix-icon="Phone" />
            </el-form-item>
            <el-form-item prop="realName">
              <el-input v-model="registerForm.realName" placeholder="真实姓名" />
            </el-form-item>
            <el-form-item prop="roomNumber">
              <el-input v-model="registerForm.roomNumber" placeholder="房号" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleRegister" :loading="loading" style="width: 100%">注册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="admin-link">
        <router-link to="/admin/login">管理员登录</router-link>
      </div>
    </div>
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
const loading = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  password: '',
  phone: '',
  realName: '',
  roomNumber: ''
})

const loginFormRef = ref()
const registerFormRef = ref()

const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const registerRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  roomNumber: [{ required: true, message: '请输入房号', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await registerFormRef.value.validate()
  loading.value = true
  try {
    const res = await register(registerForm.value)
    userStore.setToken(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('注册成功')
    router.push('/home')
  } catch (error) {
    ElMessage.error(error.message || '注册失败')
  } finally {
    loading.value = false
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

.login-box {
  width: 420px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 24px;
}

.admin-link {
  text-align: center;
  margin-top: 20px;
}

.admin-link a {
  color: #667eea;
  text-decoration: none;
  font-size: 14px;
}

.admin-link a:hover {
  text-decoration: underline;
}
</style>
