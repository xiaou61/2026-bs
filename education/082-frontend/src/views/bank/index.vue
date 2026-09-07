<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="题库名称" clearable style="width: 220px" />
        <el-select v-model="query.subjectId" placeholder="学科" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
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
        <el-table-column prop="name" label="题库名称" />
        <el-table-column prop="subjectId" label="学科">
          <template #default="{ row }">{{ subjectName(row.subjectId) }}</template>
        </el-table-column>
        <el-table-column prop="type" label="题型" width="100" />
        <el-table-column prop="totalCount" label="题量" width="90" />
        <el-table-column prop="difficulty" label="难度" width="100" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑题库' : '新增题库'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="题库名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="学科" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="题型" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option label="单选" value="single" />
            <el-option label="多选" value="multiple" />
            <el-option label="判断" value="judge" />
          </el-select>
        </el-form-item>
        <el-form-item label="难度" prop="difficulty">
          <el-input v-model="form.difficulty" />
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
import { addBank, deleteBank, getBankList, getSubjectPublicList, updateBank } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const subjectOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  subjectId: null,
  type: '',
  status: null
})

const form = reactive({
  id: null,
  subjectId: null,
  name: '',
  type: 'single',
  difficulty: '',
  status: 1
})

const rules = {
  name: [{ required: true, message: '请输入题库名称', trigger: 'blur' }],
  subjectId: [{ required: true, message: '请选择学科', trigger: 'change' }],
  type: [{ required: true, message: '请选择题型', trigger: 'change' }]
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
  form.name = ''
  form.type = 'single'
  form.difficulty = ''
  form.status = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getBankList(query)
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
  form.name = row.name
  form.type = row.type
  form.difficulty = row.difficulty
  form.status = row.status
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateBank(form)
  } else {
    await addBank(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteBank(id)
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
