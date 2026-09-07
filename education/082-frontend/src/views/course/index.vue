<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="课程标题" clearable style="width: 220px" />
        <el-select v-model="query.subjectId" placeholder="学科" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="课程标题" min-width="200" />
        <el-table-column prop="subjectId" label="学科">
          <template #default="{ row }">{{ subjectName(row.subjectId) }}</template>
        </el-table-column>
        <el-table-column prop="level" label="难度" width="100" />
        <el-table-column prop="studyHours" label="学时" width="90" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑课程' : '新增课程'" width="640px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="课程标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="学科" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="讲师ID" prop="teacherId">
          <el-input-number v-model="form.teacherId" :min="1" />
        </el-form-item>
        <el-form-item label="难度" prop="level">
          <el-select v-model="form.level" style="width: 100%">
            <el-option label="基础" value="基础" />
            <el-option label="中级" value="中级" />
            <el-option label="高级" value="高级" />
          </el-select>
        </el-form-item>
        <el-form-item label="学时" prop="studyHours">
          <el-input-number v-model="form.studyHours" :min="0" />
        </el-form-item>
        <el-form-item label="简介" prop="summary">
          <el-input v-model="form.summary" type="textarea" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
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
import { addCourse, deleteCourse, getCourseList, getSubjectPublicList, updateCourse } from '../../api'

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
  status: null
})

const form = reactive({
  id: null,
  subjectId: null,
  teacherId: 2,
  title: '',
  level: '基础',
  summary: '',
  studyHours: 0,
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入课程标题', trigger: 'blur' }],
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
  form.teacherId = 2
  form.title = ''
  form.level = '基础'
  form.summary = ''
  form.studyHours = 0
  form.status = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCourseList(query)
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
  form.teacherId = row.teacherId
  form.title = row.title
  form.level = row.level
  form.summary = row.summary
  form.studyHours = row.studyHours
  form.status = row.status
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateCourse(form)
  } else {
    await addCourse(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteCourse(id)
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
