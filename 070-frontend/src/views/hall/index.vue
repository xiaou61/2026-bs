<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input-number v-model="query.cinemaId" :min="1" placeholder="影院ID" style="width: 180px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增影厅</el-button>
      </div>
      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="cinemaId" label="影院ID" width="100" />
        <el-table-column prop="name" label="影厅名称" />
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="seatRows" label="行数" width="90" />
        <el-table-column prop="seatCols" label="列数" width="90" />
        <el-table-column prop="totalSeats" label="总座位" width="100" />
        <el-table-column label="操作" width="170">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="mt16" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" @current-change="loadData" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="影厅信息" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="影院ID"><el-input-number v-model="form.cinemaId" :min="1" /></el-form-item>
        <el-form-item label="影厅名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.type" placeholder="NORMAL/IMAX/VIP" /></el-form-item>
        <el-form-item label="座位行数"><el-input-number v-model="form.seatRows" :min="1" /></el-form-item>
        <el-form-item label="座位列数"><el-input-number v-model="form.seatCols" :min="1" /></el-form-item>
        <el-form-item label="总座位"><el-input-number v-model="form.totalSeats" :min="1" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :label="1">启用</el-radio><el-radio :label="0">停用</el-radio></el-radio-group></el-form-item>
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
import { deleteHall, getHallList, saveHall } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, cinemaId: null })
const form = reactive({ id: null, cinemaId: 1, name: '', type: 'NORMAL', seatRows: 8, seatCols: 10, totalSeats: 80, status: 1 })

const loadData = async () => {
  loading.value = true
  try {
    const params = { ...query }
    if (!params.cinemaId) {
      delete params.cinemaId
    }
    const res = await getHallList(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, cinemaId: 1, name: '', type: 'NORMAL', seatRows: 8, seatCols: 10, totalSeats: 80, status: 1 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveHall(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteHall(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 10px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
