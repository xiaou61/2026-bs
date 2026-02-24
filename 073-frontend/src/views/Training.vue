<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>培训管理</span><el-button type="primary" @click="handleAdd" v-if="userStore.user?.role !== 'employee'">新增培训</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="培训主题"><el-input v-model="query.title" placeholder="请输入" clearable /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="title" label="培训主题" />
      <el-table-column prop="trainer" label="讲师" width="100" />
      <el-table-column label="培训时间"><template #default="{ row }">{{ row.startTime?.substring(0, 16) }} 至 {{ row.endTime?.substring(0, 16) }}</template></el-table-column>
      <el-table-column prop="location" label="地点" width="120" />
      <el-table-column prop="maxCount" label="人数上限" width="100" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '进行中' : '已结束' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="150" v-if="userStore.user?.role !== 'employee'">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑培训' : '新增培训'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="培训主题" prop="title"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="讲师"><el-input v-model="form.trainer" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="开始时间"><el-date-picker v-model="form.startTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="结束时间"><el-date-picker v-model="form.endTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="地点"><el-input v-model="form.location" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="人数上限"><el-input-number v-model="form.maxCount" :min="1" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="培训内容"><el-input v-model="form.content" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { trainingApi } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const query = ref({ title: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const rules = { title: [{ required: true, message: '请输入培训主题', trigger: 'blur' }] }

const loadData = async () => { const res = await trainingApi.getList(query.value); list.value = res.list; total.value = res.total }
const handleAdd = () => { form.value = { maxCount: 50 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (id) => { await ElMessageBox.confirm('确定删除?', '提示'); await trainingApi.delete(id); ElMessage.success('删除成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await trainingApi.update(form.value) } else { await trainingApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
