<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="挂号单号/患者/医生" clearable />
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
        <el-table-column prop="patientName" label="患者" />
        <el-table-column prop="doctorName" label="医生" />
        <el-table-column prop="departmentName" label="科室" />
        <el-table-column prop="appointmentDate" label="日期" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="amount" label="金额" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">{{ statusMap[row.status] }}</template>
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
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getAppointmentPage } from '../../api'

const statusMap = { 0: '待支付', 1: '已支付', 2: '已取消', 3: '已完成' }
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, keyword: '', status: undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAppointmentPage(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
