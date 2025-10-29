<template>
  <el-card>
    <template #header>
      <h3>我的投递</h3>
    </template>
    <el-table :data="applications" v-loading="loading">
      <el-table-column prop="jobId" label="岗位ID" width="100" />
      <el-table-column prop="status" label="状态" width="120">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'pending'" type="info">待筛选</el-tag>
          <el-tag v-else-if="row.status === 'screening'" type="warning">筛选中</el-tag>
          <el-tag v-else-if="row.status === 'interview'" type="primary">面试邀约</el-tag>
          <el-tag v-else-if="row.status === 'offer'" type="success">发放offer</el-tag>
          <el-tag v-else type="danger">已拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
      <el-table-column prop="createdAt" label="投递时间" width="180" />
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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyApplications } from '@/api/application'

const applications = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const loadApplications = async () => {
  loading.value = true
  try {
    const res = await getMyApplications({
      page: pagination.value.page,
      size: pagination.value.size
    })
    applications.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadApplications()
})
</script>

