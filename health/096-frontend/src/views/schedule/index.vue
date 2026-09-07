<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.departmentId" placeholder="科室" clearable style="width: 160px">
          <el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.doctorId" placeholder="医生" clearable style="width: 160px">
          <el-option v-for="item in doctors" :key="item.id" :label="item.doctorName" :value="item.id" />
        </el-select>
        <el-date-picker v-model="query.scheduleDate" type="date" value-format="YYYY-MM-DD" placeholder="排班日期" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增排班</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="departmentName" label="科室" />
        <el-table-column prop="doctorName" label="医生" />
        <el-table-column prop="scheduleDate" label="日期" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="totalSource" label="总号源" />
        <el-table-column prop="remainingSource" label="剩余号源" />
        <el-table-column prop="fee" label="挂号费" />
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该排班吗？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑排班' : '新增排班'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="医生" prop="doctorId">
          <el-select v-model="form.doctorId" style="width: 100%">
            <el-option v-for="item in doctors" :key="item.id" :label="`${item.doctorName}（${item.departmentName}）`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="排班日期" prop="scheduleDate"><el-date-picker v-model="form.scheduleDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="时段" prop="timeSlot">
          <el-select v-model="form.timeSlot" style="width: 100%">
            <el-option label="上午" value="上午" />
            <el-option label="下午" value="下午" />
            <el-option label="夜间" value="夜间" />
          </el-select>
        </el-form-item>
        <el-form-item label="总号源"><el-input-number v-model="form.totalSource" :min="1" /></el-form-item>
        <el-form-item label="挂号费"><el-input-number v-model="form.fee" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="诊室"><el-input v-model="form.clinicRoom" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">开放</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
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
import { addSchedule, deleteSchedule, getDepartmentEnabled, getDoctorEnabled, getSchedulePage, updateSchedule } from '../../api'

const loading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const departments = ref([])
const doctors = ref([])
const query = reactive({ departmentId: undefined, doctorId: undefined, scheduleDate: '' })
const form = reactive({ id: null, doctorId: null, scheduleDate: '', timeSlot: '上午', totalSource: 20, fee: 20, clinicRoom: '', status: 1 })

const rules = {
  doctorId: [{ required: true, message: '请选择医生', trigger: 'change' }],
  scheduleDate: [{ required: true, message: '请选择日期', trigger: 'change' }]
}

const loadBase = async () => {
  const [depRes, doctorRes] = await Promise.all([getDepartmentEnabled(), getDoctorEnabled()])
  departments.value = depRes.data || []
  doctors.value = doctorRes.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSchedulePage(query)
    tableData.value = res.data.list || []
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, doctorId: null, scheduleDate: '', timeSlot: '上午', totalSource: 20, fee: 20, clinicRoom: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateSchedule(form)
  } else {
    await addSchedule(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteSchedule(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
