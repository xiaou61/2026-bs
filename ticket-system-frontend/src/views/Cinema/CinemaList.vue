<template>
  <div class="cinema-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>影院管理</span>
          <el-button v-if="isAdmin" type="primary" @click="handleAdd">新增影院</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="影院名称">
          <el-input v-model="searchForm.name" placeholder="请输入影院名称" clearable />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="searchForm.address" placeholder="请输入地址" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="影院名称" width="200" />
        <el-table-column prop="address" label="地址" width="250" />
        <el-table-column prop="phone" label="联系电话" width="150" />
        <el-table-column prop="businessHours" label="营业时间" width="180" />
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
        <el-form-item label="影院名称">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="formData.address" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="formData.phone" />
        </el-form-item>
        <el-form-item label="营业时间">
          <el-input v-model="formData.businessHours" placeholder="例如：09:00-22:00" />
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
import { cinemaApi } from '@/api'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.userInfo?.role === 'admin')

const searchForm = reactive({
  name: '',
  address: ''
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
  name: '',
  address: '',
  phone: '',
  businessHours: ''
})

const loadData = async () => {
  try {
    const res = await cinemaApi.getCinemaList({
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
  searchForm.name = ''
  searchForm.address = ''
  pagination.current = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增影院'
  isEdit.value = false
  Object.assign(formData, {
    id: null,
    name: '',
    address: '',
    phone: '',
    businessHours: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑影院'
  isEdit.value = true
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该影院吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await cinemaApi.deleteCinema(row.id)
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
      await cinemaApi.updateCinema(formData.id, formData)
    } else {
      await cinemaApi.addCinema(formData)
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.cinema-list-container {
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
