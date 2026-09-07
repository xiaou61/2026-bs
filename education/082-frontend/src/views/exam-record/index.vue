<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.userId" placeholder="学员ID" clearable style="width: 140px" />
        <el-input v-model="query.paperId" placeholder="试卷ID" clearable style="width: 140px" />
        <el-select v-model="query.passStatus" placeholder="是否通过" clearable style="width: 140px">
          <el-option label="通过" :value="1" />
          <el-option label="未通过" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="记录ID" width="90" />
        <el-table-column prop="paperId" label="试卷ID" width="90" />
        <el-table-column prop="userId" label="学员ID" width="90" />
        <el-table-column prop="totalScore" label="总分" width="90" />
        <el-table-column prop="durationSeconds" label="用时(秒)" width="120" />
        <el-table-column prop="passStatus" label="结果" width="90">
          <template #default="{ row }">
            <el-tag :type="row.passStatus === 1 ? 'success' : 'danger'">{{ row.passStatus === 1 ? '通过' : '未通过' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑考试记录' : '新增考试记录'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="试卷ID" prop="paperId">
          <el-input-number v-model="form.paperId" :min="1" />
        </el-form-item>
        <el-form-item label="学员ID" prop="userId">
          <el-input-number v-model="form.userId" :min="1" />
        </el-form-item>
        <el-form-item label="总分" prop="totalScore">
          <el-input-number v-model="form.totalScore" :min="0" />
        </el-form-item>
        <el-form-item label="客观分" prop="objectiveScore">
          <el-input-number v-model="form.objectiveScore" :min="0" />
        </el-form-item>
        <el-form-item label="主观分" prop="subjectiveScore">
          <el-input-number v-model="form.subjectiveScore" :min="0" />
        </el-form-item>
        <el-form-item label="用时(秒)" prop="durationSeconds">
          <el-input-number v-model="form.durationSeconds" :min="0" />
        </el-form-item>
        <el-form-item label="是否通过" prop="passStatus">
          <el-radio-group v-model="form.passStatus">
            <el-radio :label="1">通过</el-radio>
            <el-radio :label="0">未通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input v-model="form.status" />
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
import { addExamRecord, deleteExamRecord, getExamRecordList, updateExamRecord } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  userId: '',
  paperId: '',
  status: '',
  passStatus: null
})

const form = reactive({
  id: null,
  paperId: null,
  userId: null,
  totalScore: 0,
  objectiveScore: 0,
  subjectiveScore: 0,
  durationSeconds: 0,
  passStatus: 0,
  status: 'submitted'
})

const rules = {
  paperId: [{ required: true, message: '请输入试卷ID', trigger: 'blur' }],
  userId: [{ required: true, message: '请输入学员ID', trigger: 'blur' }]
}

const resetForm = () => {
  form.id = null
  form.paperId = null
  form.userId = null
  form.totalScore = 0
  form.objectiveScore = 0
  form.subjectiveScore = 0
  form.durationSeconds = 0
  form.passStatus = 0
  form.status = 'submitted'
}

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      ...query,
      userId: query.userId ? Number(query.userId) : null,
      paperId: query.paperId ? Number(query.paperId) : null
    }
    const res = await getExamRecordList(params)
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
  form.paperId = row.paperId
  form.userId = row.userId
  form.totalScore = Number(row.totalScore || 0)
  form.objectiveScore = Number(row.objectiveScore || 0)
  form.subjectiveScore = Number(row.subjectiveScore || 0)
  form.durationSeconds = row.durationSeconds
  form.passStatus = row.passStatus
  form.status = row.status
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateExamRecord(form)
  } else {
    await addExamRecord(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteExamRecord(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
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
