<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.indicatorName" placeholder="指标名称" clearable style="width: 180px" />
        <el-input v-model="query.dimensionName" placeholder="维度" clearable style="width: 180px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增指标</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="indicatorName" label="指标名称" min-width="140" />
        <el-table-column prop="dimensionName" label="维度" min-width="120" />
        <el-table-column prop="weightValue" label="权重" width="90" />
        <el-table-column prop="sortNo" label="排序" width="80" />
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="val => handleStatusChange(row, val)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑指标' : '新增指标'" width="460px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="指标名称" prop="indicatorName"><el-input v-model="form.indicatorName" maxlength="50" /></el-form-item>
        <el-form-item label="维度" prop="dimensionName"><el-input v-model="form.dimensionName" maxlength="50" /></el-form-item>
        <el-form-item label="权重" prop="weightValue"><el-input-number v-model="form.weightValue" :min="0.01" :max="100" :precision="2" style="width: 100%" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sortNo" :min="1" :max="999" style="width: 100%" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
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
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addIndicator, deleteIndicator, getIndicatorPage, updateIndicator, updateIndicatorStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, indicatorName: '', dimensionName: '', status: null })
const form = reactive({ id: null, indicatorName: '', dimensionName: '', weightValue: 20, sortNo: 1, status: 1 })
const rules = {
  indicatorName: [{ required: true, message: '请输入指标名称', trigger: 'blur' }],
  dimensionName: [{ required: true, message: '请输入维度', trigger: 'blur' }],
  weightValue: [{ required: true, message: '请输入权重', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getIndicatorPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, indicatorName: '', dimensionName: '', weightValue: 20, sortNo: 1, status: 1 })
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
    await updateIndicator(form)
  } else {
    await addIndicator(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定删除指标 ${row.indicatorName} 吗？`, '提示', { type: 'warning' })
  await deleteIndicator(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleStatusChange = async (row, checked) => {
  await updateIndicatorStatus({ id: row.id, status: checked ? 1 : 0 })
  row.status = checked ? 1 : 0
  ElMessage.success('状态已更新')
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
