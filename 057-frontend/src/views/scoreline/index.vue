<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input-number v-model="query.year" placeholder="年份" :min="2020" :max="2030" style="width: 120px" />
        <el-input v-model="query.province" placeholder="省份" clearable style="width: 120px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="year" label="年份" width="80" />
        <el-table-column prop="majorName" label="专业" />
        <el-table-column prop="province" label="省份" width="100" />
        <el-table-column prop="category" label="科类" width="80" />
        <el-table-column prop="batch" label="批次" width="80" />
        <el-table-column prop="score" label="分数线" width="80" />
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
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分数线' : '新增分数线'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="年份"><el-input-number v-model="form.year" :min="2020" :max="2030" /></el-form-item>
        <el-form-item label="专业" prop="majorId">
          <el-select v-model="form.majorId" filterable style="width: 100%">
            <el-option v-for="m in majorList" :key="m.id" :label="m.name" :value="m.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="省份"><el-input v-model="form.province" /></el-form-item>
        <el-form-item label="科类">
          <el-select v-model="form.category" style="width: 100%">
            <el-option label="理科" value="理科" /><el-option label="文科" value="文科" />
          </el-select>
        </el-form-item>
        <el-form-item label="批次">
          <el-select v-model="form.batch" style="width: 100%">
            <el-option label="一本" value="一本" /><el-option label="二本" value="二本" /><el-option label="专科" value="专科" />
          </el-select>
        </el-form-item>
        <el-form-item label="分数线"><el-input-number v-model="form.score" :min="0" :max="750" /></el-form-item>
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
import { getScoreLinePage, addScoreLine, updateScoreLine, deleteScoreLine, getMajorList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const majorList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, year: new Date().getFullYear(), province: '' })
const form = reactive({ id: null, year: new Date().getFullYear(), majorId: null, province: '', category: '理科', batch: '一本', score: 500 })
const rules = { majorId: [{ required: true, message: '请选择专业', trigger: 'change' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getScoreLinePage(query)
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
  Object.assign(form, { id: null, year: new Date().getFullYear(), majorId: null, province: '', category: '理科', batch: '一本', score: 500 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  form.id ? await updateScoreLine(form) : await addScoreLine(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteScoreLine(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => { loadMajor(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
