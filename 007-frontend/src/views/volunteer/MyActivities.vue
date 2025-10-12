<template>
  <div>
    <el-card>
      <el-table :data="activities" border>
        <el-table-column prop="activityTitle" label="活动标题" />
        <el-table-column prop="userName" label="报名人" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="applyTime" label="报名时间" width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row.activityId)">查看详情</el-button>
            <el-button v-if="row.status === 1" type="danger" size="small" @click="handleCancel(row.id)">取消报名</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyEnrollments, cancelEnrollment } from '@/api/enrollment'

const router = useRouter()
const activities = ref([])

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已取消' }
  return texts[status]
}

const loadActivities = async () => {
  const res = await getMyEnrollments()
  activities.value = res.data
}

const viewDetail = (id) => {
  router.push(`/volunteer/activities/${id}`)
}

const handleCancel = async (id) => {
  try {
    await ElMessageBox.confirm('确定要取消报名吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await cancelEnrollment(id)
    ElMessage.success('取消成功')
    loadActivities()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadActivities()
})
</script>

