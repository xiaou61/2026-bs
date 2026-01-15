<template>
  <div class="profile-page">
    <el-card>
      <template #header><span class="card-title">个人信息</span></template>
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="头像">
          <el-upload action="#" :auto-upload="false" :show-file-list="false" :on-change="handleAvatarChange">
            <el-avatar :size="80" :src="form.avatar">{{ form.nickname?.charAt(0) }}</el-avatar>
          </el-upload>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio :value="0">保密</el-radio>
            <el-radio :value="1">男</el-radio>
            <el-radio :value="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="form.introduction" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave" :loading="loading">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { userApi, fileApi } from '@/api'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const loading = ref(false)

const form = reactive({
  username: '',
  nickname: '',
  avatar: '',
  gender: 0,
  email: '',
  phone: '',
  introduction: ''
})

onMounted(() => {
  Object.assign(form, userStore.user)
})

const handleAvatarChange = async (file) => {
  const res = await fileApi.uploadImage(file.raw)
  form.avatar = res.data
}

const handleSave = async () => {
  loading.value = true
  try {
    await userApi.updateInfo(form)
    await userStore.fetchUserInfo()
    ElMessage.success('保存成功')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.profile-page {
  max-width: 600px;
  margin: 0 auto;
}
</style>
