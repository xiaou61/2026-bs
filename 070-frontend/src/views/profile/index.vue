<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="14">
        <el-card>
          <template #header>个人信息</template>
          <el-form :model="profile" label-width="90px">
            <el-form-item label="用户名"><el-input v-model="profile.username" disabled /></el-form-item>
            <el-form-item label="昵称"><el-input v-model="profile.nickname" /></el-form-item>
            <el-form-item label="手机号"><el-input v-model="profile.phone" /></el-form-item>
            <el-form-item label="邮箱"><el-input v-model="profile.email" /></el-form-item>
            <el-form-item label="头像地址"><el-input v-model="profile.avatar" /></el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSaveProfile">保存资料</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card class="mb16">
          <template #header>修改密码</template>
          <el-form :model="pwd" label-width="80px">
            <el-form-item label="旧密码"><el-input v-model="pwd.oldPassword" type="password" show-password /></el-form-item>
            <el-form-item label="新密码"><el-input v-model="pwd.newPassword" type="password" show-password /></el-form-item>
            <el-form-item>
              <el-button type="warning" @click="handleUpdatePwd">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <template #header>余额充值</template>
          <el-form :model="rechargeForm" label-width="80px">
            <el-form-item label="当前余额">
              <el-text type="primary" size="large">¥{{ Number(profile.balance || 0).toFixed(2) }}</el-text>
            </el-form-item>
            <el-form-item label="充值金额"><el-input-number v-model="rechargeForm.amount" :min="1" :precision="2" /></el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleRecharge">充值</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../store/user'
import { getInfo, recharge, updatePassword, updateProfile } from '../../api'

const userStore = useUserStore()

const profile = reactive({
  id: null,
  username: '',
  nickname: '',
  phone: '',
  email: '',
  avatar: '',
  balance: 0
})

const pwd = reactive({ oldPassword: '', newPassword: '' })
const rechargeForm = reactive({ amount: 100 })

const loadInfo = async () => {
  const res = await getInfo()
  Object.assign(profile, res.data || {})
  userStore.user = res.data
  localStorage.setItem('user', JSON.stringify(res.data || null))
}

const handleSaveProfile = async () => {
  await updateProfile(profile)
  ElMessage.success('资料已更新')
  loadInfo()
}

const handleUpdatePwd = async () => {
  await updatePassword(pwd)
  ElMessage.success('密码已修改，请重新登录')
  userStore.clearLogin()
  location.href = '/login'
}

const handleRecharge = async () => {
  await recharge(rechargeForm)
  ElMessage.success('充值成功')
  loadInfo()
}

onMounted(loadInfo)
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}
</style>
