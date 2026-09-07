<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>职位管理</span><el-button type="primary" @click="handleAdd">新增职位</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="职位名称"><el-input v-model="query.name" placeholder="请输入" clearable /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="职位名称" />
      <el-table-column prop="level" label="职级" width="80" />
      <el-table-column label="薪资范围"><template #default="{ row }">{{ row.minSalary }} - {{ row.maxSalary }}</template></el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '停用' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑职位' : '新增职位'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="职位名称" prop="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="职级"><el-input-number v-model="form.level" :min="1" :max="10" /></el-form-item>
        <el-form-item label="最低薪资"><el-input-number v-model="form.minSalary" :min="0" /></el-form-item>
        <el-form-item label="最高薪资"><el-input-number v-model="form.maxSalary" :min="0" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio :label="1">正常</el-radio><el-radio :label="0">停用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { positionApi } from '../api'

const query = ref({ name: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const rules = { name: [{ required: true, message: '请输入职位名称', trigger: 'blur' }] }

const loadData = async () => { const res = await positionApi.getList(query.value); list.value = res.list; total.value = res.total }
const handleAdd = () => { form.value = { status: 1, level: 1 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await positionApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await positionApi.update(form.value) } else { await positionApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
