<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="14">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>个人信息</span>
            </div>
          </template>

          <el-form :model="profileForm" label-width="90px">
            <el-form-item label="学号">
              <el-input :model-value="userStore.userInfo?.username" disabled />
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="profileForm.realName" />
            </el-form-item>
            <el-form-item label="院系">
              <el-input v-model="profileForm.department" />
            </el-form-item>
            <el-form-item label="年级">
              <el-input v-model="profileForm.grade" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profileForm.phone" />
            </el-form-item>
            <el-form-item label="信用分">
              <el-tag type="success">{{ userStore.creditScore }}</el-tag>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="saving" @click="handleSaveProfile">保存资料</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>

      <el-col :span="10">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>修改密码</span>
            </div>
          </template>

          <el-form :model="passwordForm" label-width="90px">
            <el-form-item label="原密码">
              <el-input v-model="passwordForm.oldPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="passwordForm.newPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="warning" :loading="updatingPassword" @click="handleUpdatePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, watch, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { updatePassword, updateUserInfo } from '@/api/user'

const userStore = useUserStore()
const saving = ref(false)
const updatingPassword = ref(false)

const profileForm = reactive({
  realName: '',
  department: '',
  grade: '',
  phone: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

watch(
  () => userStore.userInfo,
  (user) => {
    profileForm.realName = user?.realName || ''
    profileForm.department = user?.department || ''
    profileForm.grade = user?.grade || ''
    profileForm.phone = user?.phone || ''
  },
  { immediate: true }
)

const handleSaveProfile = async () => {
  saving.value = true
  try {
    await updateUserInfo({ ...profileForm })
    await userStore.fetchUserInfo()
    ElMessage.success('资料已更新')
  } catch (error) {
    ElMessage.error(error.message || '资料更新失败')
  } finally {
    saving.value = false
  }
}

const handleUpdatePassword = async () => {
  if (!passwordForm.oldPassword || !passwordForm.newPassword) {
    ElMessage.warning('请填写完整密码信息')
    return
  }
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    ElMessage.error('两次输入的新密码不一致')
    return
  }

  updatingPassword.value = true
  try {
    await updatePassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    passwordForm.oldPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''
    ElMessage.success('密码修改成功')
  } catch (error) {
    ElMessage.error(error.message || '密码修改失败')
  } finally {
    updatingPassword.value = false
  }
}
</script>

<style lang="scss" scoped>
.profile {
  .card-header {
    font-weight: 600;
  }
}
</style>
