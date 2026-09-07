<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="health-icon icon-1">❤️</div>
      <div class="health-icon icon-2">🏥</div>
      <div class="health-icon icon-3">👴</div>
    </div>
    
    <div class="login-box">
      <!-- 头部 -->
      <div class="login-header">
        <div class="logo-icon">
          <el-icon :size="48"><FirstAidKit /></el-icon>
        </div>
        <h1 class="login-title">老年健康管理</h1>
        <p class="login-subtitle">Senior Health Management</p>
      </div>
      
      <el-tabs v-model="activeTab" class="login-tabs">
        <!-- 登录 -->
        <el-tab-pane label="登录" name="login">
          <el-form :model="f" @submit.prevent="onLogin" class="login-form">
            <el-form-item>
              <el-input 
                v-model="f.username" 
                placeholder="请输入用户名"
                prefix-icon="User"
                size="large"
                class="custom-input"
              />
            </el-form-item>
            <el-form-item>
              <el-input 
                v-model="f.password" 
                type="password"
                placeholder="请输入密码"
                prefix-icon="Lock"
                size="large"
                show-password
                class="custom-input"
                @keyup.enter="onLogin"
              />
            </el-form-item>
            <el-form-item>
              <el-button 
                type="primary" 
                size="large"
                class="login-btn"
                @click="onLogin"
              >
                进入健康平台
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <!-- 注册 -->
        <el-tab-pane label="注册" name="register">
          <el-form :model="r" @submit.prevent="onRegister" class="login-form">
            <el-form-item>
              <el-input 
                v-model="r.username" 
                placeholder="请输入注册用户名"
                prefix-icon="User"
                size="large"
                class="custom-input"
              />
            </el-form-item>
            <el-form-item>
              <el-input 
                v-model="r.password" 
                type="password"
                placeholder="请输入注册密码"
                prefix-icon="Lock"
                size="large"
                show-password
                class="custom-input"
              />
            </el-form-item>
            <el-form-item>
              <el-select 
                v-model="r.role" 
                placeholder="请选择角色"
                size="large"
                style="width: 100%"
                class="custom-select"
              >
                <el-option label="老年人" value="ELDER" />
                <el-option label="医生" value="DOCTOR" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button 
                type="primary" 
                size="large"
                class="login-btn"
                @click="onRegister"
              >
                注册账号
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      
      <!-- 底部 -->
      <div class="login-bottom">
        <p>❤️ 关爱老人 · 健康相伴</p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../api'

const activeTab = ref('login')
const f = reactive({ username:'', password:'' })
const r = reactive({ username:'', password:'', role:'ELDER' })
const onLogin = async()=>{
  const { data } = await api.post('/api/auth/login', f)
  if(data.code!==0){ ElMessage.error(data.message); return }
  localStorage.setItem('token', data.data.token)
  localStorage.setItem('user', JSON.stringify(data.data))
  location.href = '/app'
}
const onRegister = async()=>{
  const { data } = await api.post('/api/auth/register', r)
  if(data.code!==0){ ElMessage.error(data.message); return }
  localStorage.setItem('token', data.data.token)
  localStorage.setItem('user', JSON.stringify(data.data))
  location.href = '/app'
}
</script>
