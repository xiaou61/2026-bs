<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="chat-bubble bubble-1">💬</div>
      <div class="chat-bubble bubble-2">🗨️</div>
      <div class="chat-bubble bubble-3">💭</div>
    </div>
    
    <div class="login-box">
      <!-- 头部 -->
      <div class="login-header">
        <div class="logo-icon">
          <el-icon :size="48"><ChatDotRound /></el-icon>
        </div>
        <h1 class="login-title">实时聊天</h1>
        <p class="login-subtitle">Real-time Chat System</p>
      </div>
      
      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            class="custom-input"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin" 
            :loading="loading"
            size="large"
            class="login-btn"
          >
            <span v-if="!loading">开始聊天</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
        <div class="register-link">
          还没有账号？<router-link to="/register">立即注册</router-link>
        </div>
      </el-form>
      
      <!-- 测试账号 -->
      <div class="test-accounts">
        <p class="accounts-title">测试账号</p>
        <div class="accounts-list">
          <span class="account-item" @click="fillAccount('admin')">admin</span>
          <span class="account-item" @click="fillAccount('zhangsan')">张三</span>
          <span class="account-item" @click="fillAccount('lisi')">李四</span>
        </div>
      </div>
      
      <!-- 底部 -->
      <div class="login-bottom">
        <p>密码均为 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = ref({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  try {
    const valid = await formRef.value.validate()
    if (!valid) return
  } catch (error) {
    return
  }
  
  loading.value = true
  try {
    const data = await login(form.value)
    userStore.setToken(data.token)
    userStore.setUserInfo(data.user)
    ElMessage.success('登录成功')
    
    setTimeout(() => {
      router.push('/')
    }, 100)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const fillAccount = (username) => {
  form.value.username = username
  form.value.password = '123456'
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #FF6D00 0%, #FF9100 50%, #FF6D00 100%);
  position: relative;
  overflow: hidden;
}

/* 背景装饰 */
.bg-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(74, 20, 140, 0.15);
}

.circle-1 {
  width: 350px;
  height: 350px;
  top: -120px;
  left: -80px;
  animation: float 8s ease-in-out infinite;
}

.circle-2 {
  width: 250px;
  height: 250px;
  bottom: -80px;
  right: -60px;
  animation: float 10s ease-in-out infinite reverse;
}

.circle-3 {
  width: 180px;
  height: 180px;
  top: 40%;
  right: 10%;
  animation: float 12s ease-in-out infinite;
}

.chat-bubble {
  position: absolute;
  font-size: 40px;
  animation: float 6s ease-in-out infinite;
}

.bubble-1 {
  top: 15%;
  left: 10%;
  animation-delay: 0s;
}

.bubble-2 {
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}

.bubble-3 {
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-25px) rotate(5deg);
  }
}

/* 登录框 */
.login-box {
  width: 420px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 24px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.3);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 10;
  animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 头部 */
.login-header {
  text-align: center;
  margin-bottom: 35px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  background: linear-gradient(135deg, #FF6D00 0%, #4A148C 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 8px 20px rgba(255, 109, 0, 0.4);
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #FF6D00;
  margin-bottom: 8px;
  font-family: var(--font-heading);
}

.login-subtitle {
  font-size: 13px;
  color: #9E9E9E;
  letter-spacing: 1px;
}

/* 表单 */
.login-form {
  margin-top: 20px;
}

.custom-input :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 8px 15px;
  transition: all 0.3s ease;
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 12px rgba(255, 109, 0, 0.3);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #FF6D00 0%, #FF9100 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(255, 109, 0, 0.4);
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(255, 109, 0, 0.5);
}

.register-link {
  text-align: center;
  font-size: 14px;
  color: #757575;
  margin-top: 10px;
}

.register-link a {
  color: #FF6D00;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.register-link a:hover {
  color: #E65100;
}

/* 测试账号 */
.test-accounts {
  margin-top: 25px;
  padding-top: 20px;
  border-top: 1px solid #E0E0E0;
}

.accounts-title {
  text-align: center;
  font-size: 13px;
  color: #9E9E9E;
  margin-bottom: 12px;
}

.accounts-list {
  display: flex;
  justify-content: center;
  gap: 12px;
}

.account-item {
  padding: 6px 16px;
  background: #FFF3E0;
  border-radius: 20px;
  font-size: 13px;
  color: #E65100;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
}

.account-item:hover {
  background: #FF6D00;
  color: white;
  transform: translateY(-2px);
}

/* 底部 */
.login-bottom {
  text-align: center;
  margin-top: 20px;
}

.login-bottom p {
  font-size: 12px;
  color: #BDBDBD;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
    width: 90%;
    padding: 30px;
  }
  
  .login-title {
    font-size: 24px;
  }
  
  .logo-icon {
    width: 60px;
    height: 60px;
  }
}
</style>

