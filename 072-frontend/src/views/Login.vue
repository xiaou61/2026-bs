<template>
  <div class="login-container">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏔️</div>
      <div class="deco deco-2">❄️</div>
      <div class="deco deco-3">⛄</div>
      <div class="deco deco-4">🎿</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🏔️</div>
        <h2 class="title">哈尔滨文旅平台</h2>
        <p class="subtitle">冰雪之城 · 东方小巴黎</p>
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="Lock" placeholder="请输入密码" type="password" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="submit-btn" @click="handleLogin" :loading="loading">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 开启冰雪之旅
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="large" class="register-btn" @click="showRegister = true">
            <el-icon><UserFilled /></el-icon> 注册新账号
          </el-button>
        </el-form-item>
      </el-form>

      <div class="demo-accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="success" effect="plain" round>user / 123456</el-tag>
        </div>
      </div>

      <div class="harbin-tips">
        <span>❄️ 冰雪大世界 · 欢迎来尔滨 ❄️</span>
      </div>
    </div>

    <el-dialog v-model="showRegister" title="用户注册" width="460px" class="register-dialog">
      <el-form :model="registerForm" ref="registerFormRef" class="register-form">
        <el-form-item label="用户名" required>
          <el-input v-model="registerForm.username" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号" prefix-icon="Phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRegister = false">取消</el-button>
        <el-button type="primary" @click="handleRegister" :loading="loading" class="dialog-submit-btn">
          <el-icon><CirclePlus /></el-icon> 注册
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const registerFormRef = ref()
const loading = ref(false)
const showRegister = ref(false)

const form = reactive({ username: '', password: '' })
const registerForm = reactive({ username: '', password: '', nickname: '', phone: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    userStore.setToken(res.data.token)
    userStore.setUser(res.data.user)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  if (!registerForm.username || !registerForm.password) {
    ElMessage.warning('请填写用户名和密码')
    return
  }
  loading.value = true
  try {
    await register(registerForm)
    ElMessage.success('注册成功，请登录')
    showRegister.value = false
    form.username = registerForm.username
    form.password = registerForm.password
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
  background: linear-gradient(135deg, #e0f7fa 0%, #80deea 30%, #4dd0e1 60%, #26c6da 100%);
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

.deco {
  position: absolute;
  font-size: 56px;
  opacity: 0.2;
  animation: floatDeco 8s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 64px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 48px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 44px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.2; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.3; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.25; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.22; }
}

.login-box {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(38, 198, 218, 0.3);
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
  background: linear-gradient(90deg, #e0f7fa, #80deea, #4dd0e1, #26c6da, #00bcd4, #e0f7fa);
  background-size: 400% 100%;
  animation: winterGradient 5s linear infinite;
}

@keyframes winterGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 400% 0%; }
}

.logo-section {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  font-size: 80px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #006064, #00838f, #0097a7, #00acc1);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #0097a7;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(0, 151, 167, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(0, 151, 167, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(0, 151, 167, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #0097a7;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #006064 0%, #00838f 30%, #0097a7 60%, #00acc1 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(0, 151, 167, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(0, 151, 167, 0.4) !important;
}

.register-btn {
  width: 100%;
  height: 48px;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 2px;
  border-radius: 14px !important;
  border: 2px solid #0097a7 !important;
  color: #0097a7 !important;
  background: transparent !important;
  transition: all 0.3s ease !important;
}

.register-btn:hover {
  background: rgba(0, 151, 167, 0.05) !important;
  transform: translateY(-2px);
}

.demo-accounts {
  margin-top: 24px;
}

.demo-accounts :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.account-tags {
  display: flex;
  justify-content: center;
  gap: 12px;
}

.account-tags .el-tag {
  cursor: pointer;
  transition: all 0.3s ease;
}

.account-tags .el-tag:hover {
  transform: translateY(-2px);
}

.harbin-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E0F7FA;
}

.harbin-tips span {
  color: #0097a7;
  font-size: 14px;
  letter-spacing: 2px;
  opacity: 0.6;
}

/* 注册对话框样式 */
.register-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #006064, #0097a7);
  padding: 20px 24px;
  margin: 0;
}

.register-dialog :deep(.el-dialog__title) {
  color: white;
  font-weight: 600;
}

.register-dialog :deep(.el-dialog__headerbtn .el-dialog__close) {
  color: white;
}

.register-form :deep(.el-input__wrapper) {
  border-radius: 10px !important;
}

.dialog-submit-btn {
  background: linear-gradient(135deg, #006064, #0097a7) !important;
  border: none !important;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 22px;
  }

  .logo-icon {
    font-size: 64px;
  }
}
</style>
