<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.machineId" placeholder="设备" clearable>
          <el-option v-for="item in machineOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option label="NORMAL" value="NORMAL" />
          <el-option label="SOLD_OUT" value="SOLD_OUT" />
          <el-option label="DISABLED" value="DISABLED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增货道</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="machineName" label="设备名称" min-width="160" />
        <el-table-column prop="slotNo" label="货道编号" min-width="120" />
        <el-table-column prop="productName" label="绑定商品" min-width="160" />
        <el-table-column prop="capacity" label="容量" min-width="90" />
        <el-table-column prop="currentStock" label="当前库存" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column prop="lastSyncTime" label="最近同步" min-width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑货道' : '新增货道'" width="620px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="设备" prop="machineId">
          <el-select v-model="form.machineId" style="width: 100%">
            <el-option v-for="item in machineOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="货道编号" prop="slotNo">
          <el-input v-model="form.slotNo" />
        </el-form-item>
        <el-form-item label="绑定商品" prop="productId">
          <el-select v-model="form.productId" style="width: 100%">
            <el-option v-for="item in productOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="容量">
          <el-input-number v-model="form.capacity" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="当前库存">
          <el-input-number v-model="form.currentStock" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="NORMAL" value="NORMAL" />
            <el-option label="SOLD_OUT" value="SOLD_OUT" />
            <el-option label="DISABLED" value="DISABLED" />
          </el-select>
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
import { deleteSlot, getMachineOptions, getProductOptions, getSlotList, saveSlot } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const machineOptions = ref([])
const productOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  machineId: null,
  status: ''
})
const form = reactive({
  id: null,
  machineId: null,
  slotNo: '',
  productId: null,
  capacity: 10,
  currentStock: 0,
  status: 'NORMAL'
})
const rules = {
  machineId: [{ required: true, message: '请选择设备', trigger: 'change' }],
  slotNo: [{ required: true, message: '请输入货道编号', trigger: 'blur' }],
  productId: [{ required: true, message: '请选择商品', trigger: 'change' }]
}

const loadOptions = async () => {
  const [a, b] = await Promise.all([getMachineOptions(), getProductOptions()])
  machineOptions.value = a.data || []
  productOptions.value = b.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getSlotList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    machineId: null,
    slotNo: '',
    productId: null,
    capacity: 10,
    currentStock: 0,
    status: 'NORMAL'
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveSlot(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteSlot(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
