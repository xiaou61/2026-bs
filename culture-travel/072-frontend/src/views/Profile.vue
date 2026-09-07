<template>
  <div class="profile">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-info">
            <el-avatar :size="80" :src="userStore.user?.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <h2>{{ userStore.user?.nickname || userStore.user?.username }}</h2>
            <p class="role"><el-tag>{{ userStore.user?.role === 'admin' ? '管理员' : '普通用户' }}</el-tag></p>
          </div>
          <el-divider />
          <div class="wallet">
            <p class="label">我的钱包</p>
            <p class="balance">¥{{ userStore.user?.balance || 0 }}</p>
            <el-button type="primary" @click="rechargeDialog = true">充值</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header><span>个人信息</span></template>
          <el-form :model="form" label-width="100px" :rules="rules" ref="formRef">
            <el-form-item label="用户名">{{ userStore.user?.username }}</el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入昵称" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="头像" prop="avatar">
              <el-input v-model="form.avatar" placeholder="请输入头像URL" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="updateProfile">保存修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top:20px">
          <template #header><span>修改密码</span></template>
          <el-form :model="pwdForm" label-width="100px" :rules="pwdRules" ref="pwdFormRef">
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="pwdForm.oldPassword" type="password" show-password placeholder="请输入原密码" />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="pwdForm.newPassword" type="password" show-password placeholder="请输入新密码" />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="pwdForm.confirmPassword" type="password" show-password placeholder="请再次输入新密码" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="changePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog v-model="rechargeDialog" title="钱包充值" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number v-model="rechargeAmount" :min="1" :max="10000" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rechargeDialog = false">取消</el-button>
        <el-button type="primary" @click="recharge">确认充值</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { updateUserInfo, updatePassword, rechargeWallet, getUserInfo } from '../api'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const formRef = ref()
const pwdFormRef = ref()
const form = ref({ nickname: '', phone: '', email: '', avatar: '' })
const pwdForm = ref({ oldPassword: '', newPassword: '', confirmPassword: '' })
const rechargeDialog = ref(false)
const rechargeAmount = ref(100)

const rules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '密码至少6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认密码', trigger: 'blur' }, { validator: (r, v, cb) => v === pwdForm.value.newPassword ? cb() : cb(new Error('两次密码不一致')), trigger: 'blur' }]
}

onMounted(() => {
  if (userStore.user) {
    form.value = { nickname: userStore.user.nickname || '', phone: userStore.user.phone || '', email: userStore.user.email || '', avatar: userStore.user.avatar || '' }
  }
})

const updateProfile = async () => {
  await formRef.value.validate()
  await updateUserInfo(form.value)
  const res = await getUserInfo()
  userStore.setUser(res.data)
  ElMessage.success('修改成功')
}

const changePassword = async () => {
  await pwdFormRef.value.validate()
  await updatePassword(pwdForm.value)
  ElMessage.success('密码修改成功')
  pwdForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
}

const recharge = async () => {
  await rechargeWallet(rechargeAmount.value)
  const res = await getUserInfo()
  userStore.setUser(res.data)
  ElMessage.success('充值成功')
  rechargeDialog.value = false
}
</script>

<style scoped>
.user-info { text-align: center; padding: 20px 0; }
.user-info h2 { margin: 15px 0 10px; }
.wallet { text-align: center; padding: 20px 0; }
.wallet .label { color: #999; margin-bottom: 10px; }
.wallet .balance { font-size: 32px; font-weight: bold; color: #f56c6c; margin-bottom: 15px; }
</style>
