<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.teacherNo" placeholder="教师工号" clearable style="width: 180px" />
        <el-select v-model="query.subjectId" placeholder="科目" clearable style="width: 180px">
          <el-option v-for="item in subjectOptions" :key="item.id" :label="item.subjectName" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增教师档案</el-button>
      </div>

      <el-table :data="tableData" border v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="teacherNo" label="教师工号" min-width="130" />
        <el-table-column prop="teacherName" label="教师姓名" min-width="120" />
        <el-table-column prop="subjectName" label="所属科目" min-width="120" />
        <el-table-column prop="titleName" label="职称" min-width="120" />
        <el-table-column label="状态" width="90">
          <template #default="{ row }">
            <el-switch :model-value="row.status === 1" @change="val => handleStatusChange(row, val)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑教师档案' : '新增教师档案'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="教师账号" prop="userId">
          <el-select v-model="form.userId" filterable style="width: 100%">
            <el-option v-for="item in teacherUsers" :key="item.id" :label="`${item.nickname || item.username} (${item.username})`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="教师工号" prop="teacherNo"><el-input v-model="form.teacherNo" maxlength="30" /></el-form-item>
        <el-form-item label="所属科目" prop="subjectId">
          <el-select v-model="form.subjectId" style="width: 100%">
            <el-option v-for="item in subjectOptions" :key="item.id" :label="item.subjectName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="职称"><el-input v-model="form.titleName" maxlength="30" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
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
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addTeacher, deleteTeacher, getSubjectList, getTeacherPage, getUserPage, updateTeacher, updateTeacherStatus } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const subjectOptions = ref([])
const teacherUsers = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, teacherNo: '', subjectId: null, status: null })
const form = reactive({ id: null, userId: null, teacherNo: '', subjectId: null, titleName: '教师', status: 1 })
const rules = {
  userId: [{ required: true, message: '请选择教师账号', trigger: 'change' }],
  teacherNo: [{ required: true, message: '请输入教师工号', trigger: 'blur' }],
  subjectId: [{ required: true, message: '请选择所属科目', trigger: 'change' }]
}

const loadOptions = async () => {
  const [subjectRes, userRes] = await Promise.all([
    getSubjectList(),
    getUserPage({ pageNum: 1, pageSize: 500, role: 'TEACHER' })
  ])
  subjectOptions.value = subjectRes.data || []
  teacherUsers.value = userRes.data.records || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getTeacherPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, { id: null, userId: null, teacherNo: '', subjectId: null, titleName: '教师', status: 1 })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, {
    id: row.id,
    userId: row.userId,
    teacherNo: row.teacherNo,
    subjectId: row.subjectId,
    titleName: row.titleName,
    status: row.status
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateTeacher(form)
  } else {
    await addTeacher(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确定删除教师档案 ${row.teacherNo} 吗？`, '提示', { type: 'warning' })
  await deleteTeacher(row.id)
  ElMessage.success('删除成功')
  loadData()
}

const handleStatusChange = async (row, checked) => {
  await updateTeacherStatus({ id: row.id, status: checked ? 1 : 0 })
  row.status = checked ? 1 : 0
  ElMessage.success('状态已更新')
}

onMounted(async () => {
  await loadOptions()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
