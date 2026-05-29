<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import request from '@/api/request'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({
  username: '',
  password: ''
})

const login = () => {
  request.post('/user/login', form.value).then(res => {
    if (res.code === 200) {
      ElMessage.success('登录成功')
      localStorage.setItem('user', JSON.stringify(res.data))
      router.push('/')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const goRegister = () => {
  router.push('/register')
}
</script>

<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="pet-icon icon-1">🐱</div>
      <div class="pet-icon icon-2">🐶</div>
      <div class="pet-icon icon-3">🐰</div>
    </div>
    
    <div class="login-box">
      <!-- 头部 -->
      <div class="login-header">
        <div class="logo-icon">
          <span class="pet-emoji">🐾</span>
        </div>
        <h1 class="login-title">萌宠之家</h1>
        <p class="login-subtitle">Pet Lovers Platform</p>
      </div>
      
      <el-form :model="form" class="login-form">
        <el-form-item>
          <el-input 
            v-model="form.username" 
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            class="custom-input"
          />
        </el-form-item>
        <el-form-item>
          <el-input 
            v-model="form.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            class="custom-input"
            @keyup.enter="login"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            size="large"
            class="login-btn"
            @click="login"
          >
            进入萌宠世界
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button 
            size="large"
            class="register-btn"
            @click="goRegister"
          >
            注册新账号
          </el-button>
        </el-form-item>
      </el-form>
      
      <!-- 底部 -->
      <div class="login-bottom">
        <p>🐾 爱护宠物 · 温暖相伴</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #FF8F00 0%, #FFA000 50%, #FF8F00 100%);
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
  background: rgba(121, 85, 72, 0.15);
}

.circle-1 {
  width: 350px;
  height: 350px;
  top: -120px;
  right: -80px;
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
  left: 10%;
  animation: float 12s ease-in-out infinite;
}

.pet-icon {
  position: absolute;
  font-size: 40px;
  animation: float 6s ease-in-out infinite;
}

.icon-1 {
  top: 15%;
  right: 10%;
  animation-delay: 0s;
}

.icon-2 {
  top: 60%;
  left: 15%;
  animation-delay: 2s;
}

.icon-3 {
  bottom: 20%;
  right: 20%;
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
  background: linear-gradient(135deg, #FF8F00 0%, #795548 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 20px rgba(255, 143, 0, 0.4);
}

.pet-emoji {
  font-size: 40px;
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #FF8F00;
  margin-bottom: 8px;
  font-family: var(--font-heading);
}

.login-subtitle {
  font-size: 13px;
  color: #795548;
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
  box-shadow: 0 4px 12px rgba(255, 143, 0, 0.3);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #FF8F00 0%, #FFA000 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(255, 143, 0, 0.4);
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(255, 143, 0, 0.5);
}

.register-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: transparent !important;
  border: 2px solid #FF8F00 !important;
  color: #FF8F00 !important;
  transition: all 0.3s ease;
}

.register-btn:hover {
  background: #FF8F00 !important;
  color: white !important;
  transform: translateY(-2px);
}

/* 底部 */
.login-bottom {
  text-align: center;
  margin-top: 25px;
  padding-top: 20px;
  border-top: 1px solid #FFCC80;
}

.login-bottom p {
  font-size: 14px;
  color: #795548;
  font-weight: 500;
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
