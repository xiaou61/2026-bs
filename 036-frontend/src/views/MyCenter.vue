<template>
  <div class="my-center">
    <div class="container">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="我的捐赠" name="donations">
          <el-table :data="donations" style="width: 100%">
            <el-table-column prop="project.title" label="项目名称" />
            <el-table-column prop="amount" label="捐赠金额" />
            <el-table-column prop="createTime" label="捐赠时间" />
            <el-table-column prop="paymentStatus" label="支付状态">
              <template #default="{ row }">
                <el-tag :type="getPaymentStatusType(row.paymentStatus)">
                  {{ getPaymentStatusText(row.paymentStatus) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="我的项目" name="projects">
          <el-button type="primary" @click="showCreateDialog = true" style="margin-bottom: 20px">
            创建项目
          </el-button>
          <el-table :data="projects" style="width: 100%">
            <el-table-column prop="title" label="项目名称" />
            <el-table-column prop="currentAmount" label="当前金额" />
            <el-table-column prop="targetAmount" label="目标金额" />
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button @click="$router.push(`/project/${row.id}`)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog v-model="showCreateDialog" title="创建公益项目" width="600px">
      <el-form :model="projectForm" label-width="100px">
        <el-form-item label="项目标题">
          <el-input v-model="projectForm.title" />
        </el-form-item>
        <el-form-item label="项目描述">
          <el-input v-model="projectForm.description" type="textarea" rows="4" />
        </el-form-item>
        <el-form-item label="项目分类">
          <el-input v-model="projectForm.category" />
        </el-form-item>
        <el-form-item label="目标金额">
          <el-input-number v-model="projectForm.targetAmount" :min="1" />
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="projectForm.startDate" type="datetime" />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="projectForm.endDate" type="datetime" />
        </el-form-item>
        <el-form-item label="组织名称">
          <el-input v-model="projectForm.organizationName" />
        </el-form-item>
        <el-form-item label="项目地点">
          <el-input v-model="projectForm.location" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreateProject">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
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

const loadDonations = async () => {
  try {
    const res = await getMyDonations({ page: 0, size: 10 })
    donations.value = res.data.content
  } catch (error) {
    console.error(error)
  }
}

const loadProjects = async () => {
  try {
    const res = await getMyProjects({ page: 0, size: 10 })
    projects.value = res.data.content
  } catch (error) {
    console.error(error)
  }
}

const handleCreateProject = async () => {
  try {
    await createProject({
      ...projectForm.value,
      startDate: formatDateTime(projectForm.value.startDate),
      endDate: formatDateTime(projectForm.value.endDate)
    })
    ElMessage.success('创建成功')
    showCreateDialog.value = false
    loadProjects()
  } catch (error) {
    console.error(error)
  }
}

const formatDateTime = (value) => {
  const date = value instanceof Date ? value : new Date(value)
  const pad = (num) => String(num).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
}

const getPaymentStatusType = (status) => {
  const map = {
    SUCCESS: 'success',
    PENDING: 'warning',
    FAILED: 'danger'
  }
  return map[status] || 'info'
}

const getPaymentStatusText = (status) => {
  const map = {
    SUCCESS: '成功',
    PENDING: '待支付',
    FAILED: '失败'
  }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿'
  }
  return map[status] || status
}

watch(activeTab, (newVal) => {
  if (newVal === 'donations') {
    loadDonations()
  } else if (newVal === 'projects') {
    loadProjects()
  }
})

onMounted(() => {
  loadDonations()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>
