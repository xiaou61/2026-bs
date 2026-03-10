<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.userId" placeholder="学员ID" clearable style="width: 140px" />
        <el-select v-model="query.subjectId" placeholder="学科" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-input v-model="query.title" placeholder="计划标题" clearable style="width: 220px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="title" label="计划标题" min-width="220" />
        <el-table-column prop="userId" label="学员ID" width="90" />
        <el-table-column prop="subjectId" label="学科">
          <template #default="{ row }">{{ subjectName(row.subjectId) }}</template>
        </el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="completedDays" label="完成天数" width="110" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑计划' : '新增计划'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="计划标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="学员ID" prop="userId">
          <el-input-number v-model="form.userId" :min="1" />
        </el-form-item>
        <el-form-item label="学科" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker v-model="form.startDate" value-format="YYYY-MM-DD" type="date" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker v-model="form.endDate" value-format="YYYY-MM-DD" type="date" style="width: 100%" />
        </el-form-item>
        <el-form-item label="每日目标" prop="dailyTarget">
          <el-input v-model="form.dailyTarget" type="textarea" />
        </el-form-item>
        <el-form-item label="完成天数" prop="completedDays">
          <el-input-number v-model="form.completedDays" :min="0" />
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
import { addPlan, deletePlan, getPlanList, getSubjectPublicList, updatePlan } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const subjectOptions = ref([])

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  userId: '',
  subjectId: null,
  title: '',
  status: null
})

const form = reactive({
  id: null,
  userId: 3,
  subjectId: null,
  title: '',
  startDate: '',
  endDate: '',
  dailyTarget: '',
  completedDays: 0,
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入计划标题', trigger: 'blur' }],
  userId: [{ required: true, message: '请输入学员ID', trigger: 'blur' }],
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
  form.userId = 3
  form.subjectId = null
  form.title = ''
  form.startDate = ''
  form.endDate = ''
  form.dailyTarget = ''
  form.completedDays = 0
  form.status = 1
}

const loadData = async () => {
  loading.value = true
  try {
    const params = { ...query, userId: query.userId ? Number(query.userId) : null }
    const res = await getPlanList(params)
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
  form.userId = row.userId
  form.subjectId = row.subjectId
  form.title = row.title
  form.startDate = row.startDate
  form.endDate = row.endDate
  form.dailyTarget = row.dailyTarget
  form.completedDays = row.completedDays
  form.status = row.status
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updatePlan(form)
  } else {
    await addPlan(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deletePlan(id)
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
