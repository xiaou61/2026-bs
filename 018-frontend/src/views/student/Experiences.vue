<template>
  <div>
    <el-card>
      <el-form :inline="true">
        <el-form-item label="类型">
          <el-select v-model="searchType" placeholder="请选择" clearable>
            <el-option label="面经" value="interview" />
            <el-option label="笔试" value="written" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="searchKeyword" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadExperiences">搜索</el-button>
          <el-button type="success" @click="dialogVisible = true">发布经验</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <el-row :gutter="20">
        <el-col :span="8" v-for="exp in experiences" :key="exp.id">
          <el-card class="exp-card" @click="viewDetail(exp.id)">
            <template #header>
              <div style="display: flex; justify-content: space-between">
                <span>{{ exp.title }}</span>
                <el-tag size="small">{{ exp.type === 'interview' ? '面经' : '笔试' }}</el-tag>
              </div>
            </template>
            <div class="exp-info">
              <p>公司: {{ exp.companyName }}</p>
              <p>岗位: {{ exp.jobTitle }}</p>
              <div class="exp-meta">
                <span>👁️ {{ exp.views }}</span>
                <span>❤️ {{ exp.likes }}</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        layout="total, prev, pager, next"
        @current-change="loadExperiences"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>

  <el-dialog v-model="dialogVisible" title="发布经验" width="600px">
    <el-form :model="form" label-width="100px">
      <el-form-item label="类型">
        <el-radio-group v-model="form.type">
          <el-radio value="interview">面经</el-radio>
          <el-radio value="written">笔试</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="公司名称">
        <el-input v-model="form.companyName" />
      </el-form-item>
      <el-form-item label="岗位名称">
        <el-input v-model="form.jobTitle" />
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model="form.content" type="textarea" :rows="6" />
      </el-form-item>
      <el-form-item label="标签">
        <el-input v-model="form.tags" placeholder="多个标签用逗号分隔" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handlePublish">发布</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getExperienceList, publishExperience } from '@/api/experience'
import { ElMessage } from 'element-plus'

const router = useRouter()

const experiences = ref([])
const loading = ref(false)
const searchType = ref('')
const searchKeyword = ref('')
const pagination = ref({
  page: 1,
  size: 9,
  total: 0
})

const dialogVisible = ref(false)
const form = ref({
  type: 'interview',
  companyName: '',
  jobTitle: '',
  title: '',
  content: '',
  tags: ''
})

const loadExperiences = async () => {
  loading.value = true
  try {
    const res = await getExperienceList({
      page: pagination.value.page,
      size: pagination.value.size,
      type: searchType.value,
      keyword: searchKeyword.value
    })
    experiences.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  router.push(`/student/experience/${id}`)
}

const handlePublish = async () => {
  try {
    await publishExperience(form.value)
    ElMessage.success('发布成功')
    dialogVisible.value = false
    loadExperiences()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadExperiences()
})
</script>

<style scoped>
.exp-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.exp-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.exp-info p {
  margin: 5px 0;
}

.exp-meta {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  color: #999;
}
</style>

