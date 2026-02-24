<template>
  <div>
    <el-card>
      <div class="toolbar">
        <el-input v-model="query.title" placeholder="影片标题" clearable style="width: 220px" />
        <el-input v-model="query.category" placeholder="分类" clearable style="width: 160px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isAdmin" type="success" @click="handleAdd">新增影片</el-button>
      </div>
      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="180" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="duration" label="时长" width="100" />
        <el-table-column prop="rating" label="评分" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column label="操作" width="180" v-if="isAdmin">
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

    <el-dialog v-model="dialogVisible" title="影片信息" width="620px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.type" placeholder="MOVIE/SHOW" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="时长"><el-input-number v-model="form.duration" :min="1" /></el-form-item>
        <el-form-item label="导演"><el-input v-model="form.director" /></el-form-item>
        <el-form-item label="演员"><el-input v-model="form.actors" /></el-form-item>
        <el-form-item label="海报"><el-input v-model="form.poster" /></el-form-item>
        <el-form-item label="简介"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="状态"><el-input v-model="form.status" placeholder="ON/OFF" /></el-form-item>
        <el-form-item label="推荐"><el-switch v-model="form.isRecommend" :active-value="1" :inactive-value="0" /></el-form-item>
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
import { deleteMovie, getMovieList, getPublicMovieList, saveMovie } from '../../api'

const userStore = useUserStore()
const isAdmin = computed(() => (userStore.user?.role || '').toUpperCase() === 'ADMIN')
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, title: '', category: '' })

const form = reactive({
  id: null,
  title: '',
  type: 'MOVIE',
  category: '',
  duration: 120,
  director: '',
  actors: '',
  poster: '',
  description: '',
  status: 'ON',
  isRecommend: 0
})

const loadData = async () => {
  loading.value = true
  try {
    const api = isAdmin.value ? getMovieList : getPublicMovieList
    const res = await api(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, title: '', type: 'MOVIE', category: '', duration: 120, director: '', actors: '', poster: '', description: '', status: 'ON', isRecommend: 0 })
  dialogVisible.value = true
}

const handleEdit = row => {
  Object.assign(form, { ...row })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await saveMovie(form)
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async id => {
  await deleteMovie(id)
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
