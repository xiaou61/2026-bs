<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>员工管理</span><el-button type="primary" @click="handleAdd">新增员工</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="姓名"><el-input v-model="query.name" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="工号"><el-input v-model="query.empNo" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="部门"><el-select v-model="query.departmentId" placeholder="请选择" clearable><el-option v-for="d in departments" :key="d.id" :label="d.name" :value="d.id" /></el-select></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="试用期" value="trial" /><el-option label="正式" value="regular" /><el-option label="已离职" value="resigned" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="empNo" label="工号" width="100" />
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="gender" label="性别" width="60" />
      <el-table-column prop="phone" label="手机" width="120" />
      <el-table-column prop="departmentName" label="部门" />
      <el-table-column prop="positionName" label="职位" />
      <el-table-column prop="entryDate" label="入职日期" width="100"><template #default="{ row }">{{ row.entryDate?.substring(0, 10) }}</template></el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 'regular' ? 'success' : row.status === 'trial' ? 'warning' : 'info'">{{ { trial: '试用期', regular: '正式', resigned: '已离职' }[row.status] }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑员工' : '新增员工'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="性别"><el-radio-group v-model="form.gender"><el-radio label="男">男</el-radio><el-radio label="女">女</el-radio></el-radio-group></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="手机"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="部门"><el-select v-model="form.departmentId" style="width: 100%"><el-option v-for="d in departments" :key="d.id" :label="d.name" :value="d.id" /></el-select></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="职位"><el-select v-model="form.positionId" style="width: 100%"><el-option v-for="p in positions" :key="p.id" :label="p.name" :value="p.id" /></el-select></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="入职日期"><el-date-picker v-model="form.entryDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="状态"><el-select v-model="form.status" style="width: 100%"><el-option label="试用期" value="trial" /><el-option label="正式" value="regular" /><el-option label="已离职" value="resigned" /></el-select></el-form-item></el-col>
        </el-row>
        <el-form-item label="身份证"><el-input v-model="form.idCard" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { employeeApi, departmentApi, positionApi } from '../api'

const query = ref({ name: '', empNo: '', departmentId: null, status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const departments = ref([])
const positions = ref([])
const rules = { name: [{ required: true, message: '请输入姓名', trigger: 'blur' }] }

const loadData = async () => { const res = await employeeApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadOptions = async () => { departments.value = await departmentApi.getAll(); positions.value = await positionApi.getAll() }
const handleAdd = () => { form.value = { status: 'trial', gender: '男' }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await employeeApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await employeeApi.update(form.value) } else { await employeeApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadOptions() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
