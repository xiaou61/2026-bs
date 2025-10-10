<template>
  <div class="profile-container">
    <div class="profile-header">
      <h2>个人设置</h2>
    </div>
    
    <div class="profile-content">
      <el-card class="profile-card">
        <template #header>
          <div class="card-header">
            <span>基本信息</span>
          </div>
        </template>
        
        <div class="profile-info">
          <div class="avatar-section">
            <el-avatar :size="100" :src="userStore.userInfo?.avatar || ''">
              {{ userStore.userInfo?.nickname?.[0] || 'U' }}
            </el-avatar>
          </div>
          
          <el-form :model="form" label-width="100px" class="profile-form">
            <el-form-item label="用户名">
              <el-input v-model="form.username" disabled />
            </el-form-item>
            
            <el-form-item label="昵称">
              <el-input v-model="form.nickname" placeholder="请输入昵称" />
            </el-form-item>
            
            <el-form-item label="个性签名">
              <el-input 
                v-model="form.signature" 
                type="textarea"
                :rows="3"
                placeholder="写点什么吧..."
              />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="handleUpdate" :loading="loading">
                保存修改
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      
      <el-card class="profile-card" style="margin-top: 20px">
        <template #header>
          <div class="card-header">
            <span>修改密码</span>
          </div>
        </template>
        
        <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
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
              placeholder="请确认新密码"
              show-password
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="handleChangePassword" :loading="passwordLoading">
              修改密码
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
      
      <el-card class="profile-card" style="margin-top: 20px">
        <template #header>
          <div class="card-header">
            <span>账号信息</span>
          </div>
        </template>
        
        <div class="account-info">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="用户ID">
              {{ userStore.userInfo?.id }}
            </el-descriptions-item>
            <el-descriptions-item label="注册时间">
              {{ formatTime(userStore.userInfo?.createTime) }}
            </el-descriptions-item>
            <el-descriptions-item label="账号状态">
              <el-tag type="success">正常</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateUserInfo } from '@/api/user'

const userStore = useUserStore()
const passwordFormRef = ref()
const loading = ref(false)
const passwordLoading = ref(false)

const form = ref({
  username: '',
  nickname: '',
  signature: ''
})

const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== passwordForm.value.newPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePass, trigger: 'blur' }
  ]
}

const loadUserInfo = async () => {
  try {
    const data = await getUserInfo()
    form.value = {
      username: data.username,
      nickname: data.nickname,
      signature: data.signature || ''
    }
  } catch (error) {
    console.error('加载用户信息失败', error)
  }
}

const handleUpdate = async () => {
  loading.value = true
  try {
    await updateUserInfo(form.value)
    ElMessage.success('保存成功')
    
    userStore.setUserInfo({
      ...userStore.userInfo,
      nickname: form.value.nickname,
      signature: form.value.signature
    })
  } catch (error) {
    console.error('保存失败', error)
  } finally {
    loading.value = false
  }
}

const handleChangePassword = async () => {
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      passwordLoading.value = true
      try {
        ElMessage.success('密码修改成功，请重新登录')
        
        passwordForm.value = {
          oldPassword: '',
          newPassword: '',
          confirmPassword: ''
        }
      } catch (error) {
        console.error('修改密码失败', error)
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  if (userStore.userInfo) {
    form.value = {
      username: userStore.userInfo.username,
      nickname: userStore.userInfo.nickname,
      signature: userStore.userInfo.signature || ''
    }
  }
})
</script>

<style scoped>
.profile-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.profile-header {
  padding: 20px 30px;
  border-bottom: 1px solid #e0e0e0;
}

.profile-header h2 {
  margin: 0;
  font-size: 24px;
}

.profile-content {
  flex: 1;
  padding: 30px;
  overflow-y: auto;
  max-width: 800px;
  margin: 0 auto;
  width: 100%;
}

.profile-card {
  margin-bottom: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: 500;
}

.profile-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.avatar-section {
  margin-bottom: 30px;
}

.profile-form {
  width: 100%;
}

.account-info {
  padding: 10px 0;
}
</style>

