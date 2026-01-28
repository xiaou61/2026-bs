<template>
  <div class="page-container">
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="地区">
          <el-input v-model="queryParams.region" placeholder="请输入地区" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <div style="margin-bottom: 15px"><el-button type="primary" @click="handleAdd">发布预警</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="pestName" label="病虫害名称" />
        <el-table-column prop="region" label="影响地区" />
        <el-table-column prop="warningLevel" label="预警级别" width="100">
          <template #default="{ row }">
            <el-tag :type="['', 'success', 'warning', 'danger'][row.warningLevel]">{{ ['', '轻度', '中度', '重度'][row.warningLevel] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="total, prev, pager, next" @change="loadData" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="病虫害ID" prop="pestId"><el-input-number v-model="form.pestId" :min="1" /></el-form-item>
        <el-form-item label="影响地区" prop="region"><el-input v-model="form.region" /></el-form-item>
        <el-form-item label="预警级别" prop="warningLevel">
          <el-radio-group v-model="form.warningLevel"><el-radio :value="1">轻度</el-radio><el-radio :value="2">中度</el-radio><el-radio :value="3">重度</el-radio></el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getWarningPage, addWarning, updateWarning, deleteWarning } from '@/api/pest'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), dialogTitle = ref(''), formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10, region: '', warningLevel: null }), form = ref({})
const loadData = async () => { loading.value = true; try { const res = await getWarningPage(queryParams); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { dialogTitle.value = '发布预警'; form.value = { warningLevel: 1 }; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑预警'; form.value = { ...row }; dialogVisible.value = true }
const handleDelete = async (row) => { await ElMessageBox.confirm('确定删除?'); await deleteWarning(row.id); ElMessage.success('删除成功'); loadData() }
const submitForm = async () => { form.value.id ? await updateWarning(form.value) : await addWarning(form.value); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => loadData())
</script>