<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="12">
        <el-card>
          <template #header>个人资料</template>
          <el-form :model="profileForm" label-width="90px">
            <el-form-item label="账号">
              <el-input v-model="profileForm.username" disabled />
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="profileForm.nickname" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profileForm.phone" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="profileForm.email" />
            </el-form-item>
            <el-form-item label="头像地址">
              <el-input v-model="profileForm.avatar" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleProfile">保存资料</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>修改密码</template>
          <el-form :model="passwordForm" label-width="90px">
            <el-form-item label="原密码">
              <el-input v-model="passwordForm.oldPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="passwordForm.newPassword" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handlePassword">修改密码</el-button>
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
import { getInfo, updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const profileForm = reactive({
  username: '',
  nickname: '',
  phone: '',
  email: '',
  avatar: ''
})
const passwordForm = reactive({
  oldPassword: '',
  newPassword: ''
})

const loadInfo = async () => {
  const res = await getInfo()
  Object.assign(profileForm, res.data || {})
  userStore.user = res.data || null
  localStorage.setItem('user', JSON.stringify(res.data || null))
}

const handleProfile = async () => {
  await updateProfile(profileForm)
  ElMessage.success('资料保存成功')
  loadInfo()
}

const handlePassword = async () => {
  await updatePassword(passwordForm)
  ElMessage.success('密码修改成功，请重新登录')
  passwordForm.oldPassword = ''
  passwordForm.newPassword = ''
}

onMounted(loadInfo)
</script>

<style scoped>
.page-container {
  padding: 8px;
}
</style>
