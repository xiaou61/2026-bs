<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>薪资管理</span><el-button type="primary" @click="handleAdd">新增薪资</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="员工姓名"><el-input v-model="query.employeeName" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="月份"><el-date-picker v-model="query.yearMonth" type="month" value-format="YYYY-MM" placeholder="选择月份" /></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="待发放" value="pending" /><el-option label="已发放" value="paid" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="empNo" label="工号" width="100" />
      <el-table-column prop="employeeName" label="姓名" width="100" />
      <el-table-column prop="yearMonth" label="月份" width="100" />
      <el-table-column prop="baseSalary" label="基本工资" width="100" />
      <el-table-column prop="performance" label="绩效" width="100" />
      <el-table-column prop="allowance" label="补贴" width="100" />
      <el-table-column prop="deduction" label="扣除" width="100" />
      <el-table-column prop="actualSalary" label="实发" width="100" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 'paid' ? 'success' : 'warning'">{{ row.status === 'paid' ? '已发放' : '待发放' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="success" link @click="handlePay(row.id)" v-if="row.status === 'pending'">发放</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑薪资' : '新增薪资'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="员工" prop="employeeId"><el-select v-model="form.employeeId" filterable style="width: 100%"><el-option v-for="e in employees" :key="e.id" :label="e.name + ' (' + e.empNo + ')'" :value="e.id" /></el-select></el-form-item>
        <el-form-item label="月份" prop="yearMonth"><el-date-picker v-model="form.yearMonth" type="month" value-format="YYYY-MM" style="width: 100%" /></el-form-item>
        <el-form-item label="基本工资"><el-input-number v-model="form.baseSalary" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="绩效"><el-input-number v-model="form.performance" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="补贴"><el-input-number v-model="form.allowance" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="扣除"><el-input-number v-model="form.deduction" :min="0" style="width: 100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { salaryApi, employeeApi } from '../api'

const query = ref({ employeeName: '', yearMonth: '', status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const employees = ref([])
const rules = { employeeId: [{ required: true, message: '请选择员工', trigger: 'change' }], yearMonth: [{ required: true, message: '请选择月份', trigger: 'change' }] }

const loadData = async () => { const res = await salaryApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadEmployees = async () => { employees.value = await employeeApi.getAll() }
const handleAdd = () => { form.value = { baseSalary: 0, performance: 0, allowance: 0, deduction: 0 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await salaryApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handlePay = async (id) => { await ElMessageBox.confirm('确定发放?', '提示'); await salaryApi.pay(id); ElMessage.success('发放成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await salaryApi.update(form.value) } else { await salaryApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadEmployees() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
