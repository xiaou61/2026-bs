<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="物料名称" style="width:200px" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="success" @click="handleAdd">新增</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="code" label="物料编号" width="120" />
        <el-table-column prop="name" label="物料名称" />
        <el-table-column prop="spec" label="规格" />
        <el-table-column prop="unit" label="单位" width="70" />
        <el-table-column prop="stockQuantity" label="库存" width="100">
          <template #default="{row}">
            <span :style="{color: row.stockQuantity <= row.safeStock ? '#f56c6c' : ''}">{{ row.stockQuantity }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="safeStock" label="安全库存" width="100" />
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)"><template #reference><el-button link type="danger">删除</el-button></template></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" style="margin-top:15px" @current-change="loadData" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑物料':'新增物料'" width="500px">
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="物料编号" prop="code" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.code" /></el-form-item>
        <el-form-item label="物料名称" prop="name" :rules="[{required:true,message:'请输入'}]"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="规格"><el-input v-model="form.spec" /></el-form-item>
        <el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item>
        <el-form-item label="安全库存"><el-input-number v-model="form.safeStock" :min="0" :precision="2" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMaterialPage, addMaterial, updateMaterial, deleteMaterial } from '../../api'

const loading = ref(false); const tableData = ref([]); const total = ref(0); const dialogVisible = ref(false); const formRef = ref()
const query = reactive({ pageNum: 1, pageSize: 10, name: '' })
const form = reactive({ id: null, code: '', name: '', spec: '', unit: '', safeStock: 0 })

const loadData = async () => { loading.value = true; try { const res = await getMaterialPage(query); tableData.value = res.data.records; total.value = res.data.total } finally { loading.value = false } }
const handleAdd = () => { Object.assign(form, { id: null, code: '', name: '', spec: '', unit: '', safeStock: 0 }); dialogVisible.value = true }
const handleEdit = (row) => { Object.assign(form, row); dialogVisible.value = true }
const handleSubmit = async () => { await formRef.value.validate(); if (form.id) { await updateMaterial(form) } else { await addMaterial(form) }; ElMessage.success('操作成功'); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await deleteMaterial(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>

<style scoped>.page-container { padding: 10px; } .search-bar { display: flex; gap: 10px; margin-bottom: 15px; }</style>
