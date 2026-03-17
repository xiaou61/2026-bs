<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="学期名称">
          <el-input v-model="filters.termName" placeholder="请输入学期名称" clearable />
        </el-form-item>
        <el-form-item label="当前学期">
          <el-select v-model="filters.currentFlag" placeholder="全部" clearable style="width: 140px">
            <el-option v-for="item in termFlagOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" placeholder="全部状态" clearable style="width: 140px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">新增学期</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="termName" label="学期名称" min-width="220" />
        <el-table-column prop="startDate" label="开始日期" min-width="120" />
        <el-table-column prop="endDate" label="结束日期" min-width="120" />
        <el-table-column label="当前学期" width="110">
          <template #default="{ row }">
            <el-tag :type="row.currentFlag === 1 ? 'warning' : 'info'">{{ getOptionLabel(termFlagOptions, row.currentFlag) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ getOptionLabel(statusOptions, row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="170" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑学期' : '新增学期'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="学期名称" prop="termName">
          <el-input v-model="form.termName" placeholder="请输入学期名称" />
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="当前学期">
          <el-radio-group v-model="form.currentFlag">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addTerm, deleteTerm, getTermList, updateTerm } from '../../api'
import { getOptionLabel, resolvePage, statusOptions, termFlagOptions } from '../../utils'

const filters = reactive({
  termName: '',
  status: undefined,
  currentFlag: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  termName: '',
  startDate: '',
  endDate: '',
  currentFlag: 0,
  status: 1
})
const rules = {
  termName: [{ required: true, message: '请输入学期名称', trigger: 'blur' }]
}

const resetForm = () => {
  Object.assign(form, { id: null, termName: '', startDate: '', endDate: '', currentFlag: 0, status: 1 })
}

const loadData = async () => {
  const res = await getTermList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const resetFilters = () => {
  filters.termName = ''
  filters.status = undefined
  filters.currentFlag = undefined
  pageNum.value = 1
  loadData()
}

const openDialog = (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateTerm(form)
    ElMessage.success('学期更新成功')
  } else {
    await addTerm(form)
    ElMessage.success('学期新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('删除后不可恢复，确定继续吗？', '提示', { type: 'warning' })
  await deleteTerm(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.toolbar-actions,
.pagination {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.pagination {
  margin-top: 16px;
}
</style>
