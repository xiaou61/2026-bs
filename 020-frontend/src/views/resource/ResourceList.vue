<template>
  <div class="resource-container">
    <el-card>
      <template #header>
        <div class="header-actions">
          <h3>学习资源库</h3>
          <el-button type="primary" @click="goToUpload">
            <el-icon><Upload /></el-icon>
            上传资源
          </el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="分类">
          <el-select v-model="searchForm.category" placeholder="全部分类" clearable>
            <el-option label="编程" value="编程" />
            <el-option label="数学" value="数学" />
            <el-option label="算法" value="算法" />
            <el-option label="数据库" value="数据库" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="searchForm.keyword" placeholder="搜索资源" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="resourceList" style="width: 100%">
        <el-table-column prop="title" label="资源名称" min-width="200">
          <template #default="{ row }">
            <el-link type="primary" @click="goToDetail(row.id)">{{ row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="分类" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.category }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="points" label="所需积分" width="100">
          <template #default="{ row }">
            <el-tag type="warning">{{ row.points }} 积分</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="rating" label="评分" width="120">
          <template #default="{ row }">
            <el-rate v-model="row.rating" disabled show-score />
          </template>
        </el-table-column>
        <el-table-column prop="downloadCount" label="下载量" width="100" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="createTime" label="上传时间" width="180" />
      </el-table>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getResourceList } from '@/api/resource'

const router = useRouter()

const searchForm = ref({
  category: '',
  keyword: ''
})

const resourceList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadData = async () => {
  try {
    const res = await getResourceList({
      page: currentPage.value,
      size: pageSize.value,
      category: searchForm.value.category,
      keyword: searchForm.value.keyword
    })
    resourceList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleSearch = () => {
  currentPage.value = 1
  loadData()
}

const handlePageChange = () => {
  loadData()
}

const goToDetail = (id) => {
  router.push(`/resource/${id}`)
}

const goToUpload = () => {
  router.push('/resource/upload')
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.resource-container {
  max-width: 1400px;
  margin: 0 auto;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions h3 {
  margin: 0;
}

.search-form {
  margin-bottom: 20px;
}
</style>

