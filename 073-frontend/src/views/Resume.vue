<template>
  <el-card>
    <template #header>
      <div class="card-header"><span>简历管理</span><el-button type="primary" @click="handleAdd">新增简历</el-button></div>
    </template>
    <el-form :inline="true" :model="query" class="search-form">
      <el-form-item label="姓名"><el-input v-model="query.name" placeholder="请输入" clearable /></el-form-item>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="请选择" clearable><el-option label="待筛选" value="pending" /><el-option label="面试中" value="interview" /><el-option label="已录用" value="passed" /><el-option label="已淘汰" value="rejected" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border stripe>
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="phone" label="手机" width="120" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="education" label="学历" width="120" />
      <el-table-column prop="positionName" label="应聘职位" />
      <el-table-column prop="experience" label="经验" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }"><el-tag :type="{ pending: 'info', interview: 'warning', passed: 'success', rejected: 'danger' }[row.status]">{{ { pending: '待筛选', interview: '面试中', passed: '已录用', rejected: '已淘汰' }[row.status] }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="warning" link @click="handleStatus(row.id, 'interview')" v-if="row.status === 'pending'">面试</el-button>
          <el-button type="success" link @click="handleStatus(row.id, 'passed')" v-if="row.status === 'interview'">录用</el-button>
          <el-button type="danger" link @click="handleStatus(row.id, 'rejected')" v-if="row.status !== 'passed' && row.status !== 'rejected'">淘汰</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, sizes, prev, pager, next" @change="loadData" style="margin-top: 20px" />
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑简历' : '新增简历'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="手机"><el-input v-model="form.phone" /></el-form-item></el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="学历"><el-input v-model="form.education" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="应聘职位" prop="recruitmentId"><el-select v-model="form.recruitmentId" style="width: 100%"><el-option v-for="r in recruitments" :key="r.id" :label="r.positionName + ' - ' + r.departmentName" :value="r.id" /></el-select></el-form-item>
        <el-form-item label="工作经验"><el-input v-model="form.experience" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { resumeApi, recruitmentApi } from '../api'

const query = ref({ name: '', status: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref()
const form = ref({})
const recruitments = ref([])
const rules = { name: [{ required: true, message: '请输入姓名', trigger: 'blur' }], recruitmentId: [{ required: true, message: '请选择职位', trigger: 'change' }] }

const loadData = async () => { const res = await resumeApi.getList(query.value); list.value = res.list; total.value = res.total }
const loadRecruitments = async () => { recruitments.value = await recruitmentApi.getOpen() }
const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }
const handleStatus = async (id, status) => { await resumeApi.updateStatus({ id, status }); ElMessage.success('操作成功'); loadData() }
const handleSubmit = async () => { await formRef.value.validate(); if (form.value.id) { await resumeApi.update(form.value) } else { await resumeApi.add(form.value) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => { loadData(); loadRecruitments() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 15px; }
</style>
