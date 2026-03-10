<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.appointmentNo" placeholder="预约单号" clearable style="width: 220px" />
        <el-select v-model="query.elderId" placeholder="老人" clearable style="width: 180px">
          <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="待检" :value="0" />
          <el-option label="检查中" :value="1" />
          <el-option label="已完成" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="appointmentNo" label="预约单号" width="150" />
        <el-table-column prop="elderId" label="老人">
          <template #default="{ row }">{{ elderName(row.elderId) }}</template>
        </el-table-column>
        <el-table-column prop="packageId" label="套餐">
          <template #default="{ row }">{{ packageName(row.packageId) }}</template>
        </el-table-column>
        <el-table-column prop="appointmentDate" label="预约日期" width="120" />
        <el-table-column prop="slotTime" label="时段" width="100" />
        <el-table-column prop="source" label="来源" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="warning" @click="markFinish(row)">完成</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑预约' : '新增预约'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="预约单号" prop="appointmentNo">
          <el-input v-model="form.appointmentNo" placeholder="不填则自动生成" />
        </el-form-item>
        <el-form-item label="老人" prop="elderId">
          <el-select v-model="form.elderId" style="width: 100%">
            <el-option v-for="item in elderOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="套餐" prop="packageId">
          <el-select v-model="form.packageId" style="width: 100%">
            <el-option v-for="item in packageOptions" :key="item.id" :label="item.packageName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约日期" prop="appointmentDate">
          <el-date-picker v-model="form.appointmentDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="时段" prop="slotTime">
          <el-select v-model="form.slotTime" style="width: 100%">
            <el-option label="上午" value="上午" />
            <el-option label="下午" value="下午" />
          </el-select>
        </el-form-item>
        <el-form-item label="来源" prop="source">
          <el-input v-model="form.source" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">待检</el-radio>
            <el-radio :label="1">检查中</el-radio>
            <el-radio :label="2">已完成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  addAppointment,
  deleteAppointment,
  getAppointmentList,
  getElderAll,
  getPackageAll,
  updateAppointment,
  updateAppointmentStatus
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const elderOptions = ref([])
const packageOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  appointmentNo: '',
  elderId: null,
  status: null
})

const form = reactive({
  id: null,
  appointmentNo: '',
  elderId: null,
  packageId: null,
  appointmentDate: '',
  slotTime: '上午',
  source: '线下窗口',
  status: 0,
  remark: ''
})

const rules = {
  elderId: [{ required: true, message: '请选择老人', trigger: 'change' }],
  packageId: [{ required: true, message: '请选择套餐', trigger: 'change' }],
  appointmentDate: [{ required: true, message: '请选择日期', trigger: 'change' }]
}

const elderName = (id) => {
  const target = elderOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const packageName = (id) => {
  const target = packageOptions.value.find(item => item.id === id)
  return target ? target.packageName : '-'
}

const statusText = (status) => {
  if (status === 2) return '已完成'
  if (status === 1) return '检查中'
  return '待检'
}

const statusType = (status) => {
  if (status === 2) return 'success'
  if (status === 1) return 'warning'
  return 'info'
}

const loadOptions = async () => {
  const [elderRes, packageRes] = await Promise.all([getElderAll(), getPackageAll()])
  elderOptions.value = elderRes.data || []
  packageOptions.value = packageRes.data || []
}

const resetForm = () => {
  form.id = null
  form.appointmentNo = ''
  form.elderId = null
  form.packageId = null
  form.appointmentDate = ''
  form.slotTime = '上午'
  form.source = '线下窗口'
  form.status = 0
  form.remark = ''
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAppointmentList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateAppointment(form)
  } else {
    await addAppointment(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const markFinish = async (row) => {
  await updateAppointmentStatus(row.id, 2)
  ElMessage.success('已标记完成')
  loadData()
}

const handleDelete = async (id) => {
  await deleteAppointment(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}
</style>
