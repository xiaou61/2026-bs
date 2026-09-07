<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 160px">
          <el-option label="待支付" :value="0" />
          <el-option label="已支付" :value="1" />
          <el-option label="已取消" :value="2" />
          <el-option label="已完成" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="appointmentNo" label="挂号单号" min-width="180" />
        <el-table-column prop="patientName" label="患者姓名" />
        <el-table-column prop="contactPhone" label="联系电话" />
        <el-table-column prop="appointmentDate" label="就诊日期" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="remark" label="备注" min-width="180" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">{{ statusMap[row.status] }}</template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" link type="primary" @click="handleFinish(row.id)">完成接诊</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { finishAppointment, getDoctorAppointments } from '../../api'

const statusMap = { 0: '待支付', 1: '已支付', 2: '已取消', 3: '已完成' }
const loading = ref(false)
const tableData = ref([])
const query = reactive({ status: undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDoctorAppointments(query)
    tableData.value = res.data.list || []
  } finally {
    loading.value = false
  }
}

const handleFinish = async (id) => {
  await finishAppointment(id)
  ElMessage.success('已完成接诊')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
