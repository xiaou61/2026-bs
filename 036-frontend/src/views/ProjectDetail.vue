&lt;template&gt;
  &lt;div class="project-detail" v-if="project"&gt;
    &lt;div class="container"&gt;
      &lt;el-row :gutter="30"&gt;
        &lt;el-col :span="16"&gt;
          &lt;el-card&gt;
            &lt;img :src="project.coverImage || 'https://via.placeholder.com/800x400'" class="cover-image"&gt;
            &lt;h1&gt;{{ project.title }}&lt;/h1&gt;
            &lt;div class="project-meta"&gt;
              &lt;el-tag :type="getStatusType(project.status)"&gt;{{ getStatusText(project.status) }}&lt;/el-tag&gt;
              &lt;span&gt;发起组织：{{ project.organizationName }}&lt;/span&gt;
              &lt;span&gt;地区：{{ project.location }}&lt;/span&gt;
            &lt;/div&gt;
            &lt;div class="project-description"&gt;
              &lt;h3&gt;项目介绍&lt;/h3&gt;
              &lt;p&gt;{{ project.description }}&lt;/p&gt;
            &lt;/div&gt;
          &lt;/el-card&gt;
        &lt;/el-col&gt;
        &lt;el-col :span="8"&gt;
          &lt;el-card&gt;
            &lt;div class="donate-card"&gt;
              &lt;div class="amount-info"&gt;
                &lt;div class="current-amount"&gt;¥{{ project.currentAmount }}&lt;/div&gt;
                &lt;div class="target-amount"&gt;目标 ¥{{ project.targetAmount }}&lt;/div&gt;
              &lt;/div&gt;
              &lt;el-progress :percentage="calculateProgress()" /&gt;
              &lt;div class="donors-info"&gt;
                &lt;span&gt;{{ project.donorCount }} 人参与捐赠&lt;/span&gt;
              &lt;/div&gt;
              &lt;el-button type="primary" size="large" @click="showDonateDialog = true" style="width: 100%"&gt;
                立即捐赠
              &lt;/el-button&gt;
            &lt;/div&gt;
          &lt;/el-card&gt;
        &lt;/el-col&gt;
      &lt;/el-row&gt;
    &lt;/div&gt;

    &lt;el-dialog v-model="showDonateDialog" title="捐赠" width="500px"&gt;
      &lt;el-form :model="donateForm" ref="donateFormRef"&gt;
        &lt;el-form-item label="捐赠金额" required&gt;
          &lt;el-input-number v-model="donateForm.amount" :min="1" :step="10" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="留言"&gt;
          &lt;el-input v-model="donateForm.message" type="textarea" rows="3" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="支付方式" required&gt;
          &lt;el-radio-group v-model="donateForm.paymentMethod"&gt;
            &lt;el-radio label="ALIPAY"&gt;支付宝&lt;/el-radio&gt;
            &lt;el-radio label="WECHAT"&gt;微信支付&lt;/el-radio&gt;
          &lt;/el-radio-group&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item&gt;
          &lt;el-checkbox v-model="donateForm.anonymous"&gt;匿名捐赠&lt;/el-checkbox&gt;
        &lt;/el-form-item&gt;
      &lt;/el-form&gt;
      &lt;template #footer&gt;
        &lt;el-button @click="showDonateDialog = false"&gt;取消&lt;/el-button&gt;
        &lt;el-button type="primary" @click="handleDonate"&gt;确认捐赠&lt;/el-button&gt;
      &lt;/template&gt;
    &lt;/el-dialog&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
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

const loadProject = async () =&gt; {
  try {
    const res = await getProjectDetail(route.params.id)
    project.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = () =&gt; {
  if (!project.value.targetAmount || project.value.targetAmount === 0) return 0
  return Math.round((project.value.currentAmount / project.value.targetAmount) * 100)
}

const getStatusType = (status) =&gt; {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) =&gt; {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿',
    CANCELLED: '已取消'
  }
  return map[status] || status
}

const handleDonate = async () =&gt; {
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

onMounted(() =&gt; {
  loadProject()
})
&lt;/script&gt;

&lt;style scoped&gt;
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
&lt;/style&gt;
