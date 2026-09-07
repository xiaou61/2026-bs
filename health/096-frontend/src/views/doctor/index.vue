<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.departmentId" placeholder="所属科室" clearable style="width: 180px">
          <el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 140px">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
        <el-input v-model="query.keyword" placeholder="医生姓名/职称/擅长方向" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增医生</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="doctorName" label="医生姓名" />
        <el-table-column prop="departmentName" label="科室" />
        <el-table-column prop="title" label="职称" />
        <el-table-column prop="specialty" label="擅长方向" min-width="220" show-overflow-tooltip />
        <el-table-column prop="consultationFee" label="挂号费" />
        <el-table-column prop="username" label="登录账号" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除该医生吗？" @confirm="handleDelete(row.id)">
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
        style="margin-top: 16px"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑医生' : '新增医生'" width="700px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="95px">
        <el-row :gutter="12">
          <el-col :span="12"><el-form-item label="医生姓名" prop="doctorName"><el-input v-model="form.doctorName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="所属科室" prop="departmentId">
            <el-select v-model="form.departmentId" style="width: 100%">
              <el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item></el-col>
          <el-col :span="12"><el-form-item label="职称"><el-input v-model="form.title" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="挂号费"><el-input-number v-model="form.consultationFee" :min="0" :precision="2" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="登录账号" prop="username"><el-input v-model="form.username" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="登录密码"><el-input v-model="form.password" type="password" show-password /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="状态">
            <el-radio-group v-model="form.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">停用</el-radio>
            </el-radio-group>
          </el-form-item></el-col>
          <el-col :span="24"><el-form-item label="擅长方向"><el-input v-model="form.specialty" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="医生简介"><el-input v-model="form.introduction" type="textarea" :rows="4" /></el-form-item></el-col>
        </el-row>
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
import { ElMessage } from 'element-plus'
import { addDoctor, deleteDoctor, getDepartmentEnabled, getDoctorPage, updateDoctor } from '../../api'

const loading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)
const departments = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, departmentId: undefined, keyword: '', status: undefined })
const form = reactive({
  id: null,
  doctorName: '',
  departmentId: null,
  title: '',
  consultationFee: 20,
  username: '',
  password: '',
  phone: '',
  email: '',
  status: 1,
  specialty: '',
  introduction: ''
})

const rules = {
  doctorName: [{ required: true, message: '请输入医生姓名', trigger: 'blur' }],
  departmentId: [{ required: true, message: '请选择所属科室', trigger: 'change' }],
  username: [{ required: true, message: '请输入登录账号', trigger: 'blur' }]
}

const resetForm = () => {
  Object.assign(form, { id: null, doctorName: '', departmentId: null, title: '', consultationFee: 20, username: '', password: '', phone: '', email: '', status: 1, specialty: '', introduction: '' })
}

const loadDepartments = async () => {
  const res = await getDepartmentEnabled()
  departments.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDoctorPage(query)
    tableData.value = res.data.list || []
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
  Object.assign(form, { ...row, password: '' })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateDoctor(form)
  } else {
    await addDoctor(form)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteDoctor(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadDepartments()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 4px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 16px; }
</style>
