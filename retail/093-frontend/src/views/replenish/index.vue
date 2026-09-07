<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.machineId" placeholder="设备" clearable>
          <el-option v-for="item in machineOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">登记补货</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="replenishNo" label="补货单号" min-width="160" />
        <el-table-column prop="machineName" label="设备" min-width="160" />
        <el-table-column prop="slotNo" label="货道" min-width="100" />
        <el-table-column prop="productName" label="商品" min-width="160" />
        <el-table-column prop="quantity" label="补货数量" min-width="100" />
        <el-table-column prop="beforeStock" label="补货前" min-width="90" />
        <el-table-column prop="afterStock" label="补货后" min-width="90" />
        <el-table-column prop="operatorName" label="补货员" min-width="120" />
        <el-table-column prop="createTime" label="补货时间" min-width="180" />
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="登记补货" width="560px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="90px">
        <el-form-item label="设备" prop="machineId">
          <el-select v-model="form.machineId" style="width: 100%" @change="handleMachineChange">
            <el-option v-for="item in machineOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="货道" prop="slotId">
          <el-select v-model="form.slotId" style="width: 100%">
            <el-option v-for="item in slotOptions" :key="item.id" :label="`${item.slotNo} / ${item.productName}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="补货数量" prop="quantity">
          <el-input-number v-model="form.quantity" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getMachineOptions, getMachineSlotList, getReplenishList, saveReplenish } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const machineOptions = ref([])
const slotOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  machineId: null
})
const form = reactive({
  machineId: null,
  slotId: null,
  quantity: 1,
  remark: ''
})
const rules = {
  machineId: [{ required: true, message: '请选择设备', trigger: 'change' }],
  slotId: [{ required: true, message: '请选择货道', trigger: 'change' }],
  quantity: [{ required: true, message: '请输入补货数量', trigger: 'change' }]
}

const loadOptions = async () => {
  const res = await getMachineOptions()
  machineOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getReplenishList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleMachineChange = async machineId => {
  form.slotId = null
  if (!machineId) {
    slotOptions.value = []
    return
  }
  const res = await getMachineSlotList(machineId)
  slotOptions.value = res.data || []
}

const handleAdd = () => {
  Object.assign(form, {
    machineId: null,
    slotId: null,
    quantity: 1,
    remark: ''
  })
  slotOptions.value = []
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await saveReplenish(form)
  ElMessage.success('补货登记成功')
  dialogVisible.value = false
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
