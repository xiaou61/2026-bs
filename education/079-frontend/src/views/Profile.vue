<template>
  <div class="profile">
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card>
          <template #header>个人信息</template>
          <el-form :model="userForm" label-width="80px">
            <el-form-item label="头像">
              <el-upload class="avatar-uploader" :show-file-list="false" :http-request="handleUpload">
                <el-avatar :size="80" :src="userForm.avatar" icon="UserFilled" />
              </el-upload>
            </el-form-item>
            <el-form-item label="用户名">{{ userStore.user?.username }}</el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="userForm.name" />
            </el-form-item>
            <el-form-item label="手机">
              <el-input v-model="userForm.phone" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="userForm.email" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>修改密码</template>
          <el-form :model="pwdForm" label-width="80px">
            <el-form-item label="原密码">
              <el-input v-model="pwdForm.oldPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="pwdForm.newPassword" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdatePwd">修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { updateUserInfo, updatePassword, uploadFile } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const userForm = reactive({ name: '', phone: '', email: '', avatar: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '' })

onMounted(() => {
  Object.assign(userForm, userStore.user)
})

const handleUpload = async ({ file }) => {
  const res = await uploadFile(file)
  userForm.avatar = res.data.url
}

const handleUpdateInfo = async () => {
  await updateUserInfo(userForm)
  userStore.setUser({ ...userStore.user, ...userForm })
  ElMessage.success('更新成功')
}

const handleUpdatePwd = async () => {
  await updatePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
}
</script>

<style scoped>
.avatar-uploader {
  cursor: pointer;
}
</style>
