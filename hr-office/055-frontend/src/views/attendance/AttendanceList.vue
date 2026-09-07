<template>
  <div class="page-container">
    <el-card>
      <div class="toolbar">
        <div class="search">
          <el-date-picker v-model="query.month" type="month" placeholder="选择月份" format="YYYY-MM" value-format="YYYY-MM" style="width: 150px;" />
          <el-input v-model="query.keyword" placeholder="姓名" clearable style="width: 150px;" v-if="!isEmployee" />
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="realName" label="姓名" width="100" v-if="!isEmployee" />
        <el-table-column prop="attendanceDate" label="日期" width="120" />
        <el-table-column prop="clockInTime" label="签到时间" width="180" />
        <el-table-column prop="clockOutTime" label="签退时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" />
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px;" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { getAttendanceList, getMyAttendance } from '../../api'
import { useUserStore } from '../../store/user'
import dayjs from 'dayjs'

const userStore = useUserStore()
const isEmployee = computed(() => userStore.isEmployee)
const loading = ref(false)
const list = ref([])
const total = ref(0)

const query = reactive({
  month: dayjs().format('YYYY-MM'),
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

const getStatusText = (status) => {
  const map = { 0: '正常', 1: '迟到', 2: '早退', 3: '缺卡', 4: '迟到+早退' }
  return map[status] || '未知'
}

const getStatusType = (status) => {
  const map = { 0: 'success', 1: 'warning', 2: 'warning', 3: 'danger', 4: 'danger' }
  return map[status] || 'info'
}

const loadData = async () => {
  loading.value = true
  try {
    const api = isEmployee.value ? getMyAttendance : getAttendanceList
    const res = await api(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}
.toolbar {
  margin-bottom: 15px;
}
.search {
  display: flex;
  gap: 10px;
}
</style>
