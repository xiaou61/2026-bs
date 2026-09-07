<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.subjectId" placeholder="学科" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.bankId" placeholder="题库" clearable style="width: 220px">
          <el-option v-for="item in bankOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.type" placeholder="题型" clearable style="width: 140px">
          <el-option label="单选" value="single" />
          <el-option label="多选" value="multiple" />
          <el-option label="判断" value="judge" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="stem" label="题干" min-width="260" show-overflow-tooltip />
        <el-table-column prop="type" label="题型" width="100" />
        <el-table-column prop="bankId" label="题库" min-width="180">
          <template #default="{ row }">{{ bankName(row.bankId) }}</template>
        </el-table-column>
        <el-table-column prop="answer" label="答案" width="100" />
        <el-table-column prop="score" label="分值" width="90" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑试题' : '新增试题'" width="760px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="学科" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="题库" prop="bankId">
          <el-select v-model="form.bankId" style="width: 100%">
            <el-option v-for="item in bankOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="题型" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option label="单选" value="single" />
            <el-option label="多选" value="multiple" />
            <el-option label="判断" value="judge" />
          </el-select>
        </el-form-item>
        <el-form-item label="题干" prop="stem">
          <el-input v-model="form.stem" type="textarea" />
        </el-form-item>
        <el-form-item label="选项A">
          <el-input v-model="form.optionA" />
        </el-form-item>
        <el-form-item label="选项B">
          <el-input v-model="form.optionB" />
        </el-form-item>
        <el-form-item label="选项C">
          <el-input v-model="form.optionC" />
        </el-form-item>
        <el-form-item label="选项D">
          <el-input v-model="form.optionD" />
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" />
        </el-form-item>
        <el-form-item label="分值" prop="score">
          <el-input-number v-model="form.score" :min="0" :step="1" />
        </el-form-item>
        <el-form-item label="解析">
          <el-input v-model="form.analysis" type="textarea" />
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
import { addQuestion, deleteQuestion, getBankList, getQuestionList, getSubjectPublicList, updateQuestion } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const subjectOptions = ref([])
const bankOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  subjectId: null,
  bankId: null,
  type: '',
  stem: ''
})

const form = reactive({
  id: null,
  bankId: null,
  subjectId: null,
  type: 'single',
  stem: '',
  optionA: '',
  optionB: '',
  optionC: '',
  optionD: '',
  answer: '',
  analysis: '',
  score: 1
})

const rules = {
  subjectId: [{ required: true, message: '请选择学科', trigger: 'change' }],
  bankId: [{ required: true, message: '请选择题库', trigger: 'change' }],
  type: [{ required: true, message: '请选择题型', trigger: 'change' }],
  stem: [{ required: true, message: '请输入题干', trigger: 'blur' }],
  answer: [{ required: true, message: '请输入答案', trigger: 'blur' }]
}

const bankName = (id) => {
  const target = bankOptions.value.find((item) => item.id === id)
  return target ? target.name : '-'
}

const loadSubjects = async () => {
  const res = await getSubjectPublicList()
  subjectOptions.value = res.data || []
}

const loadBanks = async () => {
  const res = await getBankList({ pageNum: 1, pageSize: 200 })
  bankOptions.value = res.data.records || []
}

const resetForm = () => {
  form.id = null
  form.bankId = null
  form.subjectId = null
  form.type = 'single'
  form.stem = ''
  form.optionA = ''
  form.optionB = ''
  form.optionC = ''
  form.optionD = ''
  form.answer = ''
  form.analysis = ''
  form.score = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getQuestionList(query)
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
  form.bankId = row.bankId
  form.subjectId = row.subjectId
  form.type = row.type
  form.stem = row.stem
  form.optionA = row.optionA
  form.optionB = row.optionB
  form.optionC = row.optionC
  form.optionD = row.optionD
  form.answer = row.answer
  form.analysis = row.analysis
  form.score = Number(row.score || 1)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateQuestion(form)
  } else {
    await addQuestion(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteQuestion(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadSubjects()
  await loadBanks()
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
