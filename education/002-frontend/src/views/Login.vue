<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
    
    <div class="login-box">
      <!-- 头部 -->
      <div class="login-header">
        <div class="logo-icon">
          <el-icon :size="48"><Reading /></el-icon>
        </div>
        <h1 class="login-title">在线选课系统</h1>
        <p class="login-subtitle">Course Selection & Grade Management</p>
      </div>
      
      <el-form :model="loginForm" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="请输入用户名" 
            size="large"
            prefix-icon="User"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="请输入密码" 
            show-password 
            size="large"
            prefix-icon="Lock"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin" 
            class="login-btn" 
            :loading="loading"
            size="large"
          >
            <span v-if="!loading">登录系统</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button 
            @click="goRegister" 
            class="register-btn"
            size="large"
          >
            学生注册
          </el-button>
        </el-form-item>
      </el-form>
      
      <!-- 测试账号提示 -->
      <div class="test-accounts">
        <p class="accounts-title">测试账号</p>
        <div class="accounts-list">
          <span class="account-item" @click="fillAccount('admin', '123456')">管理员</span>
          <span class="account-item" @click="fillAccount('student1', '123456')">学生</span>
          <span class="account-item" @click="fillAccount('teacher1', '123456')">教师</span>
        </div>
      </div>
      
      <!-- 底部 -->
      <div class="login-bottom">
        <p>© 2026 在线选课与成绩管理系统</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const formRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
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
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await userStore.login(loginForm.username, loginForm.password)
      } finally {
        loading.value = false
      }
    }
  })
}

const goRegister = () => {
  router.push('/register')
}

const fillAccount = (username, password) => {
  loginForm.username = username
  loginForm.password = password
}
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1A237E 0%, #283593 50%, #1A237E 100%);
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
  background: rgba(249, 168, 37, 0.1);
}

.circle-1 {
  width: 400px;
  height: 400px;
  top: -150px;
  right: -100px;
  animation: float 8s ease-in-out infinite;
}

.circle-2 {
  width: 250px;
  height: 250px;
  bottom: -80px;
  left: -60px;
  animation: float 10s ease-in-out infinite reverse;
}

.circle-3 {
  width: 180px;
  height: 180px;
  top: 40%;
  right: 15%;
  animation: float 12s ease-in-out infinite;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-30px) rotate(5deg);
  }
}

/* 登录框 */
.login-box {
  width: 440px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
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
  background: linear-gradient(135deg, #1A237E 0%, #F9A825 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 8px 20px rgba(26, 35, 126, 0.4);
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #1A237E;
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
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 8px 15px;
  transition: all 0.3s ease;
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 12px rgba(26, 35, 126, 0.3);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #1A237E 0%, #3949AB 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(26, 35, 126, 0.4);
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(26, 35, 126, 0.5);
}

.register-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 10px;
  background: transparent !important;
  border: 2px solid #1A237E !important;
  color: #1A237E !important;
  transition: all 0.3s ease;
}

.register-btn:hover {
  background: #1A237E !important;
  color: white !important;
  transform: translateY(-2px);
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
  gap: 16px;
}

.account-item {
  padding: 6px 16px;
  background: #F5F7FA;
  border-radius: 20px;
  font-size: 13px;
  color: #616161;
  cursor: pointer;
  transition: all 0.3s ease;
}

.account-item:hover {
  background: #1A237E;
  color: white;
  transform: translateY(-2px);
}

/* 底部 */
.login-bottom {
  text-align: center;
  margin-top: 25px;
  padding-top: 15px;
  border-top: 1px solid #E0E0E0;
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

