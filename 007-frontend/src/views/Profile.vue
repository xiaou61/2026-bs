<template>
  <div class="profile-container">
    <el-card>
      <template #header>
        <h3>个人信息</h3>
      </template>
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="角色">
          <el-tag>{{ form.role === 'ADMIN' ? '管理员' : '志愿者' }}</el-tag>
        </el-form-item>
        <el-form-item v-if="form.role === 'VOLUNTEER'" label="总积分">
          <span style="color: #409eff; font-weight: bold">{{ form.totalPoints }}</span>
        </el-form-item>
        <el-form-item v-if="form.role === 'VOLUNTEER'" label="可用积分">
          <span style="color: #67c23a; font-weight: bold">{{ form.availablePoints }}</span>
        </el-form-item>
        <el-form-item v-if="form.role === 'VOLUNTEER'" label="志愿时长">
          <span style="color: #e6a23c; font-weight: bold">{{ form.volunteerHours }} 小时</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleUpdate">保存</el-button>
          <el-button @click="showPasswordDialog = true">修改密码</el-button>
        </el-form-item>
      </el-form>
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
        <el-button type="primary" @click="handlePasswordUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo } from '@/api/auth'
import { updateUser, updatePassword } from '@/api/user'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const passwordFormRef = ref()
const showPasswordDialog = ref(false)

const form = reactive({
  id: null,
  username: '',
  name: '',
  phone: '',
  role: '',
  totalPoints: 0,
  availablePoints: 0,
  volunteerHours: 0
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const loadUserInfo = async () => {
  const res = await getUserInfo()
  Object.assign(form, res.data)
}

const handleUpdate = async () => {
  try {
    await updateUser(form.id, {
      name: form.name,
      phone: form.phone
    })
    ElMessage.success('更新成功')
    userStore.setUserInfo(form)
  } catch (error) {
    console.error(error)
  }
}

const handlePasswordUpdate = async () => {
  await passwordFormRef.value.validate()
  try {
    await updatePassword(form.id, passwordForm)
    ElMessage.success('密码修改成功')
    showPasswordDialog.value = false
    Object.assign(passwordForm, {
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    })
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.profile-container {
  max-width: 600px;
}
</style>

