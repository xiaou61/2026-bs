<template>
  <div class="auth-shell">
    <div class="auth-card">
      <div class="auth-eyebrow">Create Account</div>
      <h1>注册申请人账号</h1>
      <el-form :model="form" @submit.prevent>
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.realName" placeholder="姓名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.phone" placeholder="手机号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" show-password placeholder="密码" />
        </el-form-item>
        <el-button class="auth-button" type="primary" @click="handleRegister">完成注册</el-button>
      </el-form>
      <div class="auth-tip">
        已有账号？
        <router-link to="/login">去登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '../api'

const router = useRouter()
const form = reactive({
  username: '',
  realName: '',
  phone: '',
  password: ''
})

const handleRegister = async () => {
  await register(form)
  ElMessage.success('注册成功，请登录')
  router.push('/login')
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
  width: min(480px, 100%);
  padding: 38px;
  border-radius: 30px;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: var(--shadow);
}

.auth-eyebrow {
  font-size: 12px;
  letter-spacing: 0.22em;
  text-transform: uppercase;
  color: var(--accent);
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
