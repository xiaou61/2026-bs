<template>
  <div class="profile-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">个人中心</span>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-descriptions title="基本信息" :column="2" border>
        <el-descriptions-item label="学号">{{ userStore.userInfo?.studentId }}</el-descriptions-item>
        <el-descriptions-item label="用户名">{{ userStore.userInfo?.username }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ userStore.userInfo?.realName }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userStore.userInfo?.phone }}</el-descriptions-item>
        <el-descriptions-item label="角色">
          <el-tag>{{ getRoleName(userStore.userInfo?.role) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="userStore.userInfo?.status === 1 ? 'success' : 'danger'">
            {{ userStore.userInfo?.status === 1 ? '正常' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="注册时间" :span="2">
          {{ userStore.userInfo?.createTime }}
        </el-descriptions-item>
      </el-descriptions>

      <el-divider />

      <el-button type="primary" @click="showPasswordDialog = true">修改密码</el-button>
    </el-card>

    <el-dialog v-model="showPasswordDialog" title="修改密码" width="400px">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="handleChangePassword" :loading="loading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { updatePassword } from '@/api/user'

const userStore = useUserStore()
const loading = ref(false)
const showPasswordDialog = ref(false)
const passwordFormRef = ref()

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const getRoleName = (role) => {
  const map = {
    'ADMIN': '系统管理员',
    'STATION_ADMIN': '代收点管理员',
    'COURIER': '快递员',
    'STUDENT': '学生'
  }
  return map[role] || role
}

const handleChangePassword = async () => {
  await passwordFormRef.value.validate()
  loading.value = true
  try {
    await updatePassword(userStore.userInfo.id, {
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    ElMessage.success('密码修改成功，请重新登录')
    showPasswordDialog.value = false
    userStore.logout()
    setTimeout(() => {
      window.location.href = '/login'
    }, 1000)
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}
</style>

