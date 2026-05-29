<template>
  <div class="login-page">
    <!-- 装饰背景 -->
    <div class="bg-decoration">
      <div class="deco deco-1">🏡</div>
      <div class="deco deco-2">🌲</div>
      <div class="deco deco-3">⛰️</div>
      <div class="deco deco-4">🌅</div>
    </div>

    <el-card class="login-card" shadow="always">
      <div class="logo-section">
        <div class="logo-icon">🏡</div>
        <h2 class="title">民宿预订系统</h2>
        <p class="subtitle">诗和远方 · 从这里出发</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="0" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" size="large" class="submit-btn">
            <template v-if="!loading">
              <el-icon><Key /></el-icon> 开启旅程
            </template>
            <template v-else>登录中...</template>
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-section">
        <el-divider>其他操作</el-divider>
        <span class="hint-text">还没有账号？</span>
        <router-link to="/register" class="link">
          <el-icon><UserFilled /></el-icon> 立即注册
        </router-link>
      </div>

      <div class="travel-tips">
        <span>🌄 世界那么大，我想去看看 🌄</span>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { login } from '@/api/auth'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({ username: '', password: '' })
const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value?.validate()
  loading.value = true
  try {
    const res: any = await login(form)
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    const redirect = route.query.redirect as string
    router.push(redirect || '/')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #4FC3F7 0%, #29B6F6 30%, #03A9F4 60%, #0288D1 100%);
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

.deco-1 { top: 15%; left: 10%; animation-delay: 0s; }
.deco-2 { top: 25%; right: 15%; animation-delay: 2s; font-size: 48px; }
.deco-3 { bottom: 25%; left: 20%; animation-delay: 4s; font-size: 64px; }
.deco-4 { top: 50%; right: 10%; animation-delay: 1s; font-size: 40px; }

@keyframes floatDeco {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  25% { transform: translateY(-15px) rotate(3deg); }
  50% { transform: translateY(-8px) rotate(-2deg); }
  75% { transform: translateY(-12px) rotate(2deg); }
}

.login-card {
  width: 460px;
  padding: 48px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 28px;
  box-shadow: 0 24px 64px rgba(2, 136, 209, 0.2);
  position: relative;
  z-index: 10;
  backdrop-filter: blur(20px);
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 6px;
    background: linear-gradient(90deg, #4FC3F7, #29B6F6, #03A9F4, #0288D1, #4FC3F7);
    background-size: 300% 100%;
    animation: gradientMove 4s linear infinite;
  }
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
  background: linear-gradient(135deg, #01579B, #0288D1, #03A9F4);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin: 0 0 8px 0;
  letter-spacing: 3px;
}

.subtitle {
  color: #03A9F4;
  font-size: 14px;
  margin: 0;
  letter-spacing: 6px;
  font-weight: 500;
}

.login-form {
  margin-top: 24px;

  :deep(.el-input__wrapper) {
    border-radius: 12px !important;
    box-shadow: 0 2px 12px rgba(3, 169, 244, 0.08) !important;
    padding: 4px 12px;
    transition: all 0.3s ease;

    &:hover {
      box-shadow: 0 4px 16px rgba(3, 169, 244, 0.15) !important;
    }

    &.is-focus {
      box-shadow: 0 4px 20px rgba(3, 169, 244, 0.25) !important;
    }
  }

  :deep(.el-input__prefix) {
    color: #03A9F4;
  }
}

.submit-btn {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border-radius: 14px !important;
  background: linear-gradient(135deg, #01579B 0%, #0288D1 50%, #03A9F4 100%) !important;
  border: none !important;
  box-shadow: 0 8px 24px rgba(3, 169, 244, 0.3) !important;
  transition: all 0.3s ease !important;

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 32px rgba(3, 169, 244, 0.4) !important;
  }

  &:active {
    transform: translateY(0);
  }
}

.register-section {
  margin-top: 24px;
  text-align: center;

  :deep(.el-divider__text) {
    background: rgba(255, 255, 255, 0.95);
    color: #999;
    font-size: 12px;
  }
}

.hint-text {
  color: #666;
  font-size: 14px;
}

.link {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: #03A9F4;
  text-decoration: none;
  font-weight: 600;
  font-size: 14px;
  margin-left: 8px;
  transition: all 0.3s ease;

  &:hover {
    color: #01579B;
    transform: translateX(4px);
  }
}

.travel-tips {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #E1F5FE;

  span {
    color: #03A9F4;
    font-size: 12px;
    letter-spacing: 2px;
    opacity: 0.6;
  }
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card {
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
