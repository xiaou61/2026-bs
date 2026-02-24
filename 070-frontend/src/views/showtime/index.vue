<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input-number v-model="query.movieId" :min="1" placeholder="影片ID" style="width: 180px" />
        <el-input-number v-model="query.cinemaId" :min="1" placeholder="影院ID" style="width: 180px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isAdmin" type="success" @click="handleAdd">新增场次</el-button>
      </div>
      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="movieId" label="影片ID" width="90" />
        <el-table-column prop="cinemaId" label="影院ID" width="90" />
        <el-table-column prop="hallId" label="影厅ID" width="90" />
        <el-table-column prop="startTime" label="开始时间" width="180" />
        <el-table-column prop="endTime" label="结束时间" width="180" />
        <el-table-column prop="price" label="票价" width="100" />
        <el-table-column prop="availableSeats" label="余票" width="90" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="170" v-if="isAdmin">
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

    <el-dialog v-model="dialogVisible" title="场次信息" width="560px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="影片ID"><el-input-number v-model="form.movieId" :min="1" /></el-form-item>
        <el-form-item label="影院ID"><el-input-number v-model="form.cinemaId" :min="1" /></el-form-item>
        <el-form-item label="影厅ID"><el-input-number v-model="form.hallId" :min="1" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="票价"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="状态"><el-input v-model="form.status" placeholder="SELLING/STOP/FINISHED" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../store/user'
import { deleteShowtime, getPublicShowtimeList, getShowtimeList, saveShowtime } from '../../api'

const userStore = useUserStore()
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, movieId: null, cinemaId: null })
const form = reactive({ id: null, movieId: 1, cinemaId: 1, hallId: 1, startTime: '', endTime: '', price: 50, status: 'SELLING' })

const loadData = async () => {
  loading.value = true
  try {
    const params = { ...query }
    if (!params.movieId) delete params.movieId
    if (!params.cinemaId) delete params.cinemaId
    const api = isAdmin.value ? getShowtimeList : getPublicShowtimeList
    const res = await api(params)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, movieId: 1, cinemaId: 1, hallId: 1, startTime: '', endTime: '', price: 50, status: 'SELLING' })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveShowtime(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteShowtime(id)
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
