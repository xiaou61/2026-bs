<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.departmentId" placeholder="选择院系" clearable style="width: 200px">
          <el-option v-for="d in deptList" :key="d.id" :label="d.name" :value="d.id" />
        </el-select>
        <el-input v-model="query.name" placeholder="专业名称" clearable style="width: 200px" />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="name" label="专业名称" />
        <el-table-column prop="code" label="专业代码" />
        <el-table-column prop="departmentName" label="所属院系" />
        <el-table-column prop="degree" label="学位类型" width="100" />
        <el-table-column prop="duration" label="学制" width="80" />
        <el-table-column prop="tuition" label="学费" width="100" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
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
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑专业' : '新增专业'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="名称" prop="name"><el-input v-model="form.name" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代码"><el-input v-model="form.code" /></el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属院系" prop="departmentId">
              <el-select v-model="form.departmentId" style="width: 100%">
                <el-option v-for="d in deptList" :key="d.id" :label="d.name" :value="d.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学位类型">
              <el-select v-model="form.degree" style="width: 100%">
                <el-option label="本科" value="本科" />
                <el-option label="专科" value="专科" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="学制"><el-input-number v-model="form.duration" :min="1" :max="8" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="学费"><el-input-number v-model="form.tuition" :min="0" :precision="2" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="专业介绍"><el-input v-model="form.description" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="报考要求"><el-input v-model="form.requirement" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :value="1">启用</el-radio><el-radio :value="0">禁用</el-radio></el-radio-group></el-form-item>
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
import { getMajorPage, addMajor, updateMajor, deleteMajor, getDepartmentList } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const deptList = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, name: '', departmentId: null })
const form = reactive({ id: null, name: '', code: '', departmentId: null, degree: '本科', duration: 4, tuition: 5000, description: '', requirement: '', status: 1 })
const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  departmentId: [{ required: true, message: '请选择院系', trigger: 'change' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMajorPage(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadDept = async () => {
  const res = await getDepartmentList()
  deptList.value = res.data
}

const handleAdd = () => {
  Object.assign(form, { id: null, name: '', code: '', departmentId: null, degree: '本科', duration: 4, tuition: 5000, description: '', requirement: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  form.id ? await updateMajor(form) : await addMajor(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteMajor(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => { loadDept(); loadData() })
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
</style>
