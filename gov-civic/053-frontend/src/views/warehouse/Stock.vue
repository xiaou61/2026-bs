<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.warehouseId" placeholder="选择仓库" style="width: 200px" clearable>
          <el-option v-for="item in warehouses" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.materialId" placeholder="选择物资" style="width: 200px" clearable>
          <el-option v-for="item in materials" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">入库</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="warehouseName" label="仓库" width="150" />
        <el-table-column prop="materialName" label="物资名称" />
        <el-table-column prop="quantity" label="库存数量" width="120" />
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">调整</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '调整库存' : '物资入库'" width="450px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" style="width: 100%" :disabled="!!form.id">
            <el-option v-for="item in warehouses" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="物资" prop="materialId">
          <el-select v-model="form.materialId" style="width: 100%" :disabled="!!form.id">
            <el-option v-for="item in materials" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity"><el-input-number v-model="form.quantity" :min="0" style="width: 100%" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStockList, addStock, updateStock, getWarehouseSelect, getMaterialSelect } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const warehouses = ref([])
const materials = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, warehouseId: null, materialId: null })
const form = reactive({ id: null, warehouseId: null, materialId: null, quantity: 0 })
const rules = { warehouseId: [{ required: true, message: '请选择仓库', trigger: 'change' }], materialId: [{ required: true, message: '请选择物资', trigger: 'change' }], quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }] }

const loadOptions = async () => {
  const [wRes, mRes] = await Promise.all([getWarehouseSelect(), getMaterialSelect()])
  warehouses.value = wRes.data
  materials.value = mRes.data
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getStockList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, warehouseId: null, materialId: null, quantity: 0 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateStock(form)
    ElMessage.success('调整成功')
  } else {
    await addStock(form)
    ElMessage.success('入库成功')
  }
  dialogVisible.value = false
  loadData()
}

onMounted(() => {
  loadOptions()
  loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
