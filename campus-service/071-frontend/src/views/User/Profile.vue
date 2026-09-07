<template>
  <div>
    <el-card>
      <template #header>个人信息</template>
      <el-form :model="form" label-width="100px" style="max-width:500px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="角色">
          <el-tag>{{ roleMap[form.role] }}</el-tag>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="form.realName" />
        </el-form-item>
        <el-form-item v-if="form.role === 'user'" label="信用分">
          <el-tag :type="form.creditScore >= 90 ? 'success' : form.creditScore >= 70 ? '' : form.creditScore >= 50 ? 'warning' : 'danger'">{{ form.creditScore }}</el-tag>
        </el-form-item>
        <el-form-item v-if="form.role === 'user'" label="余额">
          <span style="font-size:18px;color:#F56C6C;font-weight:bold">¥{{ form.balance }}</span>
        </el-form-item>
        <el-form-item v-if="form.role === 'user'" label="押金状态">
          <el-tag :type="form.depositPaid ? 'success' : 'info'">{{ form.depositPaid ? '已缴纳' : '未缴纳' }}</el-tag>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top:15px">
      <template #header>修改密码</template>
      <el-form :model="pwdForm" label-width="100px" style="max-width:500px">
        <el-form-item label="原密码"><el-input v-model="pwdForm.oldPassword" type="password" show-password /></el-form-item>
        <el-form-item label="新密码"><el-input v-model="pwdForm.newPassword" type="password" show-password /></el-form-item>
        <el-form-item><el-button type="warning" @click="handleChangePwd">修改密码</el-button></el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, updateUserInfo, updatePassword } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const roleMap = { admin: '管理员', operator: '运维人员', user: '普通用户' }
const form = reactive({ username: '', phone: '', realName: '', role: '', creditScore: 0, balance: 0, depositPaid: 0 })
const pwdForm = reactive({ oldPassword: '', newPassword: '' })

onMounted(async () => {
  const res = await getUserInfo()
  Object.assign(form, res.data)
})

const handleSave = async () => {
  await updateUserInfo({ phone: form.phone, realName: form.realName })
  userStore.updateUser({ phone: form.phone, realName: form.realName })
  ElMessage.success('保存成功')
}

const handleChangePwd = async () => {
  await updatePassword(pwdForm)
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
}
</script>
