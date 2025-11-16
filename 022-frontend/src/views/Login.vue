<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>校园自习室座位预约系统</h2>
        <p>请登录您的账号</p>
      </div>
      
      <el-form ref="loginForm" :model="form" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="form.username" 
            placeholder="请输入学号"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="form.password" 
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            size="large"
            :loading="loading"
            @click="handleLogin"
            class="login-btn"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <router-link to="/register">还没有账号？立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginForm = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 6, max: 20, message: '学号长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  const valid = await loginForm.value.validate()
  if (!valid) return
  
  loading.value = true
  try {
    await userStore.login(form)
    ElMessage.success('登录成功')
    
    // 根据用户角色跳转到不同页面
    if (userStore.isAdmin) {
      router.push('/admin/dashboard')
    } else {
      router.push('/student/home')
    }
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 420px;
  background: white;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  
  .login-header {
    text-align: center;
    margin-bottom: 30px;
    
    h2 {
      font-size: 24px;
      color: #333;
      margin-bottom: 10px;
    }
    
    p {
      color: #666;
      font-size: 14px;
    }
  }
  
  .login-form {
    .login-btn {
      width: 100%;
    }
  }
  
  .login-footer {
    text-align: center;
    margin-top: 20px;
    
    a {
      color: #409eff;
      font-size: 14px;
      
      &:hover {
        color: #66b1ff;
      }
    }
  }
}
</style>