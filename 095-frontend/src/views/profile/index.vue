<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="16">
        <el-card>
          <template #header>
            <span>个人资料</span>
          </template>
          <div class="profile-layout">
            <div class="profile-side">
              <div class="avatar">{{ avatarText }}</div>
              <div class="nickname">{{ profileForm.nickname || profileForm.username || '-' }}</div>
              <el-tag>{{ roleText }}</el-tag>
              <div class="meta-line">账号编号：{{ profileForm.userNo || '-' }}</div>
              <div class="meta-line">最近登录：{{ profileForm.lastLoginTime || '-' }}</div>
              <div class="meta-line">注册时间：{{ profileForm.createTime || '-' }}</div>
            </div>
            <el-form ref="profileRef" :model="profileForm" :rules="profileRules" label-width="90px" class="profile-form">
              <el-form-item label="登录账号">
                <el-input v-model="profileForm.username" disabled />
              </el-form-item>
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="profileForm.nickname" maxlength="20" show-word-limit />
              </el-form-item>
              <el-form-item label="手机号">
                <el-input v-model="profileForm.phone" maxlength="11" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="profileForm.email" maxlength="50" />
              </el-form-item>
              <el-form-item label="头像地址">
                <el-input v-model="profileForm.avatar" placeholder="可填写头像图片链接" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleProfile">保存资料</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card>
          <template #header>
            <span>安全设置</span>
          </template>
          <el-form ref="passwordRef" :model="passwordForm" :rules="passwordRules" label-width="84px">
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
              <el-button type="danger" @click="handlePassword">更新密码</el-button>
            </el-form-item>
          </el-form>

          <el-divider />

          <div class="tip-box">
            <div class="tip-title">账号提示</div>
            <p>修改密码后系统会自动退出当前登录，请使用新密码重新进入系统。</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getInfo, updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const router = useRouter()
const userStore = useUserStore()

const profileRef = ref()
const passwordRef = ref()

const profileForm = reactive({
  id: null,
  userNo: '',
  username: '',
  nickname: '',
  phone: '',
  email: '',
  avatar: '',
  role: '',
  lastLoginTime: '',
  createTime: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const roleMap = {
  ADMIN: '管理员',
  MANAGER: '俱乐部经理',
  FAN: '球迷'
}

const roleText = computed(() => roleMap[(profileForm.role || '').toUpperCase()] || '用户')
const avatarText = computed(() => (profileForm.nickname || profileForm.username || 'U').slice(0, 1).toUpperCase())

const profileRules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的新密码不一致'))
          return
        }
        callback()
      },
      trigger: 'blur'
    }
  ]
}

const loadInfo = async () => {
  const res = await getInfo()
  Object.assign(profileForm, res.data || {})
  userStore.user = res.data || null
  localStorage.setItem('user', JSON.stringify(res.data || null))
}

const handleProfile = async () => {
  await profileRef.value.validate()
  await updateProfile({
    nickname: profileForm.nickname,
    phone: profileForm.phone,
    email: profileForm.email,
    avatar: profileForm.avatar
  })
  ElMessage.success('资料已更新')
  loadInfo()
}

const handlePassword = async () => {
  await passwordRef.value.validate()
  await updatePassword({
    oldPassword: passwordForm.oldPassword,
    newPassword: passwordForm.newPassword
  })
  ElMessage.success('密码修改成功，请重新登录')
  userStore.clearLogin()
  router.push('/login')
}

onMounted(loadInfo)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.profile-layout {
  display: grid;
  grid-template-columns: 240px 1fr;
  gap: 24px;
}

.profile-side {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 28px 20px;
  background: linear-gradient(180deg, #eef5fd 0%, #f8fbff 100%);
  border-radius: 18px;
}

.avatar {
  width: 88px;
  height: 88px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: #1d4f87;
  color: #fff;
  font-size: 34px;
  font-weight: 700;
}

.nickname {
  font-size: 20px;
  font-weight: 700;
  color: #17324d;
}

.meta-line {
  width: 100%;
  color: #667085;
  font-size: 13px;
  text-align: center;
}

.profile-form {
  padding-top: 10px;
}

.tip-box {
  color: #667085;
  line-height: 1.8;
}

.tip-title {
  margin-bottom: 8px;
  font-size: 16px;
  font-weight: 700;
  color: #17324d;
}
</style>
