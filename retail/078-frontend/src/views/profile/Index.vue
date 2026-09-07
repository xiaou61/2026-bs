<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-info">
            <el-avatar :size="80">{{ user?.nickname?.charAt(0) || user?.username?.charAt(0) }}</el-avatar>
            <h3>{{ user?.nickname || user?.username }}</h3>
            <p>{{ roleMap[user?.role] }}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>基本信息</template>
          <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="form.username" disabled />
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item label="头像">
              <el-input v-model="form.avatar" placeholder="头像URL" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSave">保存修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 20px">
          <template #header>修改密码</template>
          <el-form :model="pwdForm" :rules="pwdRules" ref="pwdFormRef" label-width="100px">
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="pwdForm.oldPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="pwdForm.newPassword" type="password" show-password />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="pwdForm.confirmPassword" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, updateUserInfo, changePassword } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const user = computed(() => userStore.user)
const formRef = ref()
const pwdFormRef = ref()
const form = reactive({ username: '', nickname: '', phone: '', email: '', avatar: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })
const roleMap = { 0: '管理员', 1: '商家', 2: '普通用户' }

const rules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '密码至少6位', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: (rule, value, callback) => value === pwdForm.newPassword ? callback() : callback(new Error('两次密码不一致')), trigger: 'blur' }
  ]
}

onMounted(async () => {
  const res = await getUserInfo()
  Object.assign(form, res.data)
})

const handleSave = async () => {
  await formRef.value.validate()
  await updateUserInfo(form)
  userStore.setUser({ ...user.value, ...form })
  ElMessage.success('保存成功')
}

const handleChangePassword = async () => {
  await pwdFormRef.value.validate()
  await changePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
  pwdForm.confirmPassword = ''
}
</script>

<style scoped>
.page-container { padding: 20px; }
.user-info { text-align: center; padding: 30px 0; }
.user-info h3 { margin: 15px 0 5px; }
.user-info p { color: #999; }
</style>
