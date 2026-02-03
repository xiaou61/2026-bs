<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input-number v-model="query.year" placeholder="年份" :min="2020" :max="2030" style="width: 120px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="year" label="年份" width="80" />
        <el-table-column prop="majorName" label="专业" />
        <el-table-column prop="departmentName" label="院系" />
        <el-table-column prop="planCount" label="计划招生" width="100" />
        <el-table-column prop="actualCount" label="实际录取" width="100" />
        <el-table-column prop="minScore" label="最低分数" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'info' : row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 0 ? '草稿' : row.status === 1 ? '已发布' : '已关闭' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" v-if="row.status === 0" @click="handlePublish(row.id)">发布</el-button>
            <el-button link type="warning" v-if="row.status === 1" @click="handleClose(row.id)">关闭</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑计划' : '新增计划'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="年份" prop="year"><el-input-number v-model="form.year" :min="2020" :max="2030" /></el-form-item>
        <el-form-item label="专业" prop="majorId">
          <el-select v-model="form.majorId" filterable style="width: 100%">
            <el-option v-for="m in majorList" :key="m.id" :label="m.name" :value="m.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="计划招生"><el-input-number v-model="form.planCount" :min="0" /></el-form-item>
        <el-form-item label="最低分数线"><el-input-number v-model="form.minScore" :min="0" :max="750" /></el-form-item>
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
import { getPlanPage, addPlan, updatePlan, deletePlan, publishPlan, closePlan, getMajorList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const majorList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, year: new Date().getFullYear() })
const form = reactive({ id: null, year: new Date().getFullYear(), majorId: null, planCount: 100, minScore: 500 })
const rules = {
  year: [{ required: true, message: '请选择年份', trigger: 'change' }],
  majorId: [{ required: true, message: '请选择专业', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPlanPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadMajor = async () => {
  const res = await getMajorList()
  majorList.value = res.data
}

const handleAdd = () => {
  Object.assign(form, { id: null, year: new Date().getFullYear(), majorId: null, planCount: 100, minScore: 500 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  form.id ? await updatePlan(form) : await addPlan(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deletePlan(id)
  ElMessage.success('删除成功')
  loadData()
}

const handlePublish = async (id) => {
  await publishPlan(id)
  ElMessage.success('发布成功')
  loadData()
}

const handleClose = async (id) => {
  await closePlan(id)
  ElMessage.success('已关闭')
  loadData()
}

onMounted(() => { loadMajor(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
