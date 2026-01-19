<template>
  <div class="login-page">
    <div class="login-box">
      <h2>房屋租赁管理系统</h2>
      <p class="subtitle">用户登录</p>
      <el-form ref="formRef" :model="form" :rules="rules" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" size="large" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" :loading="loading" @click="handleLogin" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="footer-links">
        <span>还没有账号？</span>
        <router-link to="/register">立即注册</router-link>
      </div>
      <div class="demo-accounts">
        <p>测试账号：</p>
        <el-tag @click="fillDemo('admin', 'admin123')" style="cursor:pointer">管理员</el-tag>
        <el-tag type="success" @click="fillDemo('landlord', '123456')" style="cursor:pointer">房东</el-tag>
        <el-tag type="warning" @click="fillDemo('tenant', '123456')" style="cursor:pointer">租客</el-tag>
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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.2);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 8px;
  color: #333;
}

.subtitle {
  text-align: center;
  color: #999;
  margin-bottom: 30px;
}

.footer-links {
  text-align: center;
  color: #999;
}

.footer-links a {
  color: #409eff;
}

.demo-accounts {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px dashed #eee;
  text-align: center;
}

.demo-accounts p {
  font-size: 12px;
  color: #999;
  margin-bottom: 8px;
}

.demo-accounts .el-tag {
  margin: 0 4px;
}
</style>
