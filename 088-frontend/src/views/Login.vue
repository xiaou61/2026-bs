<template>
  <div class="auth-shell">
    <div class="auth-card">
      <div class="auth-eyebrow">Welcome Back</div>
      <h1>进入孩童收养信息平台</h1>
      <el-form :model="form" @submit.prevent>
        <el-form-item>
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button class="auth-button" type="primary" @click="handleLogin">登录</el-button>
      </el-form>
      <div class="auth-tip">
        还没有账号？
        <router-link to="/register">立即注册</router-link>
      </div>
    </div>
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
const form = reactive({
  username: '',
  password: ''
})

const handleLogin = async () => {
  const res = await login(form)
  userStore.setAuth(res.data.token, res.data.userInfo)
  ElMessage.success('登录成功')
  router.push(['admin', 'reviewer'].includes(res.data.userInfo.role) ? '/admin/dashboard' : '/')
}
</script>

<style scoped>
.auth-shell {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
}

.auth-card {
  width: min(460px, 100%);
  padding: 38px;
  border-radius: 30px;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: var(--shadow);
}

.auth-eyebrow {
  font-size: 12px;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  color: var(--brand);
}

.auth-card h1 {
  margin: 14px 0 24px;
  font-size: 34px;
  line-height: 1.2;
  font-family: "STZhongsong", "Noto Serif SC", serif;
}

.auth-button {
  width: 100%;
  height: 46px;
}

.auth-tip {
  margin-top: 16px;
  color: var(--subtle);
}
</style>
