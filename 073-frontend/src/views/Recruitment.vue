<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>招聘需求</span><el-button type="primary" @click="handleAdd">新增需求</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="招聘中" value="open" /><el-option label="已关闭" value="closed" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="positionName" label="职位" />
      <el-table-column prop="departmentName" label="部门" />
      <el-table-column prop="count" label="招聘人数" width="100" />
      <el-table-column prop="salaryRange" label="薪资范围" width="120" />
      <el-table-column prop="requirements" label="要求" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 'open' ? 'success' : 'info'">{{ row.status === 'open' ? '招聘中' : '已关闭' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="warning" link @click="handleClose(row.id)" v-if="row.status === 'open'">关闭</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑需求' : '新增需求'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="职位" prop="positionId"><el-select v-model="form.positionId" style="width: 100%"><el-option v-for="p in positions" :key="p.id" :label="p.name" :value="p.id" /></el-select></el-form-item>
        <el-form-item label="部门" prop="departmentId"><el-select v-model="form.departmentId" style="width: 100%"><el-option v-for="d in departments" :key="d.id" :label="d.name" :value="d.id" /></el-select></el-form-item>
        <el-form-item label="招聘人数"><el-input-number v-model="form.count" :min="1" /></el-form-item>
        <el-form-item label="薪资范围"><el-input v-model="form.salaryRange" placeholder="如：10000-18000" /></el-form-item>
        <el-form-item label="岗位要求"><el-input v-model="form.requirements" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { recruitmentApi, positionApi, departmentApi } from '../api'

const query = ref({ status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const positions = ref([])
const departments = ref([])
const rules = { positionId: [{ required: true, message: '请选择职位', trigger: 'change' }], departmentId: [{ required: true, message: '请选择部门', trigger: 'change' }] }

const loadData = async () => { const res = await recruitmentApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadOptions = async () => { positions.value = await positionApi.getAll(); departments.value = await departmentApi.getAll() }
const handleAdd = () => { form.value = { count: 1 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await recruitmentApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleClose = async (id) => { await ElMessageBox.confirm('确定关闭?', '提示'); await recruitmentApi.close(id); ElMessage.success('关闭成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await recruitmentApi.update(form.value) } else { await recruitmentApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadOptions() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
