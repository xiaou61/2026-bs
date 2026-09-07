<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.studentName" placeholder="考生姓名" clearable style="width: 150px" />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 120px">
          <el-option label="待确认" :value="0" /><el-option label="已确认" :value="1" /><el-option label="已放弃" :value="2" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增录取</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="admissionNo" label="录取编号" width="150" />
        <el-table-column prop="examNo" label="准考证号" width="120" />
        <el-table-column prop="studentName" label="考生姓名" width="100" />
        <el-table-column prop="majorName" label="录取专业" />
        <el-table-column prop="score" label="分数" width="80" />
        <el-table-column prop="planYear" label="年份" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="['warning', 'success', 'danger'][row.status]">{{ ['待确认', '已确认', '已放弃'][row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="success" v-if="row.status === 0" @click="handleConfirm(row.id)">确认</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="新增录取" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="考生" prop="studentId">
          <el-select v-model="form.studentId" filterable style="width: 100%">
            <el-option v-for="s in studentList" :key="s.id" :label="`${s.examNo} - ${s.name}`" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="majorId">
          <el-select v-model="form.majorId" filterable style="width: 100%">
            <el-option v-for="m in majorList" :key="m.id" :label="m.name" :value="m.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="分数"><el-input-number v-model="form.score" :min="0" :max="750" /></el-form-item>
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
import { getAdmissionPage, addAdmission, deleteAdmission, confirmAdmission, getStudentList, getMajorList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const studentList = ref([])
const majorList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, studentName: '', status: null })
const form = reactive({ studentId: null, majorId: null, score: 500 })
const rules = {
  studentId: [{ required: true, message: '请选择考生', trigger: 'change' }],
  majorId: [{ required: true, message: '请选择专业', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getAdmissionPage(query)
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

const loadMajor = async () => {
  const res = await getMajorList()
  majorList.value = res.data
}

const handleAdd = () => {
  Object.assign(form, { studentId: null, majorId: null, score: 500 })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  await addAdmission(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteAdmission(id)
  ElMessage.success('删除成功')
  loadData()
}

const handleConfirm = async (id) => {
  await confirmAdmission(id)
  ElMessage.success('确认成功')
  loadData()
}

onMounted(() => { loadStudent(); loadMajor(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
