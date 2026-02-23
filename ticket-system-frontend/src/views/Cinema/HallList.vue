<template>
  <div class="hall-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>影厅管理</span>
          <el-button v-if="isAdmin" type="primary" @click="handleAdd">新增影厅</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="影院">
          <el-select v-model="searchForm.cinemaId" placeholder="请选择影院" clearable>
            <el-option v-for="cinema in cinemaList" :key="cinema.id" :label="cinema.name" :value="cinema.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="影厅名称">
          <el-input v-model="searchForm.name" placeholder="请输入影厅名称" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="cinemaName" label="所属影院" width="200" />
        <el-table-column prop="name" label="影厅名称" width="150" />
        <el-table-column prop="seatRows" label="座位行数" width="100" />
        <el-table-column prop="seatCols" label="座位列数" width="100" />
        <el-table-column prop="totalSeats" label="总座位数" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column v-if="isAdmin" label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="formData" label-width="100px">
        <el-form-item label="所属影院">
          <el-select v-model="formData.cinemaId" placeholder="请选择影院">
            <el-option v-for="cinema in cinemaList" :key="cinema.id" :label="cinema.name" :value="cinema.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="影厅名称">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="座位行数">
          <el-input-number v-model="formData.seatRows" :min="1" :max="30" />
        </el-form-item>
        <el-form-item label="座位列数">
          <el-input-number v-model="formData.seatCols" :min="1" :max="30" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { hallApi, cinemaApi } from '@/api'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.userInfo?.role === 'admin')

const cinemaList = ref([])

const searchForm = reactive({
  cinemaId: null,
  name: ''
})

const tableData = ref([])

const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  cinemaId: null,
  name: '',
  seatRows: 10,
  seatCols: 15
})

const loadCinemaList = async () => {
  try {
    const res = await cinemaApi.getAllCinemas()
    cinemaList.value = res.data
  } catch (error) {
    ElMessage.error('加载影院列表失败')
  }
}

const loadData = async () => {
  try {
    const res = await hallApi.getHallList({
      current: pagination.current,
      size: pagination.size,
      ...searchForm
    })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleSearch = () => {
  pagination.current = 1
  loadData()
}

const handleReset = () => {
  searchForm.cinemaId = null
  searchForm.name = ''
  pagination.current = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增影厅'
  isEdit.value = false
  Object.assign(formData, {
    id: null,
    cinemaId: null,
    name: '',
    seatRows: 10,
    seatCols: 15
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑影厅'
  isEdit.value = true
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该影厅吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await hallApi.deleteHall(row.id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

const handleSubmit = async () => {
  try {
    if (isEdit.value) {
      await hallApi.updateHall(formData.id, formData)
    } else {
      await hallApi.addHall(formData)
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadCinemaList()
  loadData()
})
</script>

<style scoped>
.hall-list-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.search-form {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>
