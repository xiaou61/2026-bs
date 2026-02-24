<template>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>考勤管理</span>
        <div v-if="userStore.user?.employeeId">
          <el-button type="success" @click="handleClockIn" :disabled="todayRecord?.clockIn">上班打卡</el-button>
          <el-button type="warning" @click="handleClockOut" :disabled="!todayRecord?.clockIn || todayRecord?.clockOut">下班打卡</el-button>
        </div>
      </div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="员工姓名"><el-input v-model="query.employeeName" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="日期范围"><el-date-picker v-model="dateRange" type="daterange" value-format="YYYY-MM-DD" start-placeholder="开始" end-placeholder="结束" /></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="正常" value="normal" /><el-option label="迟到" value="late" /><el-option label="早退" value="early" /><el-option label="缺勤" value="absent" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="empNo" label="工号" width="100" />
      <el-table-column prop="employeeName" label="姓名" width="100" />
      <el-table-column prop="date" label="日期" width="120"><template #default="{ row }">{{ row.date?.substring(0, 10) }}</template></el-table-column>
      <el-table-column prop="clockIn" label="上班时间" width="100"><template #default="{ row }">{{ row.clockIn?.substring(11, 19) }}</template></el-table-column>
      <el-table-column prop="clockOut" label="下班时间" width="100"><template #default="{ row }">{{ row.clockOut?.substring(11, 19) }}</template></el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="{ normal: 'success', late: 'warning', early: 'warning', absent: 'danger' }[row.status]">{{ { normal: '正常', late: '迟到', early: '早退', absent: '缺勤' }[row.status] }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" />
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
  </el-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { attendanceApi } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const query = ref({ employeeName: '', startDate: '', endDate: '', status: '', pageNum: 1, pageSize: 10 })
const dateRange = ref([])
const list = ref([])
const total = ref(0)
const todayRecord = ref(null)

watch(dateRange, (val) => { query.value.startDate = val?.[0] || ''; query.value.endDate = val?.[1] || '' })

const loadData = async () => { const res = await attendanceApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadToday = async () => { if (userStore.user?.employeeId) { todayRecord.value = await attendanceApi.getToday() } }
const handleClockIn = async () => { await attendanceApi.clockIn(); ElMessage.success('上班打卡成功'); loadToday(); loadData() }
const handleClockOut = async () => { await attendanceApi.clockOut(); ElMessage.success('下班打卡成功'); loadToday(); loadData() }
onMounted(() => { loadData(); loadToday() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
