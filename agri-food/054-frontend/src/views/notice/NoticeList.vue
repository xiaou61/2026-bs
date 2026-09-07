<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="标题"><el-input v-model="queryParams.title" placeholder="请输入标题" clearable /></el-form-item>
        <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <div style="margin-bottom: 15px"><el-button type="primary" @click="handleAdd">发布公告</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已发布' : '草稿' }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="total, prev, pager, next" @change="loadData" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="10" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status"><el-radio :value="0">草稿</el-radio><el-radio :value="1">发布</el-radio></el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getNoticePage, addNotice, updateNotice, deleteNotice } from '@/api/notice'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), dialogTitle = ref(''), formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, title: '' }), form = ref({})
const loadData = async () => { loading.value = true; try { const res = await getNoticePage(queryParams); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { dialogTitle.value = '发布公告'; form.value = { status: 0 }; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑公告'; form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deleteNotice(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => { form.value.id ? await updateNotice(form.value) : await addNotice(form.value); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => loadData())
</script>