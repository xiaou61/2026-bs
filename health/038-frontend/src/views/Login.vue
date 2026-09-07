<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="food food-1">🥗</div>
      <div class="food food-2">🍎</div>
      <div class="food food-3">🥑</div>
      <div class="food food-4">🥕</div>
      <div class="food food-5">🍊</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🥗</div>
        <h2 class="title">个人饮食管理系统</h2>
        <p class="subtitle">健康饮食 · 科学管理 · 均衡营养</p>
      </div>

      <el-form :model="loginForm" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="success"
            @click="handleLogin"
            :loading="loading"
            size="large"
            class="submit-btn"
          >
            <template v-if="!loading">
              <el-icon><Apple /></el-icon> 健康登录
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="tips">
        <el-divider>测试账号</el-divider>
        <div class="demo-accounts">
          <el-tag type="success" round effect="plain">admin / 123456</el-tag>
          <el-tag type="info" round effect="plain">user1 / 123456</el-tag>
        </div>
      </div>

      <div class="nutrition-tips">
        <span>💧 每日八杯水 · 🥗 蔬果不能少 · 🏃 坚持运动</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  
  try {
    const res = await request.post('/user/login', null, {
      params: loginForm.value
    })
    localStorage.setItem('token', res.data)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #4CAF50 0%, #8BC34A 30%, #CDDC39 60%, #FFEB3B 100%);
  position: relative;
  overflow: hidden;
}

.bg-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.food {
  position: absolute;
  font-size: 64px;
  opacity: 0.3;
  animation: floatFood 8s ease-in-out infinite;
}

.food-1 { top: 10%; left: 10%; animation-delay: 0s; }
.food-2 { top: 20%; right: 15%; animation-delay: 1s; font-size: 48px; }
.food-3 { bottom: 30%; left: 20%; animation-delay: 2s; font-size: 56px; }
.food-4 { bottom: 15%; right: 10%; animation-delay: 3s; }
.food-5 { top: 50%; left: 5%; animation-delay: 4s; font-size: 40px; }

@keyframes floatFood {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-20px) rotate(5deg); }
  50% { transform: translateY(-10px) rotate(-3deg); }
  75% { transform: translateY(-25px) rotate(3deg); }
}

.login-card {
  width: 460px;
  border-radius: 28px !important;
  border: none !important;
  background: rgba(255, 255, 255, 0.95) !important;
  backdrop-filter: blur(20px);
  box-shadow: 0 24px 64px rgba(76, 175, 80, 0.2) !important;
  position: relative;
  z-index: 10;
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #4CAF50, #8BC34A, #CDDC39, #FFEB3B, #4CAF50);
  background-size: 300% 100%;
  animation: gradientMove 4s linear infinite;
}

@keyframes gradientMove {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
  padding-top: 16px;
}

.logo-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.05); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #2E7D32, #4CAF50, #66BB6A);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #4CAF50;
  font-size: 14px;
  margin: 0;
  letter-spacing: 4px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(76, 175, 80, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(76, 175, 80, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(76, 175, 80, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #4CAF50;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 50%, #66BB6A 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(76, 175, 80, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(76, 175, 80, 0.4) !important;
}

.tips {
  margin-top: 24px;
}

.tips :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.demo-accounts {
  display: flex;
  justify-content: center;
  gap: 12px;
  flex-wrap: wrap;
}

.demo-accounts .el-tag {
  font-size: 13px;
  padding: 8px 16px;
}

.nutrition-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.nutrition-tips span {
  color: #4CAF50;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.7;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card {
    width: 90%;
    margin: 16px;
  }

  .title {
    font-size: 22px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
