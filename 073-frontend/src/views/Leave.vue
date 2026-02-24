<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>请假管理</span><el-button type="primary" @click="handleAdd" v-if="userStore.user?.employeeId">申请请假</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="员工姓名"><el-input v-model="query.employeeName" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="请假类型"><el-select v-model="query.type" placeholder="请选择" clearable><el-option label="年假" value="annual" /><el-option label="病假" value="sick" /><el-option label="事假" value="personal" /></el-select></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="待审批" value="pending" /><el-option label="已通过" value="approved" /><el-option label="已拒绝" value="rejected" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="employeeName" label="员工" width="100" />
      <el-table-column prop="type" label="类型" width="80"><template #default="{ row }">{{ { annual: '年假', sick: '病假', personal: '事假' }[row.type] }}</template></el-table-column>
      <el-table-column label="请假时间"><template #default="{ row }">{{ row.startDate?.substring(0, 10) }} 至 {{ row.endDate?.substring(0, 10) }}</template></el-table-column>
      <el-table-column prop="days" label="天数" width="80" />
      <el-table-column prop="reason" label="事由" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="{ pending: 'warning', approved: 'success', rejected: 'danger' }[row.status]">{{ { pending: '待审批', approved: '已通过', rejected: '已拒绝' }[row.status] }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="approverName" label="审批人" width="100" />
      <el-table-column label="操作" width="150" v-if="userStore.user?.role !== 'employee'">
        <template #default="{ row }">
          <template v-if="row.status === 'pending'">
            <el-button type="success" link @click="handleApprove(row.id, 'approved')">通过</el-button>
            <el-button type="danger" link @click="handleApprove(row.id, 'rejected')">拒绝</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" title="申请请假" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="请假类型" prop="type"><el-select v-model="form.type" style="width: 100%"><el-option label="年假" value="annual" /><el-option label="病假" value="sick" /><el-option label="事假" value="personal" /></el-select></el-form-item>
        <el-form-item label="开始日期" prop="startDate"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="结束日期" prop="endDate"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="天数" prop="days"><el-input-number v-model="form.days" :min="0.5" :step="0.5" /></el-form-item>
        <el-form-item label="事由" prop="reason"><el-input v-model="form.reason" type="textarea" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">提交</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { leaveApi } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const query = ref({ employeeName: '', type: '', status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const rules = { type: [{ required: true, message: '请选择类型', trigger: 'change' }], startDate: [{ required: true, message: '请选择开始日期', trigger: 'change' }], endDate: [{ required: true, message: '请选择结束日期', trigger: 'change' }], days: [{ required: true, message: '请输入天数', trigger: 'blur' }], reason: [{ required: true, message: '请输入事由', trigger: 'blur' }] }

const loadData = async () => { const res = await leaveApi.getList(query.value); list.value = res.list; total.value = res.total }
const handleAdd = () => { form.value = { employeeId: userStore.user?.employeeId }; dialogVisible.value = true }
const handleApprove = async (id, status) => { await ElMessageBox.confirm(`确定${status === 'approved' ? '通过' : '拒绝'}?`, '提示'); await leaveApi.approve({ id, status }); ElMessage.success('操作成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); await leaveApi.add(form.value); ElMessage.success('提交成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
