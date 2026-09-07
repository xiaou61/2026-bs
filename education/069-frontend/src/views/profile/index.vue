<template>
  <div class="page-container">
    <el-row :gutter="14">
      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>个人资料</template>
          <el-form ref="profileRef" :model="profileForm" :rules="profileRules" label-width="90px">
            <el-form-item label="用户名"><el-input v-model="profileForm.username" disabled /></el-form-item>
            <el-form-item label="角色"><el-input :model-value="roleText" disabled /></el-form-item>
            <el-form-item label="姓名" prop="nickname"><el-input v-model="profileForm.nickname" maxlength="50" /></el-form-item>
            <el-form-item label="手机号"><el-input v-model="profileForm.phone" maxlength="20" /></el-form-item>
            <el-form-item label="邮箱"><el-input v-model="profileForm.email" maxlength="100" /></el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="loadingProfile" @click="submitProfile">保存资料</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>修改密码</template>
          <el-form ref="pwdRef" :model="pwdForm" :rules="pwdRules" label-width="90px">
            <el-form-item label="旧密码" prop="oldPassword"><el-input v-model="pwdForm.oldPassword" type="password" show-password maxlength="100" /></el-form-item>
            <el-form-item label="新密码" prop="newPassword"><el-input v-model="pwdForm.newPassword" type="password" show-password maxlength="100" /></el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword"><el-input v-model="pwdForm.confirmPassword" type="password" show-password maxlength="100" /></el-form-item>
            <el-form-item>
              <el-button type="warning" :loading="loadingPwd" @click="submitPassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, updatePassword, updateProfile } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const profileRef = ref()
const pwdRef = ref()
const loadingProfile = ref(false)
const loadingPwd = ref(false)

const profileForm = reactive({ username: '', role: '', nickname: '', phone: '', email: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })

const roleText = computed(() => {
  if (profileForm.role === 'ADMIN') return '管理员'
  if (profileForm.role === 'TEACHER') return '教师'
  return '学生'
})

const profileRules = {
  nickname: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请再次输入新密码', trigger: 'blur' }]
}

const loadInfo = async () => {
  const res = await getUserInfo()
  const user = res.data || {}
  Object.assign(profileForm, {
    username: user.username || '',
    role: user.role || '',
    nickname: user.nickname || '',
    phone: user.phone || '',
    email: user.email || ''
  })
  userStore.refreshUser(user)
}

const submitProfile = async () => {
  await profileRef.value.validate()
  loadingProfile.value = true
  try {
    await updateProfile({ nickname: profileForm.nickname, phone: profileForm.phone, email: profileForm.email })
    ElMessage.success('资料已更新')
    await loadInfo()
  } finally {
    loadingProfile.value = false
  }
}

const submitPassword = async () => {
  await pwdRef.value.validate()
  if (pwdForm.newPassword !== pwdForm.confirmPassword) {
    ElMessage.error('两次输入的新密码不一致')
    return
  }
  loadingPwd.value = true
  try {
    await updatePassword({ oldPassword: pwdForm.oldPassword, newPassword: pwdForm.newPassword })
    ElMessage.success('密码修改成功，请重新登录')
    Object.assign(pwdForm, { oldPassword: '', newPassword: '', confirmPassword: '' })
  } finally {
    loadingPwd.value = false
  }
}

onMounted(loadInfo)
</script>

<style scoped>
.page-container {
  padding: 10px;
}
</style>
