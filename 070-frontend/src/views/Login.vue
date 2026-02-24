<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>最优网络购票系统</h2>
      <el-form :model="form" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button text type="primary" @click="registerVisible = true">没有账号？去注册</el-button>
        </el-form-item>
      </el-form>
      <div class="tips">
        <div>管理员：admin / 123456</div>
        <div>用户：user / 123456</div>
      </div>
    </el-card>

    <el-dialog v-model="registerVisible" title="注册账号" width="420px">
      <el-form :model="registerForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="registerForm.nickname" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="registerForm.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="registerVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRegister">注册</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/user'
import { login, register } from '../api'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const registerVisible = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const registerForm = reactive({
  username: '',
  password: '',
  nickname: '',
  phone: ''
})

const handleLogin = async () => {
  loading.value = true
  try {
    const res = await login(form)
    userStore.setLogin(res.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  await register(registerForm)
  ElMessage.success('注册成功，请登录')
  registerVisible.value = false
  form.username = registerForm.username
  form.password = registerForm.password
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(120deg, #f6f9fc, #d8e2f3);
}

.login-card {
  width: 420px;
}

.tips {
  color: #666;
  font-size: 13px;
  line-height: 1.8;
}
</style>
