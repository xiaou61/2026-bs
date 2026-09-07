<template>
  <el-card>
    <template #header>
      <div class="header-bar">
        <h3>简历管理</h3>
        <el-input
          v-model="searchJobId"
          placeholder="按岗位ID筛选，可留空"
          style="width: 220px"
          clearable
          @change="handleFilterChange"
        />
      </div>
    </template>
    <el-table :data="applications" v-loading="loading">
      <el-table-column prop="jobTitle" label="岗位名称" min-width="180" />
      <el-table-column prop="studentName" label="学生姓名" width="120" />
      <el-table-column prop="resumeName" label="简历名称" width="140" />
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'pending'" type="info">待筛选</el-tag>
          <el-tag v-else-if="row.status === 'screening'" type="warning">筛选中</el-tag>
          <el-tag v-else-if="row.status === 'interview'" type="primary">面试邀约</el-tag>
          <el-tag v-else-if="row.status === 'offer'" type="success">发放offer</el-tag>
          <el-tag v-else type="danger">已拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="160" />
      <el-table-column prop="createdAt" label="投递时间" width="180" />
      <el-table-column label="操作" width="320" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="viewResume(row.resumeId)">查看简历</el-button>
          <el-button type="success" link @click="handleUpdateStatus(row)">更新状态</el-button>
          <el-button type="warning" link @click="handleInterview(row)">安排面试</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.size"
      :total="pagination.total"
      layout="total, prev, pager, next"
      @current-change="loadApplications"
      style="margin-top: 20px; justify-content: center"
    />
  </el-card>

  <el-dialog v-model="resumeDialogVisible" title="简历详情" width="720px">
    <el-descriptions :column="2" border v-loading="resumeLoading">
      <el-descriptions-item label="姓名">{{ resumeDetail.name }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{ resumeDetail.gender }}</el-descriptions-item>
      <el-descriptions-item label="电话">{{ resumeDetail.phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ resumeDetail.email }}</el-descriptions-item>
      <el-descriptions-item label="院校">{{ resumeDetail.university }}</el-descriptions-item>
      <el-descriptions-item label="专业">{{ resumeDetail.major }}</el-descriptions-item>
      <el-descriptions-item label="学历">{{ resumeDetail.education }}</el-descriptions-item>
      <el-descriptions-item label="技能">{{ resumeDetail.skills }}</el-descriptions-item>
      <el-descriptions-item label="实习经历" :span="2">{{ resumeDetail.internshipExperience }}</el-descriptions-item>
      <el-descriptions-item label="项目经历" :span="2">{{ resumeDetail.projectExperience }}</el-descriptions-item>
      <el-descriptions-item label="自我介绍" :span="2">{{ resumeDetail.selfIntroduction }}</el-descriptions-item>
    </el-descriptions>
  </el-dialog>

  <el-dialog v-model="statusDialogVisible" title="更新状态" width="400px">
    <el-form :model="statusForm" label-width="80px">
      <el-form-item label="状态">
        <el-select v-model="statusForm.status">
          <el-option label="待筛选" value="pending" />
          <el-option label="筛选中" value="screening" />
          <el-option label="面试邀约" value="interview" />
          <el-option label="发放offer" value="offer" />
          <el-option label="已拒绝" value="rejected" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="statusForm.remark" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="statusDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmUpdateStatus">确定</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="interviewDialogVisible" title="安排面试" width="500px">
    <el-form :model="interviewForm" label-width="100px">
      <el-form-item label="面试类型">
        <el-radio-group v-model="interviewForm.interviewType">
          <el-radio value="online">线上</el-radio>
          <el-radio value="offline">线下</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="面试时间">
        <el-date-picker
          v-model="interviewForm.interviewTime"
          type="datetime"
          placeholder="选择时间"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item label="地点/链接">
        <el-input v-model="interviewForm.location" />
      </el-form-item>
      <el-form-item label="面试官">
        <el-input v-model="interviewForm.interviewer" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="interviewDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmInterview">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getReceivedApplications, updateApplicationStatus } from '@/api/application'
import { createInterview } from '@/api/interview'
import { getResumeById } from '@/api/resume'

const applications = ref([])
const loading = ref(false)
const searchJobId = ref('')
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const resumeDialogVisible = ref(false)
const resumeLoading = ref(false)
const resumeDetail = ref({})

const statusDialogVisible = ref(false)
const statusForm = ref({
  id: null,
  status: '',
  remark: ''
})

const interviewDialogVisible = ref(false)
const interviewForm = ref({
  applicationId: null,
  interviewType: 'online',
  interviewTime: '',
  location: '',
  interviewer: ''
})

const loadApplications = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.value.page,
      size: pagination.value.size
    }
    if (searchJobId.value) {
      params.jobId = searchJobId.value
    }
    const res = await getReceivedApplications(params)
    applications.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleFilterChange = () => {
  pagination.value.page = 1
  loadApplications()
}

const viewResume = async (resumeId) => {
  resumeDialogVisible.value = true
  resumeLoading.value = true
  try {
    const res = await getResumeById(resumeId)
    resumeDetail.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    resumeLoading.value = false
  }
}

const handleUpdateStatus = (row) => {
  statusForm.value = {
    id: row.id,
    status: row.status,
    remark: row.remark
  }
  statusDialogVisible.value = true
}

const confirmUpdateStatus = async () => {
  try {
    await updateApplicationStatus(statusForm.value)
    ElMessage.success('更新成功')
    statusDialogVisible.value = false
    loadApplications()
  } catch (error) {
    console.error(error)
  }
}

const handleInterview = (row) => {
  interviewForm.value = {
    applicationId: row.id,
    interviewType: 'online',
    interviewTime: '',
    location: '',
    interviewer: ''
  }
  interviewDialogVisible.value = true
}

const confirmInterview = async () => {
  try {
    await createInterview(interviewForm.value)
    ElMessage.success('安排成功')
    interviewDialogVisible.value = false
    loadApplications()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadApplications()
})
</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
