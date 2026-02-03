<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.studentName" placeholder="考生姓名" clearable style="width: 150px" />
        <el-input-number v-model="query.year" placeholder="年份" :min="2020" :max="2030" style="width: 120px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="examNo" label="准考证号" width="120" />
        <el-table-column prop="studentName" label="考生姓名" width="100" />
        <el-table-column prop="year" label="年份" width="80" />
        <el-table-column prop="chinese" label="语文" width="80" />
        <el-table-column prop="math" label="数学" width="80" />
        <el-table-column prop="english" label="英语" width="80" />
        <el-table-column prop="comprehensive" label="综合" width="80" />
        <el-table-column prop="totalScore" label="总分" width="80" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑成绩' : '新增成绩'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="考生" prop="studentId">
          <el-select v-model="form.studentId" filterable style="width: 100%">
            <el-option v-for="s in studentList" :key="s.id" :label="`${s.examNo} - ${s.name}`" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="年份"><el-input-number v-model="form.year" :min="2020" :max="2030" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="语文"><el-input-number v-model="form.chinese" :min="0" :max="150" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="数学"><el-input-number v-model="form.math" :min="0" :max="150" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="英语"><el-input-number v-model="form.english" :min="0" :max="150" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="综合"><el-input-number v-model="form.comprehensive" :min="0" :max="300" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getScorePage, addScore, updateScore, deleteScore, getStudentList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const studentList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, studentName: '', year: new Date().getFullYear() })
const form = reactive({ id: null, studentId: null, year: new Date().getFullYear(), chinese: 100, math: 100, english: 100, comprehensive: 200 })
const rules = { studentId: [{ required: true, message: '请选择考生', trigger: 'change' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getScorePage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadStudent = async () => {
  const res = await getStudentList()
  studentList.value = res.data
}

const handleAdd = () => {
  Object.assign(form, { id: null, studentId: null, year: new Date().getFullYear(), chinese: 100, math: 100, english: 100, comprehensive: 200 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  form.id ? await updateScore(form) : await addScore(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteScore(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => { loadStudent(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
