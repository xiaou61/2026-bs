<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>合同管理</span><el-button type="primary" @click="handleAdd">新增合同</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="员工姓名"><el-input v-model="query.employeeName" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="合同类型"><el-select v-model="query.type" placeholder="请选择" clearable><el-option label="试用期合同" value="试用期合同" /><el-option label="正式合同" value="正式合同" /></el-select></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="生效中" value="active" /><el-option label="已终止" value="terminated" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="contractNo" label="合同编号" width="120" />
      <el-table-column prop="empNo" label="工号" width="100" />
      <el-table-column prop="employeeName" label="员工" width="100" />
      <el-table-column prop="type" label="合同类型" width="100" />
      <el-table-column label="合同期限"><template #default="{ row }">{{ row.startDate?.substring(0, 10) }} 至 {{ row.endDate?.substring(0, 10) }}</template></el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 'active' ? 'success' : 'info'">{{ row.status === 'active' ? '生效中' : '已终止' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="warning" link @click="handleTerminate(row.id)" v-if="row.status === 'active'">终止</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑合同' : '新增合同'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="员工" prop="employeeId"><el-select v-model="form.employeeId" filterable style="width: 100%"><el-option v-for="e in employees" :key="e.id" :label="e.name + ' (' + e.empNo + ')'" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="合同编号"><el-input v-model="form.contractNo" /></el-form-item>
        <el-form-item label="合同类型"><el-select v-model="form.type" style="width: 100%"><el-option label="试用期合同" value="试用期合同" /><el-option label="正式合同" value="正式合同" /></el-select></el-form-item>
        <el-form-item label="开始日期"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="结束日期"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { contractApi, employeeApi } from '../api'

const query = ref({ employeeName: '', type: '', status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const employees = ref([])
const rules = { employeeId: [{ required: true, message: '请选择员工', trigger: 'change' }] }

const loadData = async () => { const res = await contractApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadEmployees = async () => { employees.value = await employeeApi.getAll() }
const handleAdd = () => { form.value = { type: '正式合同' }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await contractApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleTerminate = async (id) => { await ElMessageBox.confirm('确定终止?', '提示'); await contractApi.terminate(id); ElMessage.success('终止成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await contractApi.update(form.value) } else { await contractApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadEmployees() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
