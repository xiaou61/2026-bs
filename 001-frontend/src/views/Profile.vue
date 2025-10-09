<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <!-- 个人信息卡片 -->
      <el-col :span="8">
        <el-card>
          <template #header>
            <span>个人信息</span>
          </template>
          <div class="user-info">
            <el-avatar :size="80" class="avatar">
              {{ userInfo?.username?.charAt(0) }}
            </el-avatar>
            <h3 class="username">{{ userInfo?.username }}</h3>
            <el-tag :type="getRoleType(userInfo?.role)" size="large">
              {{ getRoleName(userInfo?.role) }}
            </el-tag>
            <el-descriptions :column="1" border style="margin-top: 20px;">
              <el-descriptions-item label="真实姓名">
                {{ userInfo?.realName }}
              </el-descriptions-item>
              <el-descriptions-item label="手机号">
                {{ userInfo?.phone }}
              </el-descriptions-item>
              <el-descriptions-item label="邮箱">
                {{ userInfo?.email }}
              </el-descriptions-item>
              <el-descriptions-item label="注册时间">
                {{ userInfo?.createTime }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>

      <!-- 编辑信息 -->
      <el-col :span="16">
        <el-card>
          <template #header>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="修改信息" name="info" />
              <el-tab-pane label="修改密码" name="password" />
            </el-tabs>
          </template>

          <!-- 修改信息表单 -->
          <el-form
            v-if="activeTab === 'info'"
            ref="infoFormRef"
            :model="infoForm"
            :rules="infoRules"
            label-width="100px"
            class="profile-form"
          >
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="infoForm.realName" placeholder="请输入真实姓名" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="infoForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="infoForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateInfo" :loading="submitting">
                保存修改
              </el-button>
            </el-form-item>
          </el-form>

          <!-- 修改密码表单 -->
          <el-form
            v-if="activeTab === 'password'"
            ref="passwordFormRef"
            :model="passwordForm"
            :rules="passwordRules"
            label-width="100px"
            class="profile-form"
          >
            <el-form-item label="原密码" prop="oldPassword">
              <el-input
                v-model="passwordForm.oldPassword"
                type="password"
                placeholder="请输入原密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input
                v-model="passwordForm.newPassword"
                type="password"
                placeholder="请输入新密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="passwordForm.confirmPassword"
                type="password"
                placeholder="请再次输入新密码"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleChangePassword" :loading="submitting">
                修改密码
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { updateUser } from '@/api/user'
import { changePassword } from '@/api/auth'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const activeTab = ref('info')
const submitting = ref(false)
const infoFormRef = ref(null)
const passwordFormRef = ref(null)

const infoForm = reactive({
  realName: '',
  phone: '',
  email: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const infoRules = {
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

// 获取角色类型
const getRoleType = (role) => {
  if (role === 'admin') return 'danger'
  if (role === 'teacher') return 'warning'
  return 'primary'
}

// 获取角色名称
const getRoleName = (role) => {
  if (role === 'admin') return '管理员'
  if (role === 'teacher') return '教师'
  return '学生'
}

// 更新个人信息
const handleUpdateInfo = async () => {
  if (!infoFormRef.value) return
  
  await infoFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await updateUser({
          id: userInfo.value.id,
          ...infoForm
        })
        
        if (res.code === 200) {
          ElMessage.success('更新成功')
          // 重新获取用户信息
          await userStore.fetchUserInfo()
        }
      } catch (error) {
        console.error('更新失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 修改密码
const handleChangePassword = async () => {
  if (!passwordFormRef.value) return
  
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true
      try {
        const res = await changePassword({
          oldPassword: passwordForm.oldPassword,
          newPassword: passwordForm.newPassword
        })
        
        if (res.code === 200) {
          ElMessage.success('密码修改成功，请重新登录')
          // 重置表单
          passwordFormRef.value.resetFields()
          // 退出登录
          setTimeout(() => {
            userStore.logout()
          }, 1500)
        }
      } catch (error) {
        console.error('修改密码失败:', error)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 初始化表单数据
const initFormData = () => {
  if (userInfo.value) {
    infoForm.realName = userInfo.value.realName
    infoForm.phone = userInfo.value.phone
    infoForm.email = userInfo.value.email
  }
}

onMounted(() => {
  initFormData()
})
</script>

<style scoped>
.profile-container {
  padding: 0;
}

.user-info {
  text-align: center;
}

.avatar {
  margin-bottom: 20px;
}

.username {
  font-size: 20px;
  margin-bottom: 10px;
  color: #303133;
}

.profile-form {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px 0;
}
</style>

