<template>
  <div>
    <el-card>
      <el-table :data="enrollments" border>
        <el-table-column prop="activityTitle" label="活动标题" />
        <el-table-column prop="userName" label="报名人" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="applyTime" label="报名时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="success" size="small" @click="handleApprove(row.id, 1)">通过</el-button>
            <el-button v-if="row.status === 0" type="danger" size="small" @click="handleApprove(row.id, 2)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getEnrollmentList, updateEnrollmentStatus } from '@/api/enrollment'

const enrollments = ref([])

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已取消' }
  return texts[status]
}

const loadEnrollments = async () => {
  const res = await getEnrollmentList()
  enrollments.value = res.data
}

const handleApprove = async (id, status) => {
  try {
    await updateEnrollmentStatus(id, status)
    ElMessage.success('操作成功')
    loadEnrollments()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadEnrollments()
})
</script>

