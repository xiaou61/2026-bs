<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="试卷标题" clearable style="width: 220px" />
        <el-select v-model="query.subjectId" placeholder="学科" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.publishStatus" placeholder="发布状态" clearable style="width: 140px">
          <el-option label="未发布" :value="0" />
          <el-option label="已发布" :value="1" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="试卷标题" min-width="220" />
        <el-table-column prop="subjectId" label="学科">
          <template #default="{ row }">{{ subjectName(row.subjectId) }}</template>
        </el-table-column>
        <el-table-column prop="durationMinutes" label="时长" width="90" />
        <el-table-column prop="questionCount" label="题数" width="90" />
        <el-table-column prop="publishStatus" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.publishStatus === 1 ? 'success' : 'info'">{{ row.publishStatus === 1 ? '已发布' : '未发布' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" @click="handlePublish(row, row.publishStatus === 1 ? 0 : 1)">
              {{ row.publishStatus === 1 ? '下线' : '发布' }}
            </el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
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
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑试卷' : '新增试卷'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="试卷标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="学科" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="考试时长" prop="durationMinutes">
          <el-input-number v-model="form.durationMinutes" :min="30" />
        </el-form-item>
        <el-form-item label="总分" prop="totalScore">
          <el-input-number v-model="form.totalScore" :min="0" />
        </el-form-item>
        <el-form-item label="及格分" prop="passScore">
          <el-input-number v-model="form.passScore" :min="0" />
        </el-form-item>
        <el-form-item label="题目数量" prop="questionCount">
          <el-input-number v-model="form.questionCount" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addPaper, deletePaper, getPaperList, getSubjectPublicList, publishPaper, updatePaper } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const subjectOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  title: '',
  subjectId: null,
  publishStatus: null
})

const form = reactive({
  id: null,
  subjectId: null,
  title: '',
  durationMinutes: 120,
  totalScore: 100,
  passScore: 60,
  questionCount: 0
})

const rules = {
  title: [{ required: true, message: '请输入试卷标题', trigger: 'blur' }],
  subjectId: [{ required: true, message: '请选择学科', trigger: 'change' }]
}

const subjectName = (id) => {
  const target = subjectOptions.value.find((item) => item.id === id)
  return target ? target.name : '-'
}

const loadSubjects = async () => {
  const res = await getSubjectPublicList()
  subjectOptions.value = res.data || []
}

const resetForm = () => {
  form.id = null
  form.subjectId = null
  form.title = ''
  form.durationMinutes = 120
  form.totalScore = 100
  form.passScore = 60
  form.questionCount = 0
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPaperList(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.id = row.id
  form.subjectId = row.subjectId
  form.title = row.title
  form.durationMinutes = row.durationMinutes
  form.totalScore = Number(row.totalScore || 0)
  form.passScore = Number(row.passScore || 0)
  form.questionCount = row.questionCount
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updatePaper(form)
  } else {
    await addPaper(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handlePublish = async (row, status) => {
  await publishPaper(row.id, status)
  ElMessage.success(status === 1 ? '发布成功' : '下线成功')
  loadData()
}

const handleDelete = async (id) => {
  await deletePaper(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadSubjects()
  await loadData()
})
</script>

<style scoped>
.page-container {
  padding: 10px;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  flex-wrap: wrap;
}
</style>
