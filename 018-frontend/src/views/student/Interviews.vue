<template>
  <el-card>
    <template #header>
      <h3>我的面试</h3>
    </template>
    <el-table :data="interviews" v-loading="loading">
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
      <el-table-column prop="feedback" label="反馈" />
    </el-table>
    <el-pagination
      v-model:current-page="pagination.page"
      v-model:page-size="pagination.size"
      :total="pagination.total"
      layout="total, prev, pager, next"
      @current-change="loadInterviews"
      style="margin-top: 20px; justify-content: center"
    />
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyInterviews } from '@/api/interview'

const interviews = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
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

onMounted(() => {
  loadInterviews()
})
</script>

