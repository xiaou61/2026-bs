<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="house house-1">🏠</div>
      <div class="house house-2">🏢</div>
      <div class="house house-3">🏘️</div>
      <div class="house house-4">🔑</div>
    </div>

    <div class="login-box">
      <div class="logo-section">
        <div class="logo-icon">🏠</div>
        <h2 class="title">房屋租赁管理系统</h2>
        <p class="subtitle">安居乐业 · 轻松租房</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="handleLogin" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" :loading="loading" @click="handleLogin" class="submit-btn">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 安心登录
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="footer-links">
        <el-divider>其他操作</el-divider>
        <span class="hint-text">还没有账号？</span>
        <router-link to="/register" class="register-link">
          <el-icon><UserFilled /></el-icon> 立即注册
        </router-link>
      </div>

      <div class="demo-accounts">
        <p class="demo-title">🏷️ 测试账号</p>
        <div class="demo-tags">
          <el-tag @click="fillDemo('admin', 'admin123')" effect="plain" round>管理员</el-tag>
          <el-tag type="success" @click="fillDemo('landlord', '123456')" effect="plain" round>房东</el-tag>
          <el-tag type="warning" @click="fillDemo('tenant', '123456')" effect="plain" round>租客</el-tag>
        </div>
      </div>

      <div class="housing-tips">
        <span>🏡 找到理想的家 · 从这里开始 🏡</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const formRef = ref()
const loading = ref(false)
const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const fillDemo = (username, password) => {
  form.username = username
  form.password = password
}

const handleLogin = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    await userStore.login(form)
    ElMessage.success('登录成功')
    const role = userStore.userInfo?.role
    if (role === 'admin') router.push('/admin')
    else if (role === 'landlord') router.push('/landlord')
    else router.push('/tenant')
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #FF6B35 0%, #F7931E 30%, #FFB347 60%, #FFD700 100%);
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

.house {
  position: absolute;
  font-size: 56px;
  opacity: 0.2;
  animation: floatHouse 8s ease-in-out infinite;
}

.house-1 { top: 15%; left: 10%; animation-delay: 0s; }
.house-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 48px; }
.house-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 64px; }
.house-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatHouse {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(3deg); }
  50% { transform: translateY(-8px) rotate(-2deg); }
  75% { transform: translateY(-12px) rotate(2deg); }
}

.login-box {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(255, 107, 53, 0.2);
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
  background: linear-gradient(90deg, #FF6B35, #F7931E, #FFB347, #FFD700, #FF6B35);
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
}

.logo-icon {
  font-size: 72px;
  margin-bottom: 16px;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.title {
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #E65100, #FF6B35, #F7931E);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #FF6B35;
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
  box-shadow: 0 2px 12px rgba(255, 107, 53, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(255, 107, 53, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #FF6B35;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #E65100 0%, #FF6B35 50%, #F7931E 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(255, 107, 53, 0.3) !important;
  transition: all 0.3s ease !important;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(255, 107, 53, 0.4) !important;
}

.footer-links {
  margin-top: 24px;
  text-align: center;
}

.footer-links :deep(.el-divider__text) {
  background: rgba(255, 255, 255, 0.95);
  color: #999;
  font-size: 12px;
}

.hint-text {
  color: #666;
  font-size: 14px;
}

.register-link {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: #FF6B35;
  text-decoration: none;
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #E65100;
  transform: translateX(4px);
}

.demo-accounts {
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #FFF3E0;
  text-align: center;
}

.demo-title {
  font-size: 13px;
  color: #666;
  margin-bottom: 12px;
  font-weight: 500;
}

.demo-tags {
  display: flex;
  justify-content: center;
  gap: 12px;
}

.demo-tags .el-tag {
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 13px;
  padding: 8px 16px;
}

.demo-tags .el-tag:hover {
  transform: translateY(-2px);
}

.housing-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #FFF3E0;
}

.housing-tips span {
  color: #FF6B35;
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
    font-size: 22px;
  }

  .logo-icon {
    font-size: 56px;
  }
}
</style>
