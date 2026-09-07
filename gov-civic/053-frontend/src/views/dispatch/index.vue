<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.disasterId" placeholder="选择灾情" style="width: 200px" clearable>
          <el-option v-for="item in disasters" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
          <el-option label="待发货" :value="0" />
          <el-option label="运输中" :value="1" />
          <el-option label="已送达" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增调度</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="dispatchNo" label="调度单号" width="180" />
        <el-table-column prop="disasterName" label="目标灾情" />
        <el-table-column prop="warehouseName" label="出库仓库" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'info' : row.status === 1 ? 'warning' : 'success'">
              {{ ['待发货', '运输中', '已送达'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="success" link @click="handleStatus(row.id, row.status + 1)" v-if="row.status < 2">更新状态</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button type="danger" link>删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑调度' : '新增调度'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="目标灾情" prop="disasterId">
          <el-select v-model="form.disasterId" style="width: 100%">
            <el-option v-for="item in disasters" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="出库仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" style="width: 100%">
            <el-option v-for="item in warehouses" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" /></el-form-item>
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
import { getDispatchList, addDispatch, updateDispatch, deleteDispatch, updateDispatchStatus, getDisasterSelect, getWarehouseSelect } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const disasters = ref([])
const warehouses = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, disasterId: null, status: null })
const form = reactive({ id: null, disasterId: null, warehouseId: null, remark: '' })
const rules = { disasterId: [{ required: true, message: '请选择灾情', trigger: 'change' }], warehouseId: [{ required: true, message: '请选择仓库', trigger: 'change' }] }

const loadOptions = async () => {
  const [dRes, wRes] = await Promise.all([getDisasterSelect(), getWarehouseSelect()])
  disasters.value = dRes.data
  warehouses.value = wRes.data
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDispatchList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, disasterId: null, warehouseId: null, remark: '' })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateDispatch(form)
    ElMessage.success('更新成功')
  } else {
    await addDispatch(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleStatus = async (id, status) => {
  await updateDispatchStatus(id, status)
  ElMessage.success('状态更新成功')
  loadData()
}

const handleDelete = async (id) => {
  await deleteDispatch(id)
  ElMessage.success('删除成功')
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
