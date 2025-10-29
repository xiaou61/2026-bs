<template>
  <div>
    <el-card>
      <el-button type="primary" @click="handleAdd">发布岗位</el-button>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <h3>岗位列表</h3>
      </template>
      <el-table :data="jobs" v-loading="loading">
        <el-table-column prop="title" label="岗位名称" />
        <el-table-column prop="jobType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.jobType === 'internship'" type="success">实习</el-tag>
            <el-tag v-else type="primary">校招</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="工作地点" width="120" />
        <el-table-column label="薪资" width="150">
          <template #default="{ row }">
            {{ row.salaryMin }}-{{ row.salaryMax }}元/月
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 1" type="success">发布中</el-tag>
            <el-tag v-else type="info">已下架</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
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

  <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑岗位' : '发布岗位'" width="700px">
    <el-form :model="form" label-width="100px">
      <el-form-item label="岗位名称">
        <el-input v-model="form.title" />
      </el-form-item>
      <el-form-item label="岗位类型">
        <el-radio-group v-model="form.jobType">
          <el-radio value="internship">实习</el-radio>
          <el-radio value="fulltime">校招</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="职位类别">
        <el-input v-model="form.category" />
      </el-form-item>
      <el-form-item label="工作地点">
        <el-input v-model="form.location" />
      </el-form-item>
      <el-form-item label="薪资范围">
        <el-col :span="11">
          <el-input v-model="form.salaryMin" placeholder="最低薪资" />
        </el-col>
        <el-col :span="2" style="text-align: center">-</el-col>
        <el-col :span="11">
          <el-input v-model="form.salaryMax" placeholder="最高薪资" />
        </el-col>
      </el-form-item>
      <el-form-item label="学历要求">
        <el-select v-model="form.education">
          <el-option label="专科" value="专科" />
          <el-option label="本科" value="本科" />
          <el-option label="硕士" value="硕士" />
          <el-option label="博士" value="博士" />
        </el-select>
      </el-form-item>
      <el-form-item label="专业要求">
        <el-input v-model="form.major" />
      </el-form-item>
      <el-form-item label="技能要求">
        <el-input v-model="form.skills" />
      </el-form-item>
      <el-form-item label="招聘人数">
        <el-input-number v-model="form.headcount" :min="1" />
      </el-form-item>
      <el-form-item label="岗位描述">
        <el-input v-model="form.description" type="textarea" :rows="4" />
      </el-form-item>
      <el-form-item label="任职要求">
        <el-input v-model="form.requirement" type="textarea" :rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSave">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getJobsByCompanyId, publishJob, updateJob, deleteJob } from '@/api/job'
import { ElMessage, ElMessageBox } from 'element-plus'

const userStore = useUserStore()

const jobs = ref([])
const loading = ref(false)
const pagination = ref({
  page: 1,
  size: 10,
  total: 0
})

const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({
  title: '',
  jobType: 'internship',
  category: '',
  location: '',
  salaryMin: null,
  salaryMax: null,
  education: '本科',
  major: '',
  skills: '',
  headcount: 1,
  description: '',
  requirement: ''
})

const loadJobs = async () => {
  loading.value = true
  try {
    const companyId = userStore.user.companyId
    const res = await getJobsByCompanyId(companyId, {
      page: pagination.value.page,
      size: pagination.value.size
    })
    jobs.value = res.data.records
    pagination.value.total = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  isEdit.value = false
  form.value = {
    title: '',
    jobType: 'internship',
    category: '',
    location: '',
    salaryMin: null,
    salaryMax: null,
    education: '本科',
    major: '',
    skills: '',
    headcount: 1,
    description: '',
    requirement: '',
    companyId: userStore.user.companyId
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSave = async () => {
  try {
    if (isEdit.value) {
      await updateJob(form.value)
    } else {
      await publishJob({ ...form.value, companyId: userStore.user.companyId })
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadJobs()
  } catch (error) {
    console.error(error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteJob(id)
    ElMessage.success('删除成功')
    loadJobs()
  } catch (error) {
    console.log(error)
  }
}

onMounted(() => {
  loadJobs()
})
</script>

