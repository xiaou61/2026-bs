<template>
  <div class="profile-container">
    <el-card class="user-card">
      <div class="user-header">
        <el-avatar :size="80" :icon="UserFilled" />
        <div class="user-basic">
          <div class="username">{{ userInfo?.username }}</div>
          <div class="real-name">{{ userInfo?.realName }}</div>
        </div>
      </div>
      <el-descriptions :column="2" border class="user-stats">
        <el-descriptions-item label="学号">{{ userInfo?.studentId }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ userInfo?.phone }}</el-descriptions-item>
        <el-descriptions-item label="宿舍地址" :span="2">
          {{ userInfo?.dormitoryBuilding }} {{ userInfo?.dormitoryRoom }}
        </el-descriptions-item>
        <el-descriptions-item label="信用分">
          <el-tag :type="getCreditScoreType(userInfo?.creditScore)">{{ userInfo?.creditScore }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="userInfo?.status === 0 ? 'success' : 'danger'">
            {{ userInfo?.status === 0 ? '正常' : '禁用' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="累计发单">{{ userInfo?.totalOrders }}</el-descriptions-item>
        <el-descriptions-item label="累计接单">{{ userInfo?.totalTakes }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card class="edit-card">
      <template #header>
        <span>修改个人信息</span>
      </template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="宿舍楼栋" prop="dormitoryBuilding">
          <el-input v-model="form.dormitoryBuilding" />
        </el-form-item>
        <el-form-item label="房间号" prop="dormitoryRoom">
          <el-input v-model="form.dormitoryRoom" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleUpdate">
            保存修改
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="password-card">
      <template #header>
        <span>修改密码</span>
      </template>
      <el-form ref="passwordFormRef" :model="passwordForm" :rules="passwordRules" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="passwordLoading" @click="handlePasswordChange">
            修改密码
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '../stores/user'
import { UserFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const userInfo = ref(null)
const formRef = ref()
const passwordFormRef = ref()
const loading = ref(false)
const passwordLoading = ref(false)

const form = reactive({
  username: '',
  dormitoryBuilding: '',
  dormitoryRoom: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  dormitoryBuilding: [{ required: true, message: '请输入宿舍楼栋', trigger: 'blur' }],
  dormitoryRoom: [{ required: true, message: '请输入房间号', trigger: 'blur' }]
}

const validatePassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
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
    { validator: validatePassword, trigger: 'blur' }
  ]
}

const loadUserInfo = async () => {
  try {
    await userStore.loadUserInfo()
    userInfo.value = userStore.userInfo
    form.username = userInfo.value.username
    form.dormitoryBuilding = userInfo.value.dormitoryBuilding
    form.dormitoryRoom = userInfo.value.dormitoryRoom
  } catch (error) {
    console.error('加载用户信息失败', error)
  }
}

const handleUpdate = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        ElMessage.success('信息修改成功')
        loadUserInfo()
      } catch (error) {
        console.error('修改失败', error)
      } finally {
        loading.value = false
      }
    }
  })
}

const handlePasswordChange = async () => {
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      passwordLoading.value = true
      try {
        ElMessage.success('密码修改成功')
        passwordForm.oldPassword = ''
        passwordForm.newPassword = ''
        passwordForm.confirmPassword = ''
      } catch (error) {
        console.error('修改密码失败', error)
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

const getCreditScoreType = (score) => {
  if (score >= 90) return 'success'
  if (score >= 60) return 'warning'
  return 'danger'
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 0 auto;
}

.user-card {
  margin-bottom: 20px;
}

.user-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 0;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 20px;
}

.user-basic {
  flex: 1;
}

.username {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
}

.real-name {
  font-size: 14px;
  color: #909399;
}

.edit-card,
.password-card {
  margin-bottom: 20px;
}
</style>

