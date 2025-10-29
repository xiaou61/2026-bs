<template>
  <div>
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="岗位类型">
          <el-select v-model="searchForm.jobType" placeholder="请选择" clearable>
            <el-option label="实习" value="internship" />
            <el-option label="校招" value="fulltime" />
          </el-select>
        </el-form-item>
        <el-form-item label="职位类别">
          <el-input v-model="searchForm.category" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="工作地点">
          <el-input v-model="searchForm.location" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="searchForm.keyword" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadJobs">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>岗位列表</h3>
      </template>
      <el-table :data="jobs" v-loading="loading">
        <el-table-column prop="title" label="岗位名称" width="200" />
        <el-table-column prop="jobType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.jobType === 'internship'" type="success">实习</el-tag>
            <el-tag v-else type="primary">校招</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="职位类别" width="120" />
        <el-table-column prop="location" label="工作地点" width="120" />
        <el-table-column label="薪资" width="150">
          <template #default="{ row }">
            {{ row.salaryMin }}-{{ row.salaryMax }}元/月
          </template>
        </el-table-column>
        <el-table-column prop="education" label="学历要求" width="100" />
        <el-table-column prop="views" label="浏览次数" width="100" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewDetail(row.id)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadJobs"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getJobList } from '@/api/job'

const router = useRouter()

const searchForm = ref({
  jobType: '',
  category: '',
  location: '',
  keyword: ''
})

const jobs = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const loadJobs = async () => {
  loading.value = true
  try {
    const res = await getJobList({
      page: pagination.value.page,
      size: pagination.value.size,
      ...searchForm.value
    })
    jobs.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  searchForm.value = {
    jobType: '',
    category: '',
    location: '',
    keyword: ''
  }
  pagination.value.page = 1
  loadJobs()
}

const viewDetail = (id) => {
  router.push(`/student/job/${id}`)
}

onMounted(() => {
  loadJobs()
})
</script>

<style scoped>
.search-card {
  margin-bottom: 20px;
}
</style>

