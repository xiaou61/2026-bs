<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.courseName" placeholder="课程名称" clearable style="width: 220px" />
        <el-select v-model="query.categoryId" placeholder="分类" clearable style="width: 180px">
          <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-input v-model="query.term" placeholder="学期，如2025-2026-2" clearable style="width: 180px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="courseName" label="课程名称" min-width="180" />
        <el-table-column prop="courseCode" label="课程编码" width="130" />
        <el-table-column label="课程分类" width="120">
          <template #default="{ row }">{{ categoryName(row.categoryId) }}</template>
        </el-table-column>
        <el-table-column prop="term" label="学期" width="130" />
        <el-table-column prop="credit" label="学分" width="80" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="170">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑课程' : '新增课程'" width="720px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
        <el-form-item label="课程名称" prop="courseName"><el-input v-model="form.courseName" /></el-form-item>
        <el-form-item label="课程编码" prop="courseCode"><el-input v-model="form.courseCode" /></el-form-item>
        <el-form-item label="课程分类" prop="categoryId">
          <el-select v-model="form.categoryId" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="授课教师ID" prop="teacherId"><el-input-number v-model="form.teacherId" :min="1" /></el-form-item>
        <el-form-item label="学分" prop="credit"><el-input-number v-model="form.credit" :precision="2" :step="0.5" :min="0" /></el-form-item>
        <el-form-item label="学期" prop="term"><el-input v-model="form.term" placeholder="如 2025-2026-2" /></el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="课程简介" prop="description"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
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
import { addCourse, deleteCourse, getCategoryEnabled, getCourseList, updateCourse } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const categoryOptions = ref([])

const query = reactive({ pageNum: 1, pageSize: 10, courseName: '', categoryId: null, status: null, term: '' })
const form = reactive({
  id: null,
  courseName: '',
  courseCode: '',
  categoryId: null,
  teacherId: 2,
  credit: 3,
  term: '2025-2026-2',
  description: '',
  status: 1
})

const rules = {
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  courseCode: [{ required: true, message: '请输入课程编码', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择课程分类', trigger: 'change' }]
}

const categoryName = (id) => {
  const target = categoryOptions.value.find(item => item.id === id)
  return target ? target.name : '-'
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    courseName: '',
    courseCode: '',
    categoryId: null,
    teacherId: 2,
    credit: 3,
    term: '2025-2026-2',
    description: '',
    status: 1
  })
}

const loadCategory = async () => {
  const res = await getCategoryEnabled()
  categoryOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getCourseList(query)
    tableData.value = res.data.list || []
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
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) await updateCourse(form)
  else await addCourse(form)
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
  await loadCategory()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
</style>
