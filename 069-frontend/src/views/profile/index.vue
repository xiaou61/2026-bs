<template>
  <el-row :gutter="12">
    <el-col :xs="24" :md="14">
      <el-card>
        <template #header>个人资料</template>
        <el-form ref="profileRef" :model="profile" :rules="profileRules" label-width="90px">
          <el-form-item label="昵称" prop="nickname"><el-input v-model="profile.nickname" maxlength="50" /></el-form-item>
          <el-form-item label="手机号"><el-input v-model="profile.phone" maxlength="20" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="profile.email" maxlength="100" /></el-form-item>
          <el-form-item label="头像地址"><el-input v-model="profile.avatar" maxlength="255" /></el-form-item>
          <el-form-item label="个人简介"><el-input v-model="profile.profile" type="textarea" :rows="4" maxlength="500" /></el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveProfile">保存资料</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>

    <el-col :xs="24" :md="10">
      <el-card>
        <template #header>修改密码</template>
        <el-form ref="pwdRef" :model="pwdForm" :rules="pwdRules" label-width="90px">
          <el-form-item label="原密码" prop="oldPassword"><el-input v-model="pwdForm.oldPassword" type="password" show-password /></el-form-item>
          <el-form-item label="新密码" prop="newPassword"><el-input v-model="pwdForm.newPassword" type="password" show-password /></el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword"><el-input v-model="pwdForm.confirmPassword" type="password" show-password /></el-form-item>
          <el-form-item>
            <el-button type="warning" @click="savePassword">更新密码</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const profileRef = ref()
const pwdRef = ref()

const profile = reactive({
  nickname: userStore.user?.nickname || '',
  phone: userStore.user?.phone || '',
  email: userStore.user?.email || '',
  avatar: userStore.user?.avatar || '',
  profile: userStore.user?.profile || ''
})

const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })

const profileRules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认新密码', trigger: 'blur' }]
}

const saveProfile = async () => {
  await profileRef.value.validate()
  await updateProfile(profile)
  userStore.refreshUser({ ...userStore.user, ...profile })
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
  userStore.logout()
  location.href = '/login'
}
</script>
