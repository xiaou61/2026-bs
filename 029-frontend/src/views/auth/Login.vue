<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1>🌿 中药食疗平台</h1>
        <p>登录账户</p>
      </div>

      <el-form
        ref="formRef"
        :model="loginForm"
        :rules="rules"
        label-width="0"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名或邮箱"
            prefix-icon="User"
            clearable
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            placeholder="密码"
            prefix-icon="Lock"
            type="password"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="handleLogin"
            :loading="loading"
            class="login-btn"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <el-link type="primary" @click="goToRegister">没有账户？立即注册</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '../../stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名或邮箱', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    loading.value = true

    await userStore.login({
      username: loginForm.username,
      password: loginForm.password
    })

    ElMessage.success('登录成功')
    
    // 重定向到之前的页面或首页
    const redirect = route.query.redirect
    if (redirect) {
      router.push(redirect)
    } else {
      router.push({ name: 'home' })
    }
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error(error?.message || error?.msg || '登录失败，请检查用户名和密码')
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  router.push({ name: 'register' })
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  padding: 40px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  margin: 0;
  font-size: 24px;
  color: #27ae60;
}

.login-header p {
  margin: 10px 0 0 0;
  color: #666;
  font-size: 14px;
}

.login-btn {
  width: 100%;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
}

:deep(.el-input__prefix) {
  display: none;
}

:deep(.el-form-item) {
  margin-bottom: 18px;
}

:deep(.el-form-item:last-of-type) {
  margin-bottom: 24px;
}
</style>
