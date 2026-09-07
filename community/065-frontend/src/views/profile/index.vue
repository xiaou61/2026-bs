<template>
  <el-row :gutter="12">
    <el-col :span="12">
      <el-card>
        <template #header>个人资料</template>
        <el-form ref="profileRef" :model="profileForm" :rules="profileRules" label-width="90px">
          <el-form-item label="用户名"><el-input v-model="profileForm.username" disabled /></el-form-item>
          <el-form-item label="昵称" prop="nickname"><el-input v-model="profileForm.nickname" maxlength="50" /></el-form-item>
          <el-form-item label="手机号"><el-input v-model="profileForm.phone" maxlength="20" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="profileForm.email" maxlength="100" /></el-form-item>
          <el-form-item><el-button type="primary" @click="saveProfile">保存资料</el-button></el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <template #header>修改密码</template>
        <el-form ref="pwdRef" :model="pwdForm" :rules="pwdRules" label-width="90px">
          <el-form-item label="原密码" prop="oldPassword"><el-input v-model="pwdForm.oldPassword" type="password" show-password maxlength="100" /></el-form-item>
          <el-form-item label="新密码" prop="newPassword"><el-input v-model="pwdForm.newPassword" type="password" show-password maxlength="100" /></el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword"><el-input v-model="pwdForm.confirmPassword" type="password" show-password maxlength="100" /></el-form-item>
          <el-form-item><el-button type="warning" @click="savePassword">更新密码</el-button></el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, logout, updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const profileRef = ref()
const pwdRef = ref()

const profileForm = reactive({ id: null, username: '', nickname: '', phone: '', email: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })

const profileRules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认新密码', trigger: 'blur' }]
}

const loadUser = async () => {
  const res = await getUserInfo()
  Object.assign(profileForm, res.data)
}

const saveProfile = async () => {
  await profileRef.value.validate()
  await updateProfile({ nickname: profileForm.nickname, phone: profileForm.phone, email: profileForm.email })
  const infoRes = await getUserInfo()
  userStore.setUser(infoRes.data, localStorage.getItem('token'))
  ElMessage.success('资料已更新')
}

const savePassword = async () => {
  await pwdRef.value.validate()
  if (pwdForm.newPassword !== pwdForm.confirmPassword) {
    ElMessage.warning('两次输入的新密码不一致')
    return
  }
  await updatePassword({ oldPassword: pwdForm.oldPassword, newPassword: pwdForm.newPassword })
  ElMessage.success('密码修改成功，请重新登录')
  try {
    await logout()
  } catch (e) {
  }
  userStore.logout()
  location.href = '/login'
}

onMounted(loadUser)
</script>
