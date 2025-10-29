<template>
  <el-card>
    <template #header>
      <h3>个人信息</h3>
    </template>
    <el-form :model="form" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled />
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input v-model="form.realName" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateUser } from '@/api/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()

const form = ref({
  username: '',
  realName: '',
  email: '',
  phone: ''
})

const loadData = async () => {
  try {
    const res = await getUserInfo()
    form.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleUpdate = async () => {
  try {
    await updateUser(form.value)
    userStore.setUser(form.value)
    ElMessage.success('更新成功')
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadData()
})
</script>

