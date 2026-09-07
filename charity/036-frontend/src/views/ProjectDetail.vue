<template>
  <div class="project-detail" v-if="project">
    <div class="container">
      <el-row :gutter="30">
        <el-col :span="16">
          <el-card>
            <img :src="project.coverImage || '/cover-placeholder.svg'" class="cover-image">
            <h1>{{ project.title }}</h1>
            <div class="project-meta">
              <el-tag :type="getStatusType(project.status)">{{ getStatusText(project.status) }}</el-tag>
              <span>发起组织：{{ project.organizationName }}</span>
              <span>地区：{{ project.location }}</span>
            </div>
            <div class="project-description">
              <h3>项目介绍</h3>
              <p>{{ project.description }}</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div class="donate-card">
              <div class="amount-info">
                <div class="current-amount">¥{{ project.currentAmount }}</div>
                <div class="target-amount">目标 ¥{{ project.targetAmount }}</div>
              </div>
              <el-progress :percentage="calculateProgress()" />
              <div class="donors-info">
                <span>{{ project.donorCount }} 人参与捐赠</span>
              </div>
              <el-button type="primary" size="large" @click="showDonateDialog = true" style="width: 100%">
                立即捐赠
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <el-dialog v-model="showDonateDialog" title="捐赠" width="500px">
      <el-form :model="donateForm" ref="donateFormRef">
        <el-form-item label="捐赠金额" required>
          <el-input-number v-model="donateForm.amount" :min="1" :step="10" />
        </el-form-item>
        <el-form-item label="留言">
          <el-input v-model="donateForm.message" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="支付方式" required>
          <el-radio-group v-model="donateForm.paymentMethod">
            <el-radio value="ALIPAY">支付宝</el-radio>
            <el-radio value="WECHAT">微信支付</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="donateForm.anonymous">匿名捐赠</el-checkbox>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDonateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleDonate">确认捐赠</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProjectDetail } from '@/api/project'
import { createDonation } from '@/api/donation'
import { useUserStore } from '@/store/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const project = ref(null)
const showDonateDialog = ref(false)
const donateFormRef = ref()
const donateForm = ref({
  amount: 10,
  message: '',
  paymentMethod: 'ALIPAY',
  anonymous: false
})

const loadProject = async () => {
  try {
    const res = await getProjectDetail(route.params.id)
    project.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = () => {
  if (!project.value.targetAmount || project.value.targetAmount === 0) return 0
  return Math.round((project.value.currentAmount / project.value.targetAmount) * 100)
}

const getStatusType = (status) => {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿',
    CANCELLED: '已取消'
  }
  return map[status] || status
}

const handleDonate = async () => {
  if (!userStore.token) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  try {
    await createDonation({
      projectId: project.value.id,
      ...donateForm.value
    })
    ElMessage.success('捐赠成功')
    showDonateDialog.value = false
    loadProject()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadProject()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}

.cover-image {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
  margin-bottom: 20px;
}

h1 {
  font-size: 28px;
  margin-bottom: 15px;
}

.project-meta {
  display: flex;
  gap: 20px;
  align-items: center;
  color: #666;
  margin-bottom: 20px;
}

.project-description {
  margin-top: 30px;
}

.project-description h3 {
  font-size: 20px;
  margin-bottom: 15px;
}

.project-description p {
  line-height: 1.8;
  color: #666;
}

.donate-card {
  text-align: center;
}

.amount-info {
  margin-bottom: 20px;
}

.current-amount {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 5px;
}

.target-amount {
  color: #666;
  font-size: 14px;
}

.donors-info {
  margin: 15px 0 20px;
  color: #666;
}
</style>
