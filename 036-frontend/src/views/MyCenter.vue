&lt;template&gt;
  &lt;div class="my-center"&gt;
    &lt;div class="container"&gt;
      &lt;el-tabs v-model="activeTab"&gt;
        &lt;el-tab-pane label="我的捐赠" name="donations"&gt;
          &lt;el-table :data="donations" style="width: 100%"&gt;
            &lt;el-table-column prop="project.title" label="项目名称" /&gt;
            &lt;el-table-column prop="amount" label="捐赠金额" /&gt;
            &lt;el-table-column prop="createTime" label="捐赠时间" /&gt;
            &lt;el-table-column prop="paymentStatus" label="支付状态"&gt;
              &lt;template #default="{ row }"&gt;
                &lt;el-tag :type="getPaymentStatusType(row.paymentStatus)"&gt;
                  {{ getPaymentStatusText(row.paymentStatus) }}
                &lt;/el-tag&gt;
              &lt;/template&gt;
            &lt;/el-table-column&gt;
          &lt;/el-table&gt;
        &lt;/el-tab-pane&gt;
        &lt;el-tab-pane label="我的项目" name="projects"&gt;
          &lt;el-button type="primary" @click="showCreateDialog = true" style="margin-bottom: 20px"&gt;
            创建项目
          &lt;/el-button&gt;
          &lt;el-table :data="projects" style="width: 100%"&gt;
            &lt;el-table-column prop="title" label="项目名称" /&gt;
            &lt;el-table-column prop="currentAmount" label="当前金额" /&gt;
            &lt;el-table-column prop="targetAmount" label="目标金额" /&gt;
            &lt;el-table-column prop="status" label="状态"&gt;
              &lt;template #default="{ row }"&gt;
                &lt;el-tag :type="getStatusType(row.status)"&gt;{{ getStatusText(row.status) }}&lt;/el-tag&gt;
              &lt;/template&gt;
            &lt;/el-table-column&gt;
            &lt;el-table-column label="操作"&gt;
              &lt;template #default="{ row }"&gt;
                &lt;el-button @click="$router.push(`/project/${row.id}`)"&gt;查看&lt;/el-button&gt;
              &lt;/template&gt;
            &lt;/el-table-column&gt;
          &lt;/el-table&gt;
        &lt;/el-tab-pane&gt;
      &lt;/el-tabs&gt;
    &lt;/div&gt;

    &lt;el-dialog v-model="showCreateDialog" title="创建公益项目" width="600px"&gt;
      &lt;el-form :model="projectForm" label-width="100px"&gt;
        &lt;el-form-item label="项目标题"&gt;
          &lt;el-input v-model="projectForm.title" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="项目描述"&gt;
          &lt;el-input v-model="projectForm.description" type="textarea" rows="4" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="项目分类"&gt;
          &lt;el-input v-model="projectForm.category" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="目标金额"&gt;
          &lt;el-input-number v-model="projectForm.targetAmount" :min="1" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="开始日期"&gt;
          &lt;el-date-picker v-model="projectForm.startDate" type="datetime" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="结束日期"&gt;
          &lt;el-date-picker v-model="projectForm.endDate" type="datetime" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="组织名称"&gt;
          &lt;el-input v-model="projectForm.organizationName" /&gt;
        &lt;/el-form-item&gt;
        &lt;el-form-item label="项目地点"&gt;
          &lt;el-input v-model="projectForm.location" /&gt;
        &lt;/el-form-item&gt;
      &lt;/el-form&gt;
      &lt;template #footer&gt;
        &lt;el-button @click="showCreateDialog = false"&gt;取消&lt;/el-button&gt;
        &lt;el-button type="primary" @click="handleCreateProject"&gt;创建&lt;/el-button&gt;
      &lt;/template&gt;
    &lt;/el-dialog&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
import { ref, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyDonations } from '@/api/donation'
import { getMyProjects, createProject } from '@/api/project'

const activeTab = ref('donations')
const donations = ref([])
const projects = ref([])
const showCreateDialog = ref(false)
const projectForm = ref({
  title: '',
  description: '',
  category: '',
  targetAmount: 1000,
  startDate: new Date(),
  endDate: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000),
  organizationName: '',
  location: ''
})

const loadDonations = async () =&gt; {
  try {
    const res = await getMyDonations({ page: 0, size: 10 })
    donations.value = res.data.content
  } catch (error) {
    console.error(error)
  }
}

const loadProjects = async () =&gt; {
  try {
    const res = await getMyProjects({ page: 0, size: 10 })
    projects.value = res.data.content
  } catch (error) {
    console.error(error)
  }
}

const handleCreateProject = async () =&gt; {
  try {
    await createProject(projectForm.value)
    ElMessage.success('创建成功')
    showCreateDialog.value = false
    loadProjects()
  } catch (error) {
    console.error(error)
  }
}

const getPaymentStatusType = (status) =&gt; {
  const map = {
    SUCCESS: 'success',
    PENDING: 'warning',
    FAILED: 'danger'
  }
  return map[status] || 'info'
}

const getPaymentStatusText = (status) =&gt; {
  const map = {
    SUCCESS: '成功',
    PENDING: '待支付',
    FAILED: '失败'
  }
  return map[status] || status
}

const getStatusType = (status) =&gt; {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning'
  }
  return map[status] || 'info'
}

const getStatusText = (status) =&gt; {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿'
  }
  return map[status] || status
}

watch(activeTab, (newVal) =&gt; {
  if (newVal === 'donations') {
    loadDonations()
  } else if (newVal === 'projects') {
    loadProjects()
  }
})

onMounted(() =&gt; {
  loadDonations()
})
&lt;/script&gt;

&lt;style scoped&gt;
.container {
  max-width: 1200px;
  margin: 0 auto;
}
&lt;/style&gt;
