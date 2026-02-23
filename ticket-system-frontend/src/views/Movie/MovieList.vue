<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="电影标题" style="width: 200px" />
        <el-select v-model="query.status" placeholder="状态" style="width: 120px" clearable>
          <el-option label="即将上映" value="upcoming" />
          <el-option label="热映中" value="showing" />
          <el-option label="已下线" value="offline" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd" v-if="isAdmin">新增</el-button>
      </div>
      
      <el-table :data="tableData" v-loading="loading" style="margin-top: 15px">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="category" label="类型" width="100" />
        <el-table-column prop="duration" label="时长(分)" width="100" />
        <el-table-column prop="director" label="导演" />
        <el-table-column prop="rating" label="评分" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 'showing'" type="success">热映中</el-tag>
            <el-tag v-else-if="row.status === 'upcoming'" type="warning">即将上映</el-tag>
            <el-tag v-else type="info">已下线</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" v-if="isAdmin">
          <template #default="{ row }">
            <el-button link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        @current-change="loadData"
        layout="total, prev, pager, next"
        style="margin-top: 15px; justify-content: flex-end"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑电影' : '新增电影'" width="600px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="类型" prop="category">
          <el-input v-model="form.category" />
        </el-form-item>
        <el-form-item label="时长(分)" prop="duration">
          <el-input-number v-model="form.duration" :min="1" />
        </el-form-item>
        <el-form-item label="导演" prop="director">
          <el-input v-model="form.director" />
        </el-form-item>
        <el-form-item label="演员" prop="actors">
          <el-input v-model="form.actors" type="textarea" />
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status">
            <el-option label="即将上映" value="upcoming" />
            <el-option label="热映中" value="showing" />
          </el-select>
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
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { movieApi } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.userInfo.role === 'admin')

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  title: '',
  status: ''
})

const form = reactive({
  id: null,
  title: '',
  category: '',
  duration: 0,
  director: '',
  actors: '',
  description: '',
  status: 'upcoming'
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await movieApi.getMovieList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, {
    id: null,
    title: '',
    category: '',
    duration: 0,
    director: '',
    actors: '',
    description: '',
    status: 'upcoming'
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  loading.value = true
  try {
    if (form.id) {
      await movieApi.updateMovie(form)
    } else {
      await movieApi.addMovie(form)
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id) => {
  await movieApi.deleteMovie(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}
</style>
