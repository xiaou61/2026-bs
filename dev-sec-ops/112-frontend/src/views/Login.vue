<template>
  <div class="login">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🔐</div>
      <div class="deco deco-2">🖥️</div>
      <div class="deco deco-3">🛡️</div>
      <div class="deco deco-4">🔑</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🔐</div>
        <h2 class="title">零信任设备准入与访问控制</h2>
        <p class="subtitle">设备准入、身份校验、风险评估、访问策略、审计事件闭环</p>
      </div>

      <el-form :model="form" class="login-form" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入账号" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-button type="primary" size="large" class="submit-btn" @click="handleLogin">
          <el-icon><Key /></el-icon> 登录系统
        </el-button>
      </el-form>

      <div class="accounts">
        <el-divider>测试账号</el-divider>
        <div class="account-tags">
          <el-tag effect="plain" round>admin / 123456</el-tag>
          <el-tag type="success" effect="plain" round>security / 123456</el-tag>
          <el-tag type="warning" effect="plain" round>network / 123456</el-tag>
          <el-tag type="danger" effect="plain" round>auditor / 123456</el-tag>
        </div>
      </div>

      <div class="zero-trust-tips">
        <span>🔐 零信任架构 · 永不信任 · 始终验证 🔐</span>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'
const router = useRouter()
const userStore = useUserStore()
const form = reactive({ username: 'admin', password: '123456' })
const handleLogin = async () => {
  const res = await login(form)
  userStore.setLogin(res.data)
  ElMessage.success('登录成功')
  router.push('/dashboard')
}
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1B1B1B 0%, #2D2D2D 30%, #424242 60%, #616161 100%);
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
  font-size: 48px;
  opacity: 0.1;
  animation: floatDeco 8s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 64px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 40px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 56px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.1; }
  25% { transform: translateY(-15px) rotate(5deg); opacity: 0.2; }
  50% { transform: translateY(-8px) rotate(-3deg); opacity: 0.15; }
  75% { transform: translateY(-12px) rotate(3deg); opacity: 0.12; }
}

.login-card {
  width: 480px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(0, 0, 0, 0.4);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;
}

.login-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #424242, #616161, #757575, #9E9E9E, #424242);
  background-size: 300% 100%;
  animation: zeroTrustGradient 4s linear infinite;
}

@keyframes zeroTrustGradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 300% 0%; }
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
  font-size: 24px;
  font-weight: 800;
  background: linear-gradient(135deg, #212121, #424242, #616161);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 2px;
}

.subtitle {
  color: #757575;
  font-size: 13px;
  margin: 0;
  letter-spacing: 1px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(66, 66, 66, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(66, 66, 66, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(66, 66, 66, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #616161;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #212121 0%, #424242 50%, #616161 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(33, 33, 33, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(33, 33, 33, 0.4) !important;
}

.accounts {
  margin-top: 24px;
}

.accounts :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.account-tags {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 8px;
}

.account-tags .el-tag {
  cursor: pointer;
  transition: all 0.3s ease;
}

.account-tags .el-tag:hover {
  transform: translateY(-2px);
}

.zero-trust-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E0E0E0;
}

.zero-trust-tips span {
  color: #616161;
  font-size: 12px;
  letter-spacing: 2px;
  opacity: 0.6;
}

@media (max-width: 480px) {
  .login-card {
    width: 90%;
    margin: 16px;
    padding: 32px 24px;
  }

  .title {
    font-size: 18px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
