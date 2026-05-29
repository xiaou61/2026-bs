<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="car car-1">🚗</div>
      <div class="car car-2">🚙</div>
      <div class="car car-3">🅿️</div>
      <div class="car car-4">🏠</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🅿️</div>
        <h2 class="title">社区车辆违停处理系统</h2>
        <p class="subtitle">规范停车 · 和谐社区</p>
      </div>

      <el-tabs v-model="activeTab" class="custom-tabs">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" :loading="loading" size="large" class="submit-btn">
                <template v-if="!loading">
                  <el-icon><Key /></el-icon> 安全登录
                </template>
                <template v-else>登录中...</template>
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" class="login-form">
            <el-form-item prop="username">
              <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="registerForm.phone" placeholder="请输入手机号" prefix-icon="Phone" />
            </el-form-item>
            <el-form-item prop="realName">
              <el-input v-model="registerForm.realName" placeholder="请输入真实姓名" prefix-icon="UserFilled" />
            </el-form-item>
            <el-form-item prop="roomNumber">
              <el-input v-model="registerForm.roomNumber" placeholder="请输入房号" prefix-icon="HomeFilled" />
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleRegister" :loading="loading" size="large" class="submit-btn register-btn">
                <template v-if="!loading">
                  <el-icon><CirclePlus /></el-icon> 立即注册
                </template>
                <template v-else>注册中...</template>
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <div class="admin-link">
        <el-divider>其他入口</el-divider>
        <router-link to="/admin/login" class="admin-entry">
          <el-icon><Setting /></el-icon> 管理员登录
        </router-link>
      </div>

      <div class="community-tips">
        <span>🏘️ 文明停车 · 从我做起 🏘️</span>
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
  background: linear-gradient(135deg, #1565C0 0%, #0D47A1 30%, #01579B 60%, #006064 100%);
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

.car {
  position: absolute;
  font-size: 56px;
  opacity: 0.2;
  animation: driveCar 12s linear infinite;
}

.car-1 { top: 20%; left: -10%; animation-delay: 0s; }
.car-2 { top: 50%; right: -10%; animation-delay: 3s; animation-direction: reverse; }
.car-3 { bottom: 25%; left: 15%; animation-delay: 5s; font-size: 48px; animation: none; }
.car-4 { top: 15%; right: 20%; animation-delay: 2s; font-size: 64px; animation: none; opacity: 0.15; }

@keyframes driveCar {
  0% { transform: translateX(-100px); opacity: 0; }
  10% { opacity: 0.2; }
  90% { opacity: 0.2; }
  100% { transform: translateX(calc(100vw + 100px)); opacity: 0; }
}

.login-box {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(0, 0, 0, 0.25);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;
}

.login-box::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #1565C0, #42A5F5, #1565C0);
  background-size: 200% 100%;
  animation: shimmer 3s linear infinite;
}

@keyframes shimmer {
  0% { background-position: 200% 0; }
  100% { background-position: -200% 0; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: pulse 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

.title {
  font-size: 26px;
  font-weight: 800;
  background: linear-gradient(135deg, #0D47A1, #1565C0, #1976D2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #1976D2;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.custom-tabs {
  margin-top: 24px;
}

.custom-tabs :deep(.el-tabs__header) {
  margin-bottom: 24px;
}

.custom-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.custom-tabs :deep(.el-tabs__active-bar) {
  background: linear-gradient(90deg, #1565C0, #42A5F5);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  font-weight: 600;
  color: #666;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #1565C0;
}

.login-form {
  margin-top: 16px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(21, 101, 192, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(21, 101, 192, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(21, 101, 192, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #1565C0;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #0D47A1 0%, #1565C0 50%, #1976D2 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(21, 101, 192, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(21, 101, 192, 0.4) !important;
}

.register-btn {
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 50%, #66BB6A 100%) !important;
  box-shadow: 0 8px 24px rgba(76, 175, 80, 0.3) !important;
}

.register-btn:hover {
  box-shadow: 0 12px 32px rgba(76, 175, 80, 0.4) !important;
}

.admin-link {
  margin-top: 24px;
}

.admin-link :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.admin-entry {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: #1565C0;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.admin-entry:hover {
  color: #0D47A1;
  transform: translateX(4px);
}

.community-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #e0e0e0;
}

.community-tips span {
  color: #1976D2;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 20px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
