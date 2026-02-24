<template>
  <el-card>
    <div style="margin-bottom:15px"><el-button type="success" @click="handleAdd">新增规则</el-button></div>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="name" label="规则名称" />
      <el-table-column label="车型" width="100"><template #default="{ row }"><el-tag>{{ row.bikeType === 1 ? '普通单车' : '电动单车' }}</el-tag></template></el-table-column>
      <el-table-column prop="basePrice" label="基础价(元)" width="100" />
      <el-table-column prop="baseDuration" label="基础时长(分)" width="110" />
      <el-table-column prop="extraPrice" label="超时价(元/30分)" width="130" />
      <el-table-column prop="dailyCap" label="每日封顶(元)" width="110" />
      <el-table-column label="状态" width="80"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">{{ row.status === 1 ? '启用' : '停用' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑规则' : '新增规则'" width="500px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="规则名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="车型"><el-select v-model="form.bikeType" style="width:100%"><el-option label="普通单车" :value="1" /><el-option label="电动单车" :value="2" /></el-select></el-form-item>
        <el-form-item label="基础价(元)"><el-input-number v-model="form.basePrice" :min="0" :precision="2" :step="0.5" /></el-form-item>
        <el-form-item label="基础时长(分)"><el-input-number v-model="form.baseDuration" :min="1" /></el-form-item>
        <el-form-item label="超时价(元)"><el-input-number v-model="form.extraPrice" :min="0" :precision="2" :step="0.5" /></el-form-item>
        <el-form-item label="每日封顶(元)"><el-input-number v-model="form.dailyCap" :min="0" :precision="2" :step="5" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getPricingList, addPricing, updatePricing, deletePricing } from '../../api'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const form = reactive({ id: null, name: '', bikeType: 1, basePrice: 1.5, baseDuration: 30, extraPrice: 1.0, dailyCap: 20, status: 1 })

const loadData = async () => { loading.value = true; try { const res = await getPricingList(); tableData.value = res.data } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, name: '', bikeType: 1, basePrice: 1.5, baseDuration: 30, extraPrice: 1.0, dailyCap: 20, status: 1 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { if (form.id) { await updatePricing(form.id, form) } else { await addPricing(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deletePricing(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
