<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.title" placeholder="职位名称" style="width: 200px" clearable />
        <el-input v-model="query.city" placeholder="城市" style="width: 120px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="openDialog()">发布职位</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="title" label="职位名称" />
        <el-table-column prop="companyName" label="企业" />
        <el-table-column prop="salary" label="薪资" />
        <el-table-column prop="city" label="城市" />
        <el-table-column prop="education" label="学历" width="100" />
        <el-table-column prop="experience" label="经验" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '招聘中' : '已结束' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link @click="openDialog(row)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button link type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="total, prev, pager, next" :total="total" v-model:current-page="query.pageNum" @current-change="loadData" style="margin-top: 15px" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑职位' : '发布职位'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="职位名称"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="薪资范围"><el-input v-model="form.salary" placeholder="如: 10k-20k" /></el-form-item>
        <el-form-item label="工作城市"><el-input v-model="form.city" /></el-form-item>
        <el-form-item label="学历要求">
          <el-select v-model="form.education">
            <el-option label="不限" value="不限" />
            <el-option label="大专" value="大专" />
            <el-option label="本科" value="本科" />
            <el-option label="硕士" value="硕士" />
            <el-option label="博士" value="博士" />
          </el-select>
        </el-form-item>
        <el-form-item label="经验要求">
          <el-select v-model="form.experience">
            <el-option label="不限" value="不限" />
            <el-option label="应届生" value="应届生" />
            <el-option label="1-3年" value="1-3年" />
            <el-option label="3-5年" value="3-5年" />
            <el-option label="5年以上" value="5年以上" />
          </el-select>
        </el-form-item>
        <el-form-item label="招聘人数"><el-input-number v-model="form.headcount" :min="1" /></el-form-item>
        <el-form-item label="职位描述"><el-input v-model="form.description" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="任职要求"><el-input v-model="form.requirements" type="textarea" :rows="5" /></el-form-item>
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
import { getJobList, addJob, updateJob, deleteJob } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const query = reactive({ pageNum: 1, pageSize: 10, title: '', city: '' })
const form = reactive({ id: null, title: '', salary: '', city: '', education: '不限', experience: '不限', headcount: 1, description: '', requirements: '' })

const loadData = async () => {
  loading.value = true
  try {
    const res = await getJobList(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  if (row) {
    Object.assign(form, row)
  } else {
    Object.assign(form, { id: null, title: '', salary: '', city: '', education: '不限', experience: '不限', headcount: 1, description: '', requirements: '' })
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (form.id) {
    await updateJob(form)
  } else {
    await addJob(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteJob(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
