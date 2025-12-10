<template>
  <div class="profile-page">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>个人信息</template>
          <el-form :model="userInfo" label-width="100px">
            <el-form-item label="用户名">
              <el-input :value="userInfo.username" disabled />
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
            <el-form-item label="学号/工号">
              <el-input v-model="userInfo.studentId" />
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="userInfo.gender">
                <el-radio :label="0">未知</el-radio>
                <el-radio :label="1">男</el-radio>
                <el-radio :label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="saveLoading" @click="handleSave">保存修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>信用信息</template>
          <div class="credit-info">
            <div class="credit-score">{{ userInfo.creditScore }}</div>
            <div class="credit-label">信用分</div>
            <el-progress :percentage="Math.min(userInfo.creditScore, 100)" :color="creditColor" />
          </div>
        </el-card>
        <el-card class="mt-20">
          <template #header>认证状态</template>
          <div class="auth-status">
            <el-tag :type="authStatusType[userInfo.authStatus]" size="large">
              {{ authStatusText[userInfo.authStatus] }}
            </el-tag>
            <p class="tip">实名认证后可享受更多服务</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { userApi } from '@/api'

const userInfo = ref({})
const saveLoading = ref(false)

const authStatusText = { 0: '未认证', 1: '待审核', 2: '已认证', 3: '认证失败' }
const authStatusType = { 0: 'info', 1: 'warning', 2: 'success', 3: 'danger' }

const creditColor = computed(() => {
  const score = userInfo.value.creditScore || 0
  if (score >= 80) return '#67c23a'
  if (score >= 60) return '#e6a23c'
  return '#f56c6c'
})

const loadUserInfo = async () => {
  const res = await userApi.getInfo()
  userInfo.value = res.data
}

const handleSave = async () => {
  saveLoading.value = true
  try {
    await userApi.updateInfo(userInfo.value)
    ElMessage.success('保存成功')
  } finally {
    saveLoading.value = false
  }
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped lang="scss">
.credit-info {
  text-align: center;
  
  .credit-score {
    font-size: 48px;
    font-weight: bold;
    color: #409eff;
  }
  
  .credit-label {
    color: #999;
    margin-bottom: 20px;
  }
}

.auth-status {
  text-align: center;
  
  .tip {
    margin-top: 15px;
    font-size: 12px;
    color: #999;
  }
}
</style>
