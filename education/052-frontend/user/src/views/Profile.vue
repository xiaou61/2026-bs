<template>
  <div class="profile">
    <el-card>
      <template #header><h3>个人信息</h3></template>
      <el-form :model="userForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="userForm.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="userForm.nickname" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userForm.email" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="userForm.phone" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateInfo">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header><h3>修改密码</h3></template>
      <el-form :model="pwdForm" label-width="80px">
        <el-form-item label="原密码">
          <el-input v-model="pwdForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="pwdForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updatePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi } from '../api'

const userForm = reactive({ username: '', nickname: '', email: '', phone: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '' })

onMounted(async () => {
  const user = await userApi.getInfo()
  Object.assign(userForm, user)
})

const updateInfo = async () => {
  await userApi.updateInfo(userForm)
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  user.nickname = userForm.nickname
  localStorage.setItem('user', JSON.stringify(user))
  ElMessage.success('修改成功')
}

const updatePassword = async () => {
  if (!pwdForm.oldPassword || !pwdForm.newPassword) {
    ElMessage.warning('请填写完整')
    return
  }
  await userApi.updatePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
}
</script>

<style scoped>
.profile { max-width: 600px; }
h3 { margin: 0; }
</style>
