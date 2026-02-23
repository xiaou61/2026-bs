<template>
  <el-card>
    <div class="bar">
      <el-input v-model="query.slotNo" placeholder="车位编号" style="width: 180px" clearable />
      <el-select v-model="query.ownerId" placeholder="业主" style="width: 160px" clearable>
        <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
        <el-option label="空闲" :value="0" />
        <el-option label="已占用" :value="1" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button type="success" @click="openAdd">新增车位</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="slotNo" label="车位编号" width="130" />
      <el-table-column prop="location" label="位置" min-width="180" />
      <el-table-column prop="ownerName" label="业主" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已占用' : '空闲' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link @click="openEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="form.id ? '编辑车位' : '新增车位'" width="520px">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="车位编号" prop="slotNo"><el-input v-model="form.slotNo" maxlength="50" /></el-form-item>
      <el-form-item label="位置"><el-input v-model="form.location" maxlength="100" /></el-form-item>
      <el-form-item label="业主">
        <el-select v-model="form.ownerId" clearable style="width: 100%">
          <el-option v-for="item in owners" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addParking, deleteParking, getOwnerList, getParkingPage, updateParking } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const owners = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, slotNo: '', ownerId: null, status: null })
const form = reactive({})

const rules = {
  slotNo: [{ required: true, message: '请输入车位编号', trigger: 'blur' }]
}

const loadBase = async () => {
  const res = await getOwnerList()
  owners.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getParkingPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openAdd = async () => {
  await loadBase()
  Object.assign(form, { id: null, slotNo: '', location: '', ownerId: null, status: 0 })
  dialogVisible.value = true
}

const openEdit = async (row) => {
  await loadBase()
  Object.assign(form, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateParking(form)
  } else {
    await addParking(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteParking(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
