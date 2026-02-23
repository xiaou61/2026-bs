<template>
  <div class="showtime-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>场次管理</span>
          <el-button v-if="isAdmin" type="primary" @click="handleAdd">新增场次</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="电影">
          <el-select v-model="searchForm.movieId" placeholder="请选择电影" clearable>
            <el-option v-for="movie in movieList" :key="movie.id" :label="movie.title" :value="movie.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="影院">
          <el-select v-model="searchForm.cinemaId" placeholder="请选择影院" clearable>
            <el-option v-for="cinema in cinemaList" :key="cinema.id" :label="cinema.name" :value="cinema.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="放映日期">
          <el-date-picker v-model="searchForm.showDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="movieTitle" label="电影" width="200" />
        <el-table-column prop="cinemaName" label="影院" width="180" />
        <el-table-column prop="hallName" label="影厅" width="120" />
        <el-table-column prop="showDate" label="放映日期" width="120" />
        <el-table-column prop="showTime" label="放映时间" width="100" />
        <el-table-column prop="price" label="票价" width="100">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="availableSeats" label="余票" width="80" />
        <el-table-column label="操作" fixed="right" width="250">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleViewSeats(row)">查看座位</el-button>
            <el-button v-if="isAdmin" type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button v-if="isAdmin" type="danger" size="small" @click="handleDelete(row)">删除</el-button>
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
        <el-form-item label="电影">
          <el-select v-model="formData.movieId" placeholder="请选择电影">
            <el-option v-for="movie in movieList" :key="movie.id" :label="movie.title" :value="movie.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="影院">
          <el-select v-model="formData.cinemaId" placeholder="请选择影院" @change="handleCinemaChange">
            <el-option v-for="cinema in cinemaList" :key="cinema.id" :label="cinema.name" :value="cinema.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="影厅">
          <el-select v-model="formData.hallId" placeholder="请选择影厅">
            <el-option v-for="hall in hallList" :key="hall.id" :label="hall.name" :value="hall.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="放映日期">
          <el-date-picker v-model="formData.showDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="放映时间">
          <el-time-picker v-model="formData.showTime" placeholder="选择时间" value-format="HH:mm" />
        </el-form-item>
        <el-form-item label="票价">
          <el-input-number v-model="formData.price" :min="0" :precision="2" />
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
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { showtimeApi, movieApi, cinemaApi, hallApi } from '@/api'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const isAdmin = computed(() => userStore.userInfo?.role === 'admin')

const movieList = ref([])
const cinemaList = ref([])
const hallList = ref([])

const searchForm = reactive({
  movieId: null,
  cinemaId: null,
  showDate: ''
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
  movieId: null,
  cinemaId: null,
  hallId: null,
  showDate: '',
  showTime: '',
  price: 50
})

const loadMovieList = async () => {
  try {
    const res = await movieApi.getAllMovies()
    movieList.value = res.data
  } catch (error) {
    ElMessage.error('加载电影列表失败')
  }
}

const loadCinemaList = async () => {
  try {
    const res = await cinemaApi.getAllCinemas()
    cinemaList.value = res.data
  } catch (error) {
    ElMessage.error('加载影院列表失败')
  }
}

const handleCinemaChange = async (cinemaId) => {
  formData.hallId = null
  if (cinemaId) {
    try {
      const res = await hallApi.getHallsByCinema(cinemaId)
      hallList.value = res.data
    } catch (error) {
      ElMessage.error('加载影厅列表失败')
    }
  } else {
    hallList.value = []
  }
}

const loadData = async () => {
  try {
    const res = await showtimeApi.getShowtimeList({
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
  searchForm.movieId = null
  searchForm.cinemaId = null
  searchForm.showDate = ''
  pagination.current = 1
  loadData()
}

const handleAdd = () => {
  dialogTitle.value = '新增场次'
  isEdit.value = false
  Object.assign(formData, {
    id: null,
    movieId: null,
    cinemaId: null,
    hallId: null,
    showDate: '',
    showTime: '',
    price: 50
  })
  hallList.value = []
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑场次'
  isEdit.value = true
  Object.assign(formData, row)
  handleCinemaChange(row.cinemaId)
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该场次吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await showtimeApi.deleteShowtime(row.id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

const handleViewSeats = (row) => {
  router.push(`/showtime/seat-selection/${row.id}`)
}

const handleSubmit = async () => {
  try {
    if (isEdit.value) {
      await showtimeApi.updateShowtime(formData.id, formData)
    } else {
      await showtimeApi.addShowtime(formData)
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadMovieList()
  loadCinemaList()
  loadData()
})
</script>

<style scoped>
.showtime-list-container {
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
