<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
        </div>
      </template>
      <el-form :model="userInfo" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="userInfo.username" disabled />
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="userInfo.realName" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="userInfo.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userInfo.email" />
        </el-form-item>
        <el-form-item label="账户余额">
          <el-input v-model="userInfo.balance" disabled>
            <template #append>元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-tag :type="userInfo.role === 'admin' ? 'danger' : 'success'">
            {{ userInfo.role === 'admin' ? '管理员' : '普通用户' }}
          </el-tag>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleUpdate">更新信息</el-button>
          <el-button @click="showRechargeDialog = true">余额充值</el-button>
          <el-button @click="showPasswordDialog = true">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog v-model="showRechargeDialog" title="余额充值" width="400px">
      <el-form :model="rechargeForm">
        <el-form-item label="充值金额" label-width="100px">
          <el-input-number v-model="rechargeForm.amount" :min="0.01" :step="100" :precision="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRechargeDialog = false">取消</el-button>
        <el-button type="primary" @click="handleRecharge">确认充值</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showPasswordDialog" title="修改密码" width="400px">
      <el-form :model="passwordForm">
        <el-form-item label="旧密码" label-width="100px">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" label-width="100px">
          <el-input v-model="passwordForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" label-width="100px">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showPasswordDialog = false">取消</el-button>
        <el-button type="primary" @click="handlePasswordChange">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi, paymentApi } from '@/api'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

const userInfo = ref({
  username: '',
  realName: '',
  phone: '',
  email: '',
  balance: 0,
  role: ''
})

const showRechargeDialog = ref(false)
const rechargeForm = ref({
  amount: 100
})

const showPasswordDialog = ref(false)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const loadUserInfo = async () => {
  try {
    const res = await userApi.getUserInfo()
    userInfo.value = res.data
  } catch (error) {
    ElMessage.error('获取用户信息失败')
  }
}

const handleUpdate = async () => {
  try {
    await userApi.updateUserInfo(userInfo.value)
    ElMessage.success('更新成功')
    loadUserInfo()
  } catch (error) {
    ElMessage.error('更新失败')
  }
}

const handleRecharge = async () => {
  try {
    await paymentApi.recharge(rechargeForm.value.amount)
    ElMessage.success('充值成功')
    showRechargeDialog.value = false
    loadUserInfo()
  } catch (error) {
    ElMessage.error('充值失败')
  }
}

const handlePasswordChange = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次密码输入不一致')
    return
  }
  try {
    await userApi.changePassword(passwordForm.value)
    ElMessage.success('密码修改成功，请重新登录')
    showPasswordDialog.value = false
    userStore.logout()
  } catch (error) {
    ElMessage.error('密码修改失败')
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.profile-card {
  max-width: 600px;
  margin: 0 auto;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
}
</style>
