<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="全部" clearable>
            <el-option label="待执行" :value="0" />
            <el-option label="执行中" :value="1" />
            <el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div style="margin-bottom: 15px">
        <el-button type="primary" @click="handleAdd">新增任务</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="taskName" label="任务名称" />
        <el-table-column prop="taskType" label="任务类型" />
        <el-table-column prop="planDate" label="计划日期" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="['info', 'warning', 'success'][row.status]">
              {{ ['待执行', '执行中', '已完成'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" v-if="row.status < 2" @click="handleStatus(row)">
              {{ row.status === 0 ? '开始' : '完成' }}
            </el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize"
          :total="total" layout="total, prev, pager, next" @change="loadData" />
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" />
        </el-form-item>
        <el-form-item label="计划ID" prop="planId">
          <el-input-number v-model="form.planId" :min="1" />
        </el-form-item>
        <el-form-item label="任务类型" prop="taskType">
          <el-input v-model="form.taskType" />
        </el-form-item>
        <el-form-item label="计划日期" prop="planDate">
          <el-date-picker v-model="form.planDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getTaskPage, addTask, updateTask, deleteTask, updateTaskStatus } from '@/api/plan'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, status: null })
const form = ref({})
const rules = { taskName: [{ required: true, message: '请输入任务名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTaskPage(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => { dialogTitle.value = '新增任务'; form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑任务'; form.value = { ...row }; dialogVisible.value = true }
const handleStatus = async (row) => { await updateTaskStatus(row.id, row.status + 1); ElMessage.success('操作成功'); loadData() }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deleteTask(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => {
  await formRef.value.validate()
  form.value.id ? await updateTask(form.value) : await addTask(form.value)
  ElMessage.success(form.value.id ? '修改成功' : '新增成功')
  dialogVisible.value = false
  loadData()
}

onMounted(() => loadData())
</script>