<template>
  <el-card v-loading="loading">
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center">
        <h2>{{ job.title }}</h2>
        <el-button type="primary" @click="handleApply">投递简历</el-button>
      </div>
    </template>
    
    <el-descriptions :column="2" border>
      <el-descriptions-item label="岗位类型">
        <el-tag v-if="job.jobType === 'internship'" type="success">实习</el-tag>
        <el-tag v-else type="primary">校招</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="职位类别">{{ job.category }}</el-descriptions-item>
      <el-descriptions-item label="工作地点">{{ job.location }}</el-descriptions-item>
      <el-descriptions-item label="薪资">{{ job.salaryMin }}-{{ job.salaryMax }}元/月</el-descriptions-item>
      <el-descriptions-item label="学历要求">{{ job.education }}</el-descriptions-item>
      <el-descriptions-item label="专业要求">{{ job.major || '不限' }}</el-descriptions-item>
      <el-descriptions-item label="招聘人数">{{ job.headcount }}人</el-descriptions-item>
      <el-descriptions-item label="浏览次数">{{ job.views }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <h3>岗位描述</h3>
    <div class="content">{{ job.description }}</div>

    <el-divider />

    <h3>任职要求</h3>
    <div class="content">{{ job.requirement }}</div>

    <el-divider />

    <h3>技能要求</h3>
    <div class="content">{{ job.skills }}</div>
  </el-card>

  <el-dialog v-model="dialogVisible" title="投递简历" width="400px">
    <el-form :model="applyForm" label-width="80px">
      <el-form-item label="选择简历">
        <el-select v-model="applyForm.resumeId" placeholder="请选择简历">
          <el-option :label="resume.name" :value="resume.id" v-if="resume.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmApply">确认投递</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getJobById } from '@/api/job'
import { getMyResume } from '@/api/resume'
import { submitApplication } from '@/api/application'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const job = ref({})
const resume = ref({})
const loading = ref(false)
const dialogVisible = ref(false)
const applyForm = ref({
  resumeId: null
})

const loadJob = async () => {
  loading.value = true
  try {
    const res = await getJobById(route.params.id)
    job.value = res.data
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const loadResume = async () => {
  try {
    const res = await getMyResume()
    resume.value = res.data
    applyForm.value.resumeId = res.data.id
  } catch (error) {
    console.error(error)
  }
}

const handleApply = () => {
  if (!resume.value.id) {
    ElMessage.warning('请先创建简历')
    router.push('/student/resume')
    return
  }
  dialogVisible.value = true
}

const confirmApply = async () => {
  try {
    await submitApplication({
      jobId: job.value.id,
      resumeId: applyForm.value.resumeId
    })
    ElMessage.success('投递成功')
    dialogVisible.value = false
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadJob()
  loadResume()
})
</script>

<style scoped>
.content {
  white-space: pre-wrap;
  line-height: 1.8;
}
</style>

