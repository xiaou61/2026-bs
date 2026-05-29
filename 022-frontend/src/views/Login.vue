<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="study-icon icon-1">📚</div>
      <div class="study-icon icon-2">🪑</div>
      <div class="study-icon icon-3">📖</div>
    </div>
    
    <div class="login-box">
      <!-- 头部 -->
      <div class="login-header">
        <div class="logo-icon">
          <el-icon :size="48"><Reading /></el-icon>
        </div>
        <h1 class="login-title">自习室预约</h1>
        <p class="login-subtitle">Study Room Reservation</p>
      </div>
      
      <el-form ref="loginForm" :model="form" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入学号或管理员账号"
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
            size="large"
            :loading="loading"
            @click="handleLogin"
            class="login-btn"
          >
            <span v-if="!loading">预约座位</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <router-link to="/register">还没有账号？立即注册</router-link>
      </div>
      
      <!-- 底部 -->
      <div class="login-bottom">
        <p>📖 安静学习 · 高效预约</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginForm = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 4, max: 20, message: '账号长度在 4 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  const valid = await loginForm.value.validate()
  if (!valid) return
  
  loading.value = true
  try {
    await userStore.login(form)
    ElMessage.success('登录成功')
    
    // 根据用户角色跳转到不同页面
    if (userStore.isAdmin) {
      router.push('/admin/dashboard')
    } else {
      router.push('/student/home')
    }
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #5C6BC0 0%, #7986CB 50%, #5C6BC0 100%);
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
  background: rgba(255, 112, 67, 0.15);
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

.study-icon {
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
  background: linear-gradient(135deg, #5C6BC0 0%, #FF7043 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 8px 20px rgba(92, 107, 192, 0.4);
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #5C6BC0;
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
  box-shadow: 0 4px 12px rgba(92, 107, 192, 0.3);
}

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  background: linear-gradient(135deg, #5C6BC0 0%, #7986CB 100%) !important;
  border: none !important;
  box-shadow: 0 8px 20px rgba(92, 107, 192, 0.4);
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 25px rgba(92, 107, 192, 0.5);
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  
  a {
    color: #5C6BC0;
    font-size: 14px;
    text-decoration: none;
    font-weight: 600;
    transition: all 0.3s ease;
    
    &:hover {
      color: #3F51B5;
    }
  }
}

/* 底部 */
.login-bottom {
  text-align: center;
  margin-top: 25px;
  padding-top: 20px;
  border-top: 1px solid #E0E0E0;
}

.login-bottom p {
  font-size: 14px;
  color: #FF7043;
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
