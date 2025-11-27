<template>
  <div class="profile-container">
    <el-card>
      <template #header>
        <span>个人信息</span>
      </template>
      <el-descriptions :column="2">
        <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ user.nickname }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ user.phone }}</el-descriptions-item>
        <el-descriptions-item label="余额">¥{{ user.balance }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ getRoleText() }}</el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 20px">
        <el-button type="primary" @click="showRecharge = true">充值</el-button>
      </div>
    </el-card>
    
    <el-dialog v-model="showRecharge" title="余额充值" width="300px">
      <el-input-number v-model="rechargeAmount" :min="1" :max="10000" />
      <template #footer>
        <el-button @click="showRecharge = false">取消</el-button>
        <el-button type="primary" @click="handleRecharge">确认充值</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import request from '../utils/request'
import { ElMessage } from 'element-plus'

const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const showRecharge = ref(false)
const rechargeAmount = ref(100)

const getRoleText = () => {
  const map = { 'USER': '普通用户', 'ART IST': '画师', 'ADMIN': '管理员' }
  return map[user.value.role] || '未知'
}

const handleRecharge = async () => {
  try {
    await request.put('/user/balance', null, { params: { id: user.value.id, amount: rechargeAmount.value } })
    ElMessage.success('充值成功')
    user.value.balance = Number(user.value.balance) + rechargeAmount.value
    localStorage.setItem('user', JSON.stringify(user.value))
    showRecharge.value = false
  } catch (error) {
    console.error(error)
  }
}
</script>
