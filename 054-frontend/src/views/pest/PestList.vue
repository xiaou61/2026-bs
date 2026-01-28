<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="名称">
          <el-input v-model="queryParams.name" placeholder="请输入名称" clearable />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="queryParams.type" placeholder="全部" clearable>
            <el-option label="虫害" :value="1" />
            <el-option label="病害" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-container">
      <div style="margin-bottom: 15px">
        <el-button type="primary" @click="handleAdd">新增病虫害</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.type === 1 ? 'warning' : 'danger'">{{ row.type === 1 ? '虫害' : '病害' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="symptoms" label="症状" show-overflow-tooltip />
        <el-table-column prop="prevention" label="防治方法" show-overflow-tooltip />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
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
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio :value="1">虫害</el-radio>
            <el-radio :value="2">病害</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="症状" prop="symptoms">
          <el-input v-model="form.symptoms" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="防治方法" prop="prevention">
          <el-input v-model="form.prevention" type="textarea" :rows="3" />
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
import { getPestPage, addPest, updatePest, deletePest } from '@/api/pest'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, name: '', type: null })
const form = ref({})
const rules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPestPage(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => { dialogTitle.value = '新增病虫害'; form.value = { type: 1 }; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑病虫害'; form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deletePest(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => {
  await formRef.value.validate()
  form.value.id ? await updatePest(form.value) : await addPest(form.value)
  ElMessage.success(form.value.id ? '修改成功' : '新增成功')
  dialogVisible.value = false
  loadData()
}

onMounted(() => loadData())
</script>