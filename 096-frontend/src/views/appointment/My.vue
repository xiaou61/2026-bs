<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="预约状态" clearable style="width: 160px">
          <el-option label="待支付" :value="0" />
          <el-option label="已支付" :value="1" />
          <el-option label="已取消" :value="2" />
          <el-option label="已完成" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="appointmentNo" label="挂号单号" min-width="180" />
        <el-table-column prop="doctorName" label="医生" />
        <el-table-column prop="departmentName" label="科室" />
        <el-table-column prop="appointmentDate" label="就诊日期" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="amount" label="费用" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 3 ? 'success' : row.status === 2 ? 'info' : row.status === 1 ? 'warning' : 'danger'">
              {{ statusMap[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="danger" v-if="row.status === 0 || row.status === 1" @click="handleCancel(row.id)">取消</el-button>
            <el-button link type="primary" v-if="row.status === 3" @click="openReview(row)">评价</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 16px"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="reviewVisible" title="医生评价" width="520px">
      <el-form :model="reviewForm" label-width="90px">
        <el-form-item label="评价星级">
          <el-rate v-model="reviewForm.rating" />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input v-model="reviewForm.content" type="textarea" :rows="4" placeholder="请输入本次就诊感受" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交评价</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addReview, cancelAppointment, getMyAppointments } from '../../api'

const statusMap = { 0: '待支付', 1: '已支付', 2: '已取消', 3: '已完成' }
const loading = ref(false)
const reviewVisible = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, status: undefined })
const reviewForm = reactive({ appointmentId: null, rating: 5, content: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyAppointments(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleCancel = async (id) => {
  await cancelAppointment(id)
  ElMessage.success('预约已取消')
  loadData()
}

const openReview = (row) => {
  Object.assign(reviewForm, { appointmentId: row.id, rating: 5, content: '' })
  reviewVisible.value = true
}

const submitReview = async () => {
  await addReview(reviewForm)
  ElMessage.success('评价成功')
  reviewVisible.value = false
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
