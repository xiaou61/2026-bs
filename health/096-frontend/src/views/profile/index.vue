<template>
  <div class="page-container">
    <el-row :gutter="12">
      <el-col :span="14">
        <el-card>
          <template #header>个人资料</template>
          <el-form :model="form" label-width="90px">
            <el-form-item label="用户名"><el-input :model-value="userStore.user?.username" disabled /></el-form-item>
            <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
            <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
            <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="form.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄"><el-input-number v-model="form.age" :min="0" /></el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveProfile">保存资料</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card>
          <template #header>修改密码</template>
          <el-form :model="passwordForm" label-width="90px">
            <el-form-item label="原密码"><el-input v-model="passwordForm.oldPassword" type="password" show-password /></el-form-item>
            <el-form-item label="新密码"><el-input v-model="passwordForm.newPassword" type="password" show-password /></el-form-item>
            <el-form-item>
              <el-button type="primary" @click="savePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const form = reactive({
  nickname: userStore.user?.nickname || '',
  phone: userStore.user?.phone || '',
  email: userStore.user?.email || '',
  gender: userStore.user?.gender || '',
  age: userStore.user?.age || null
})
const passwordForm = reactive({ oldPassword: '', newPassword: '' })

const saveProfile = async () => {
  await updateProfile(form)
  userStore.setUser({ ...userStore.user, ...form }, userStore.token)
  ElMessage.success('资料已更新')
}

const savePassword = async () => {
  await updatePassword(passwordForm)
  Object.assign(passwordForm, { oldPassword: '', newPassword: '' })
  ElMessage.success('密码修改成功')
}
</script>

<style scoped>
.page-container { padding: 4px; }
</style>
