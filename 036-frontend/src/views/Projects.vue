&lt;template&gt;
  &lt;div class="projects-page"&gt;
    &lt;div class="container"&gt;
      &lt;div class="filter-bar"&gt;
        &lt;el-select v-model="filters.category" placeholder="选择分类" clearable @change="loadProjects"&gt;
          &lt;el-option v-for="cat in categories" :key="cat" :label="cat" :value="cat" /&gt;
        &lt;/el-select&gt;
        &lt;el-select v-model="filters.status" placeholder="项目状态" clearable @change="loadProjects"&gt;
          &lt;el-option label="进行中" value="ACTIVE" /&gt;
          &lt;el-option label="已完成" value="COMPLETED" /&gt;
        &lt;/el-select&gt;
      &lt;/div&gt;

      &lt;el-row :gutter="20"&gt;
        &lt;el-col :span="8" v-for="project in projects" :key="project.id"&gt;
          &lt;el-card class="project-card" @click="goToDetail(project.id)"&gt;
            &lt;img :src="project.coverImage || 'https://via.placeholder.com/300x200'" class="project-cover"&gt;
            &lt;div class="project-info"&gt;
              &lt;el-tag :type="getStatusType(project.status)"&gt;{{ getStatusText(project.status) }}&lt;/el-tag&gt;
              &lt;h3&gt;{{ project.title }}&lt;/h3&gt;
              &lt;p class="project-desc"&gt;{{ project.description }}&lt;/p&gt;
              &lt;div class="project-progress"&gt;
                &lt;el-progress :percentage="calculateProgress(project)" /&gt;
                &lt;div class="progress-info"&gt;
                  &lt;span&gt;已筹：¥{{ project.currentAmount }}&lt;/span&gt;
                  &lt;span&gt;目标：¥{{ project.targetAmount }}&lt;/span&gt;
                &lt;/div&gt;
              &lt;/div&gt;
            &lt;/div&gt;
          &lt;/el-card&gt;
        &lt;/el-col&gt;
      &lt;/el-row&gt;

      &lt;el-pagination
        v-model:current-page="pagination.page"
        :page-size="pagination.size"
        :total="pagination.total"
        @current-change="loadProjects"
        layout="prev, pager, next"
      /&gt;
    &lt;/div&gt;
  &lt;/div&gt;
&lt;/template&gt;

&lt;script setup&gt;
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

const loadProjects = async () =&gt; {
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

const loadCategories = async () =&gt; {
  try {
    const res = await getCategories()
    categories.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const calculateProgress = (project) =&gt; {
  if (!project.targetAmount || project.targetAmount === 0) return 0
  return Math.round((project.currentAmount / project.targetAmount) * 100)
}

const getStatusType = (status) =&gt; {
  const map = {
    ACTIVE: 'success',
    COMPLETED: 'info',
    DRAFT: 'warning',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

const getStatusText = (status) =&gt; {
  const map = {
    ACTIVE: '进行中',
    COMPLETED: '已完成',
    DRAFT: '草稿',
    CANCELLED: '已取消'
  }
  return map[status] || status
}

const goToDetail = (id) =&gt; {
  router.push(`/project/${id}`)
}

onMounted(() =&gt; {
  loadProjects()
  loadCategories()
})
&lt;/script&gt;

&lt;style scoped&gt;
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
&lt;/style&gt;
