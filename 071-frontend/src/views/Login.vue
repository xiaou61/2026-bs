<template>
  <div class="login-container">
    <div class="login-card">
      <h2>{{ isLogin ? '共享单车管理系统' : '用户注册' }}</h2>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="form.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" prefix-icon="Lock" type="password" placeholder="请输入密码" size="large" show-password />
        </el-form-item>
        <el-form-item v-if="!isLogin" prop="phone">
          <el-input v-model="form.phone" prefix-icon="Phone" placeholder="请输入手机号" size="large" />
        </el-form-item>
        <el-form-item v-if="!isLogin" prop="realName">
          <el-input v-model="form.realName" prefix-icon="UserFilled" placeholder="请输入真实姓名" size="large" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" style="width:100%" @click="handleSubmit" :loading="loading">
            {{ isLogin ? '登 录' : '注 册' }}
          </el-button>
        </el-form-item>
      </el-form>
      <div class="switch-mode">
        <el-link type="primary" @click="switchMode">{{ isLogin ? '没有账号？去注册' : '已有账号？去登录' }}</el-link>
      </div>
    </div>
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
const loading = ref(false)
const isLogin = ref(true)
const form = reactive({ username: '', password: '', phone: '', realName: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    if (isLogin.value) {
      const res = await login({ username: form.username, password: form.password })
      userStore.setUser(res.data.user, res.data.token)
      ElMessage.success('登录成功')
      router.push('/dashboard')
    } else {
      await register(form)
      ElMessage.success('注册成功，请登录')
      isLogin.value = true
    }
  } finally {
    loading.value = false
  }
}

const switchMode = () => {
  isLogin.value = !isLogin.value
}
</script>

<style scoped>
.login-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-card { width: 420px; padding: 40px; background: #fff; border-radius: 12px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
.login-card h2 { text-align: center; margin-bottom: 30px; color: #333; font-size: 24px; }
.switch-mode { text-align: center; margin-top: 10px; }
</style>
