<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="计划名称">
          <el-input v-model="queryParams.planName" placeholder="请输入计划名称" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="全部" clearable>
            <el-option label="计划中" :value="0" />
            <el-option label="执行中" :value="1" />
            <el-option label="已完成" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div style="margin-bottom: 15px">
        <el-button type="primary" @click="handleAdd">新增计划</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="planName" label="计划名称" />
        <el-table-column prop="cropName" label="作物" />
        <el-table-column prop="area" label="种植面积" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="['info', 'warning', 'success'][row.status]">
              {{ ['计划中', '执行中', '已完成'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" v-if="row.status < 2" @click="handleStatus(row)">
              {{ row.status === 0 ? '开始执行' : '完成' }}
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="计划名称" prop="planName">
          <el-input v-model="form.planName" />
        </el-form-item>
        <el-form-item label="作物ID" prop="cropId">
          <el-input-number v-model="form.cropId" :min="1" />
        </el-form-item>
        <el-form-item label="种植面积" prop="area">
          <el-input v-model="form.area" placeholder="如: 100亩" />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" />
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
import { getPlanPage, addPlan, updatePlan, deletePlan, updatePlanStatus } from '@/api/plan'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, planName: '', status: null })
const form = ref({})
const rules = {
  planName: [{ required: true, message: '请输入计划名称', trigger: 'blur' }],
  cropId: [{ required: true, message: '请输入作物ID', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPlanPage(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetQuery = () => {
  queryParams.planName = ''
  queryParams.status = null
  queryParams.pageNum = 1
  loadData()
}

const handleAdd = () => { dialogTitle.value = '新增计划'; form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑计划'; form.value = { ...row }; dialogVisible.value = true }
const handleStatus = async (row) => { await updatePlanStatus(row.id, row.status + 1); ElMessage.success('操作成功'); loadData() }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deletePlan(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => {
  await formRef.value.validate()
  form.value.id ? await updatePlan(form.value) : await addPlan(form.value)
  ElMessage.success(form.value.id ? '修改成功' : '新增成功')
  dialogVisible.value = false
  loadData()
}

onMounted(() => loadData())
</script>