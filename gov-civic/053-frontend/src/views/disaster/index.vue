<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="灾情名称" style="width: 200px" clearable />
        <el-select v-model="query.type" placeholder="灾情类型" style="width: 150px" clearable>
          <el-option label="地震" value="地震" />
          <el-option label="洪水" value="洪水" />
          <el-option label="台风" value="台风" />
          <el-option label="火灾" value="火灾" />
          <el-option label="其他" value="其他" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="name" label="灾情名称" />
        <el-table-column prop="type" label="灾情类型" width="100" />
        <el-table-column prop="level" label="灾情等级" width="100">
          <template #default="{ row }">
            <el-tag :type="row.level >= 4 ? 'danger' : row.level >= 2 ? 'warning' : 'info'">{{ row.level }}级</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="发生地点" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 0 ? 'danger' : row.status === 1 ? 'warning' : 'success'">
              {{ ['待处理', '处理中', '已结束'][row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="happenTime" label="发生时间" width="160" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button type="danger" link>删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑灾情' : '新增灾情'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="名称" prop="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option label="地震" value="地震" />
            <el-option label="洪水" value="洪水" />
            <el-option label="台风" value="台风" />
            <el-option label="火灾" value="火灾" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="等级" prop="level"><el-input-number v-model="form.level" :min="1" :max="5" /></el-form-item>
        <el-form-item label="地点" prop="location"><el-input v-model="form.location" /></el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="待处理" :value="0" />
            <el-option label="处理中" :value="1" />
            <el-option label="已结束" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="发生时间"><el-date-picker v-model="form.happenTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
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
import { getDisasterList, addDisaster, updateDisaster, deleteDisaster } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()

const query = reactive({ pageNum: 1, pageSize: 10, name: '', type: '' })
const form = reactive({ id: null, name: '', type: '', level: 1, location: '', status: 0, happenTime: '', description: '' })
const rules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }], type: [{ required: true, message: '请选择类型', trigger: 'change' }], location: [{ required: true, message: '请输入地点', trigger: 'blur' }] }

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDisasterList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  Object.assign(form, { id: null, name: '', type: '', level: 1, location: '', status: 0, happenTime: '', description: '' })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateDisaster(form)
    ElMessage.success('更新成功')
  } else {
    await addDisaster(form)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteDisaster(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
