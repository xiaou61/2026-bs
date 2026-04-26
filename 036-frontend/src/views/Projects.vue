<template>
  <div class="projects-page">
    <div class="container">
      <div class="filter-bar">
        <el-select v-model="filters.category" placeholder="选择分类" clearable @change="loadProjects">
          <el-option v-for="cat in categories" :key="cat" :label="cat" :value="cat" />
        </el-select>
        <el-select v-model="filters.status" placeholder="项目状态" clearable @change="loadProjects">
          <el-option label="进行中" value="ACTIVE" />
          <el-option label="已完成" value="COMPLETED" />
        </el-select>
      </div>

      <el-row :gutter="20">
        <el-col :span="8" v-for="project in projects" :key="project.id">
          <el-card class="project-card" @click="goToDetail(project.id)">
            <img :src="project.coverImage || '/cover-placeholder.svg'" class="project-cover">
            <div class="project-info">
              <el-tag :type="getStatusType(project.status)">{{ getStatusText(project.status) }}</el-tag>
              <h3>{{ project.title }}</h3>
              <p class="project-desc">{{ project.description }}</p>
              <div class="project-progress">
                <el-progress :percentage="calculateProgress(project)" />
                <div class="progress-info">
                  <span>已筹：¥{{ project.currentAmount }}</span>
                  <span>目标：¥{{ project.targetAmount }}</span>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-pagination
        v-model:current-page="pagination.page"
        :page-size="pagination.size"
        :total="pagination.total"
        @current-change="loadProjects"
        layout="prev, pager, next"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProjects, getCategories } from '@/api/project'

const router = useRouter()
const projects = ref([])
const categories = ref([])
const filters = ref({
  category: '',
  status: ''
})
const pagination = ref({
  page: 1,
  size: 9,
  total: 0
})

const loadProjects = async () => {
  try {
    const params = {
      page: pagination.value.page - 1,
      size: pagination.value.size,
      category: filters.value.category,
      status: filters.value.status
    }
    const res = await getProjects(params)
    projects.value = res.data.content
    pagination.value.total = res.data.totalElements
  } catch (error) {
    console.error(error)
  }
}

const loadCategories = async () => {
  try {
    const res = await getCategories()
    categories.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = (project) => {
  if (!project.targetAmount || project.targetAmount === 0) return 0
  return Math.round((project.currentAmount / project.targetAmount) * 100)
}

const getStatusType = (status) => {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿',
    CANCELLED: '已取消'
  }
  return map[status] || status
}

const goToDetail = (id) => {
  router.push(`/project/${id}`)
}

onMounted(() => {
  loadProjects()
  loadCategories()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}

.filter-bar {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.project-card {
  cursor: pointer;
  margin-bottom: 20px;
  transition: transform 0.3s;
}

.project-card:hover {
  transform: translateY(-5px);
}

.project-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.project-info {
  padding: 15px 0;
}

.project-info h3 {
  font-size: 18px;
  margin: 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.project-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.project-progress {
  margin-top: 10px;
}

.progress-info {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
  font-size: 14px;
  color: #666;
}

.el-pagination {
  margin-top: 30px;
  text-align: center;
}
</style>
