<template>
  <section class="page-shell" v-loading="loading">
    <div class="page-head">
      <div>
        <p class="eyebrow">Profile Center</p>
        <h1>个人资料与健康档案</h1>
        <p>同步维护基础资料、健康诉求和创作者认证状态，方便系统推荐更贴近你的内容。</p>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :lg="12" :xs="24">
        <el-card class="panel-card" shadow="never">
          <template #header>基础信息</template>
          <el-form label-position="top" :model="profileForm">
            <el-form-item label="用户名">
              <el-input v-model="profileForm.username" disabled />
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="profileForm.nickname" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="profileForm.email" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profileForm.phone" />
            </el-form-item>
            <el-form-item label="头像地址">
              <el-input v-model="profileForm.avatar" />
            </el-form-item>
            <el-form-item label="个人简介">
              <el-input v-model="profileForm.bio" type="textarea" :rows="4" />
            </el-form-item>
            <el-button type="primary" :loading="savingProfile" @click="saveProfile">
              保存基础信息
            </el-button>
          </el-form>
        </el-card>
      </el-col>

      <el-col :lg="12" :xs="24">
        <el-card class="panel-card" shadow="never">
          <template #header>健康档案</template>
          <el-form label-position="top" :model="healthForm">
            <el-form-item label="体质">
              <el-input v-model="healthForm.constitution" placeholder="例如：气虚质 / 平和质" />
            </el-form-item>
            <el-form-item label="过敏情况">
              <el-input v-model="healthForm.allergies" type="textarea" :rows="3" />
            </el-form-item>
            <el-form-item label="健康目标">
              <el-input v-model="healthForm.healthGoals" type="textarea" :rows="3" />
            </el-form-item>
            <el-form-item label="饮食限制">
              <el-input v-model="healthForm.dietaryRestrictions" type="textarea" :rows="3" />
            </el-form-item>
            <el-button type="primary" plain :loading="savingHealth" @click="saveHealthProfile">
              保存健康档案
            </el-button>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="panel-card" shadow="never">
      <template #header>创作者认证</template>
      <div class="auth-status">
        <div>
          <el-tag :type="authTagType">{{ authStatusText }}</el-tag>
          <p class="auth-copy">
            {{ authInfo?.credentials || '还没有提交创作者认证资料，认证通过后可长期发布专业食疗内容。' }}
          </p>
        </div>
      </div>
      <div v-if="!authInfo || authInfo.status !== 0" class="auth-form">
        <el-input v-model="authForm.authType" placeholder="认证类型，例如：营养师认证 / 创作者认证" />
        <el-input v-model="authForm.credentials" type="textarea" :rows="4" placeholder="填写认证材料说明" />
        <el-button type="primary" :loading="savingAuth" @click="applyAuth">提交认证申请</el-button>
      </div>
    </el-card>
  </section>
</template>

<script setup>
import { computed, reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../stores/user'
import request from '../../api/request'

const userStore = useUserStore()
const loading = ref(false)
const savingProfile = ref(false)
const savingHealth = ref(false)
const savingAuth = ref(false)
const authInfo = ref(null)

const profileForm = reactive({
  username: '',
  nickname: '',
  email: '',
  phone: '',
  avatar: '',
  bio: '',
  userType: 0,
  status: 1
})

const healthForm = reactive({
  constitution: '',
  allergies: '',
  healthGoals: '',
  dietaryRestrictions: ''
})

const authForm = reactive({
  authType: '创作者认证',
  credentials: ''
})

const authStatusText = computed(() => ({
  0: '审核中',
  1: '已通过',
  2: '已拒绝'
}[authInfo.value?.status] || '未申请'))

const authTagType = computed(() => ({
  0: 'warning',
  1: 'success',
  2: 'danger'
}[authInfo.value?.status] || 'info'))

const loadProfile = async () => {
  loading.value = true
  try {
    const [profileResp, healthResp, authResp] = await Promise.all([
      request.get('/user/info'),
      request.get('/health-profile/info').catch(() => ({ data: null })),
      request.get('/creator/auth/info').catch(() => ({ data: null }))
    ])

    Object.assign(profileForm, profileResp.data || {})
    Object.assign(healthForm, healthResp.data || {})
    authInfo.value = authResp.data || null
  } catch (error) {
    ElMessage.error(error?.message || '加载个人资料失败')
  } finally {
    loading.value = false
  }
}

const saveProfile = async () => {
  savingProfile.value = true
  try {
    await userStore.updateUserInfo({ ...profileForm })
    ElMessage.success('基础信息已更新')
  } catch (error) {
    ElMessage.error(error?.message || '保存失败')
  } finally {
    savingProfile.value = false
  }
}

const saveHealthProfile = async () => {
  savingHealth.value = true
  try {
    await request.post('/health-profile/save', { ...healthForm })
    ElMessage.success('健康档案已保存')
    await loadProfile()
  } catch (error) {
    ElMessage.error(error?.message || '保存健康档案失败')
  } finally {
    savingHealth.value = false
  }
}

const applyAuth = async () => {
  if (!authForm.authType.trim() || !authForm.credentials.trim()) {
    ElMessage.warning('请先填写认证类型和材料说明')
    return
  }

  savingAuth.value = true
  try {
    await request.post('/creator/auth/apply', null, {
      params: {
        authType: authForm.authType.trim(),
        credentials: authForm.credentials.trim()
      }
    })
    ElMessage.success('认证申请已提交')
    authForm.credentials = ''
    await loadProfile()
  } catch (error) {
    ElMessage.error(error?.message || '提交认证失败')
  } finally {
    savingAuth.value = false
  }
}

onMounted(loadProfile)
</script>

<style scoped>
.page-shell {
  max-width: 1120px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.page-head,
.panel-card {
  border-radius: 24px;
}

.page-head {
  padding: 30px 32px;
  background: linear-gradient(135deg, #15392c 0%, #2f6a4b 56%, #c7813e 100%);
  color: #f7f3ea;
}

.eyebrow {
  font-size: 12px;
  letter-spacing: 0.16em;
  text-transform: uppercase;
  opacity: 0.82;
  margin-bottom: 8px;
}

.auth-status {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

.auth-copy {
  color: #55655c;
  line-height: 1.8;
  margin-top: 12px;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
</style>
