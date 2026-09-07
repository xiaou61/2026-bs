<template>
  <el-card>
    <template #header><span>个人中心</span></template>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="用户名">{{ user?.username }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{ user?.name }}</el-descriptions-item>
      <el-descriptions-item label="角色">{{ { admin: '管理员', hr: 'HR', employee: '员工' }[user?.role] }}</el-descriptions-item>
      <el-descriptions-item label="状态"><el-tag :type="user?.status === 1 ? 'success' : 'danger'">{{ user?.status === 1 ? '启用' : '禁用' }}</el-tag></el-descriptions-item>
    </el-descriptions>
    <el-divider />
    <h4>修改密码</h4>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="100px" style="max-width: 400px; margin-top: 20px">
      <el-form-item label="原密码" prop="oldPassword"><el-input v-model="form.oldPassword" type="password" show-password /></el-form-item>
      <el-form-item label="新密码" prop="newPassword"><el-input v-model="form.newPassword" type="password" show-password /></el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword"><el-input v-model="form.confirmPassword" type="password" show-password /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">修改密码</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const user = computed(() => userStore.user)
const formRef = ref()
const form = ref({ oldPassword: '', newPassword: '', confirmPassword: '' })

const validateConfirm = (rule, value, callback) => {
  if (value !== form.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认密码', trigger: 'blur' }, { validator: validateConfirm, trigger: 'blur' }]
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await userApi.updatePassword({ oldPassword: form.value.oldPassword, newPassword: form.value.newPassword })
  ElMessage.success('修改成功')
  form.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
}
</script>
