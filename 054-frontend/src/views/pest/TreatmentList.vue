<template>
  <div class="page-container">
    <div class="table-container">
      <div style="margin-bottom: 15px"><el-button type="primary" @click="handleAdd">添加防治记录</el-button></div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="planId" label="计划ID" width="100" />
        <el-table-column prop="pestName" label="病虫害" />
        <el-table-column prop="treatmentMethod" label="防治方法" show-overflow-tooltip />
        <el-table-column prop="treatmentDate" label="防治日期" width="120" />
        <el-table-column prop="effect" label="效果" width="100" />
        <el-table-column label="操作" width="100"><template #default="{ row }"><el-button link type="primary" @click="handleEdit(row)">编辑</el-button></template></el-table-column>
      </el-table>
      <div class="pagination-container"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="total, prev, pager, next" @change="loadData" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="计划ID"><el-input-number v-model="form.planId" :min="1" /></el-form-item>
        <el-form-item label="病虫害ID"><el-input-number v-model="form.pestId" :min="1" /></el-form-item>
        <el-form-item label="防治方法"><el-input v-model="form.treatmentMethod" type="textarea" /></el-form-item>
        <el-form-item label="防治日期"><el-date-picker v-model="form.treatmentDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" /></el-form-item>
        <el-form-item label="效果"><el-input v-model="form.effect" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="submitForm">确定</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getTreatmentPage, addTreatment, updateTreatment } from '@/api/pest'
import { ElMessage } from 'element-plus'
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), dialogTitle = ref(''), formRef = ref()
const queryParams = reactive({ pageNum: 1, pageSize: 10 }), form = ref({})
const loadData = async () => { loading.value = true; try { const res = await getTreatmentPage(queryParams); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { dialogTitle.value = '添加防治记录'; form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { dialogTitle.value = '编辑防治记录'; form.value = { ...row }; dialogVisible.value = true }
const submitForm = async () => { form.value.id ? await updateTreatment(form.value) : await addTreatment(form.value); ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
onMounted(() => loadData())
</script>