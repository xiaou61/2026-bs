<template>
  <div class="auth-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🖼️</div>
      <div class="deco deco-2">🎨</div>
      <div class="deco deco-3">📷</div>
      <div class="deco deco-4">✨</div>
    </div>

    <div class="auth-card glass-card">
      <div class="auth-side">
        <div class="section-eyebrow">欢迎回来</div>
        <div class="logo-icon">🖼️</div>
        <h1 class="display-title auth-title">登录你的壁纸空间</h1>
        <p class="auth-text">登录后可收藏喜欢的作品、管理自己的投稿，并进入后台进行运营审核。</p>
        <div class="side-features">
          <div class="feature-item">
            <span class="feature-icon">⭐</span>
            <span>收藏喜欢的壁纸</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">📤</span>
            <span>分享你的作品</span>
          </div>
          <div class="feature-item">
            <span class="feature-icon">🎯</span>
            <span>个性化推荐</span>
          </div>
        </div>
      </div>
      <div class="auth-form">
        <h2 class="form-title">账号登录</h2>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top" class="login-form">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" prefix-icon="Lock" size="large" @keyup.enter="handleSubmit" />
          </el-form-item>
          <el-button type="primary" class="submit-btn" size="large" @click="handleSubmit">
            <el-icon><Key /></el-icon> 登录壁纸空间
          </el-button>
        </el-form>
        <div class="auth-foot">
          <el-divider>其他操作</el-divider>
          <span class="hint-text">还没有账号？</span>
          <router-link to="/register" class="register-link">
            <el-icon><UserFilled /></el-icon> 立即注册
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'
import { useUserStore } from '../store/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()

const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  const res = await login(form)
  userStore.setToken(res.data.token)
  userStore.setUserInfo(res.data.userInfo)
  ElMessage.success('登录成功')
  router.push(res.data.userInfo?.role === 'admin' ? '/admin/dashboard' : '/')
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 30%, #f093fb 60%, #4facfe 100%);
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
  opacity: 0.15;
  animation: floatDeco 8s ease-in-out infinite;
}

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; font-size: 56px; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); opacity: 0.15; }
  25% { transform: translateY(-20px) rotate(10deg); opacity: 0.25; }
  50% { transform: translateY(-10px) rotate(-5deg); opacity: 0.2; }
  75% { transform: translateY(-15px) rotate(5deg); opacity: 0.18; }
}

.auth-card {
  width: min(1080px, 100%);
  display: grid;
  grid-template-columns: 1fr 0.9fr;
  border-radius: 34px;
  overflow: hidden;
  box-shadow: 0 24px 64px rgba(102, 126, 234, 0.3);
  position: relative;
  z-index: 10;
}

.auth-side {
  padding: 54px;
  background:
    radial-gradient(circle at top left, rgba(125, 211, 252, 0.35), transparent 35%),
    linear-gradient(160deg, rgba(8, 17, 31, 0.85), rgba(9, 16, 29, 0.95)),
    url('https://picsum.photos/id/1003/900/1200') center/cover;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.section-eyebrow {
  color: #4facfe;
  font-size: 14px;
  font-weight: 600;
  letter-spacing: 4px;
  text-transform: uppercase;
}

.logo-icon {
  font-size: 64px;
  margin: 16px 0;
  animation: bounce 2s ease-in-out infinite;
  display: inline-block;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0) scale(1); }
  50% { transform: translateY(-10px) scale(1.1); }
}

.auth-title {
  margin: 18px 0 16px;
  font-size: 48px;
  line-height: 1.1;
  color: white;
  font-weight: 800;
}

.auth-text {
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.8;
  font-size: 15px;
}

.side-features {
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

.feature-icon {
  font-size: 20px;
}

.auth-form {
  padding: 54px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-title {
  margin: 0 0 32px;
  font-size: 28px;
  font-weight: 800;
  background: linear-gradient(135deg, #667eea, #764ba2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 12px !important;
  box-shadow: 0 2px 12px rgba(102, 126, 234, 0.08) !important;
  padding: 4px 12px;
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.15) !important;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.25) !important;
}

.login-form :deep(.el-input__prefix) {
  color: #667eea;
}

.login-form :deep(.el-form-item__label) {
  font-weight: 600;
  color: #333;
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3) !important;
  transition: all 0.3s ease !important;
  margin-top: 10px;
}

.submit-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.4) !important;
}

.auth-foot {
  margin-top: 24px;
  text-align: center;
}

.auth-foot :deep(.el-divider__text) {
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
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  font-size: 14px;
  margin-left: 8px;
  transition: all 0.3s ease;
}

.register-link:hover {
  color: #764ba2;
  transform: translateX(4px);
}

@media (max-width: 900px) {
  .auth-card {
    grid-template-columns: 1fr;
  }

  .auth-side {
    padding: 32px;
    text-align: center;
  }

  .auth-title {
    font-size: 32px;
  }

  .side-features {
    align-items: center;
  }

  .auth-form {
    padding: 32px;
  }
}
</style>
