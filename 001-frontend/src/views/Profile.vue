<template>
  <div class="profile-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title">
        <el-icon><User /></el-icon>
        <span>个人中心</span>
      </div>
    </div>

    <el-row :gutter="24">
      <!-- 个人信息卡片 -->
      <el-col :xs="24" :lg="8">
        <el-card class="profile-card">
          <div class="user-info">
            <div class="avatar-container">
              <el-avatar :size="100" class="avatar">
                {{ userInfo?.username?.charAt(0) }}
              </el-avatar>
              <div class="avatar-badge">
                <el-icon><Camera /></el-icon>
              </div>
            </div>
            <h3 class="username">{{ userInfo?.username }}</h3>
            <el-tag :type="getRoleType(userInfo?.role)" size="large" class="role-tag">
              {{ getRoleName(userInfo?.role) }}
            </el-tag>
            <el-divider />
            <el-descriptions :column="1" class="info-descriptions">
              <el-descriptions-item label="真实姓名">
                <el-icon><User /></el-icon>
                {{ userInfo?.realName }}
              </el-descriptions-item>
              <el-descriptions-item label="手机号">
                <el-icon><Phone /></el-icon>
                {{ userInfo?.phone }}
              </el-descriptions-item>
              <el-descriptions-item label="邮箱">
                <el-icon><Message /></el-icon>
                {{ userInfo?.email }}
              </el-descriptions-item>
              <el-descriptions-item label="注册时间">
                <el-icon><Calendar /></el-icon>
                {{ userInfo?.createTime }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>

      <!-- 编辑信息 -->
      <el-col :xs="24" :lg="16">
        <el-card class="edit-card">
          <template #header>
            <div class="card-header">
              <el-tabs v-model="activeTab" class="custom-tabs">
                <el-tab-pane label="修改信息" name="info" />
                <el-tab-pane label="修改密码" name="password" />
              </el-tabs>
            </div>
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
              <el-input v-model="infoForm.realName" placeholder="请输入真实姓名" class="custom-input" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="infoForm.phone" placeholder="请输入手机号" class="custom-input" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="infoForm.email" placeholder="请输入邮箱" class="custom-input" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateInfo" :loading="submitting" class="save-btn">
                <el-icon><Check /></el-icon>
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
                class="custom-input"
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input
                v-model="passwordForm.newPassword"
                type="password"
                placeholder="请输入新密码"
                show-password
                class="custom-input"
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="passwordForm.confirmPassword"
                type="password"
                placeholder="请再次输入新密码"
                show-password
                class="custom-input"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleChangePassword" :loading="submitting" class="save-btn">
                <el-icon><Key /></el-icon>
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

/* 页面标题 */
.page-header {
  margin-bottom: 24px;
  padding: 0 4px;
}

.page-title {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 24px;
  font-weight: 700;
  color: #2C3E50;
  font-family: var(--font-heading);
}

.page-title .el-icon {
  color: #2E7D32;
  font-size: 28px;
}

/* 个人信息卡片 */
.profile-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08) !important;
  margin-bottom: 24px;
}

.user-info {
  text-align: center;
  padding: 20px 0;
}

.avatar-container {
  position: relative;
  display: inline-block;
  margin-bottom: 20px;
}

.avatar {
  background: linear-gradient(135deg, #2E7D32 0%, #1565C0 100%);
  color: white;
  font-size: 36px;
  font-weight: 700;
  box-shadow: 0 8px 24px rgba(46, 125, 50, 0.3);
}

.avatar-badge {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #FF6F00 0%, #FFA726 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(255, 111, 0, 0.3);
}

.avatar-badge:hover {
  transform: scale(1.1);
}

.username {
  font-size: 24px;
  font-weight: 700;
  color: #2C3E50;
  margin-bottom: 12px;
  font-family: var(--font-heading);
}

.role-tag {
  border-radius: 20px !important;
  padding: 0 20px !important;
  font-weight: 600;
  font-size: 14px;
}

.info-descriptions {
  margin-top: 20px;
}

.info-descriptions :deep(.el-descriptions__label) {
  font-weight: 500;
  color: #606266;
}

.info-descriptions :deep(.el-descriptions__content) {
  color: #2C3E50;
}

.info-descriptions :deep(.el-descriptions-item) {
  padding: 12px 0;
}

.info-descriptions :deep(.el-descriptions-item .el-icon) {
  color: #2E7D32;
  margin-right: 8px;
}

/* 编辑信息卡片 */
.edit-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08) !important;
}

.edit-card :deep(.el-card__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 16px 24px;
}

.card-header {
  display: flex;
  align-items: center;
}

/* 自定义标签页 */
.custom-tabs :deep(.el-tabs__header) {
  margin-bottom: 0;
}

.custom-tabs :deep(.el-tabs__nav-wrap::after) {
  height: 1px;
  background: #f0f0f0;
}

.custom-tabs :deep(.el-tabs__active-bar) {
  background: linear-gradient(90deg, #2E7D32, #1565C0);
  height: 3px;
  border-radius: 2px;
}

.custom-tabs :deep(.el-tabs__item) {
  font-weight: 500;
  color: #606266;
}

.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #2E7D32;
  font-weight: 600;
}

/* 个人表单 */
.profile-form {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px 0;
}

.profile-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #2C3E50;
}

.custom-input :deep(.el-input__wrapper) {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #2E7D32 inset;
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #2E7D32 inset;
}

/* 保存按钮 */
.save-btn {
  height: 44px;
  padding: 0 24px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, #2E7D32 0%, #4CAF50 100%) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
  transition: all 0.3s ease;
}

.save-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(46, 125, 50, 0.4);
}

.save-btn:active {
  transform: translateY(0);
}

/* 响应式 */
@media (max-width: 1200px) {
  .profile-card {
    margin-bottom: 24px;
  }
}

@media (max-width: 768px) {
  .page-header {
    margin-bottom: 16px;
  }
  
  .profile-form {
    padding: 10px 0;
  }
  
  .avatar {
    width: 80px !important;
    height: 80px !important;
    font-size: 28px;
  }
}
</style>

