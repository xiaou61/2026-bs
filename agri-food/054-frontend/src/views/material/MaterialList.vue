<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="名称"><el-input v-model="queryParams.name" placeholder="请输入名称" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <div style="margin-bottom: 15px">
        <el-button type="primary" @click="handleAdd">新增农资</el-button>
        <el-button type="success" @click="handleStockIn">入库</el-button>
        <el-button type="warning" @click="handleStockOut">出库</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="category" label="分类" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="currentStock" label="库存" width="100">
          <template #default="{ row }"><span :style="{ color: row.currentStock <= row.warningStock ? 'red' : '' }">{{ row.currentStock }}</span></template>
        </el-table-column>
        <el-table-column prop="warningStock" label="预警值" width="100" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="total, prev, pager, next" @change="loadData" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="规格"><el-input v-model="form.specification" /></el-form-item>
        <el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item>
        <el-form-item label="预警值"><el-input-number v-model="form.warningStock" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
    <el-dialog v-model="stockDialogVisible" :title="stockDialogTitle" width="400px">
      <el-form :model="stockForm" ref="stockFormRef" label-width="80px">
        <el-form-item label="农资ID"><el-input-number v-model="stockForm.materialId" :min="1" /></el-form-item>
        <el-form-item label="数量"><el-input-number v-model="stockForm.quantity" :min="1" /></el-form-item>
        <el-form-item label="单价" v-if="stockType === 'in'"><el-input-number v-model="stockForm.unitPrice" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="用途"><el-input v-model="stockForm.purpose" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="stockDialogVisible = false">取消</el-button><el-button type="primary" @click="submitStock">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMaterialPage, addMaterial, updateMaterial, deleteMaterial, stockIn, stockOut } from '@/api/material'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), dialogTitle = ref(''), formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, name: '', category: '' }), form = ref({})
const stockDialogVisible = ref(false), stockDialogTitle = ref(''), stockType = ref('in'), stockForm = ref({}), stockFormRef = ref()
const loadData = async () => { loading.value = true; try { const res = await getMaterialPage(queryParams); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { dialogTitle.value = '新增农资'; form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑农资'; form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deleteMaterial(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => { form.value.id ? await updateMaterial(form.value) : await addMaterial(form.value); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleStockIn = () => { stockDialogTitle.value = '入库'; stockType.value = 'in'; stockForm.value = {}; stockDialogVisible.value = true }
const handleStockOut = () => { stockDialogTitle.value = '出库'; stockType.value = 'out'; stockForm.value = {}; stockDialogVisible.value = true }
const submitStock = async () => { stockType.value === 'in' ? await stockIn(stockForm.value) : await stockOut(stockForm.value); ElMessage.success('操作成功'); stockDialogVisible.value = false; loadData() }
onMounted(() => loadData())
</script>