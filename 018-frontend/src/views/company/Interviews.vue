<template>
  <el-card>
    <template #header>
      <h3>面试管理</h3>
    </template>
    <el-table :data="interviews" v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="userId" label="学生ID" width="100" />
      <el-table-column prop="jobId" label="岗位ID" width="100" />
      <el-table-column prop="interviewType" label="面试类型" width="120">
        <template #default="{ row }">
          <el-tag v-if="row.interviewType === 'online'" type="success">线上</el-tag>
          <el-tag v-else type="primary">线下</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="interviewTime" label="面试时间" width="180" />
      <el-table-column prop="location" label="面试地点/链接" />
      <el-table-column prop="interviewer" label="面试官" width="120" />
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'scheduled'" type="primary">已安排</el-tag>
          <el-tag v-else-if="row.status === 'completed'" type="success">已完成</el-tag>
          <el-tag v-else type="info">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑反馈</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.size"
      :total="pagination.total"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: center"
    />
  </el-card>

  <el-dialog v-model="dialogVisible" title="编辑反馈" width="500px">
    <el-form :model="form" label-width="100px">
      <el-form-item label="面试状态">
        <el-select v-model="form.status">
          <el-option label="已安排" value="scheduled" />
          <el-option label="已完成" value="completed" />
          <el-option label="已取消" value="cancelled" />
        </el-select>
      </el-form-item>
      <el-form-item label="面试反馈">
        <el-input v-model="form.feedback" type="textarea" :rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSave">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyInterviews, updateInterview } from '@/api/interview'
import { ElMessage } from 'element-plus'

const interviews = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)
const form = ref({
  id: null,
  status: '',
  feedback: ''
})

const loadInterviews = async () => {
  loading.value = true
  try {
    const res = await getMyInterviews({
      page: pagination.value.page,
      size: pagination.value.size
    })
    interviews.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleEdit = (row) => {
  form.value = {
    id: row.id,
    status: row.status,
    feedback: row.feedback
  }
  dialogVisible.value = true
}

const handleSave = async () => {
  try {
    await updateInterview(form.value)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadInterviews()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadInterviews()
})
</script>

