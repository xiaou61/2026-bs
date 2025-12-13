<template>
  <div style="display:flex;align-items:center;justify-content:center;height:100%">
    <el-card style="width:360px">
      <h2>登录</h2>
      <el-form :model="f" @submit.prevent="onLogin">
        <el-form-item>
          <el-input v-model="f.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="f.password" type="password" placeholder="密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%" @click="onLogin">登录</el-button>
        </el-form-item>
        <el-divider />
        <el-form-item>
          <el-input v-model="r.username" placeholder="注册用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="r.password" type="password" placeholder="注册密码" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="r.role" placeholder="角色">
            <el-option label="ELDER" value="ELDER" />
            <el-option label="DOCTOR" value="DOCTOR" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" @click="onRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../api'
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
