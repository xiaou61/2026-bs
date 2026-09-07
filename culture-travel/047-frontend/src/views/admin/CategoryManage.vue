<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between"><span>分类管理</span><el-button type="primary" @click="showAdd">添加分类</el-button></div></template>
    <el-table :data="list">
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="sort" label="排序" />
      <el-table-column label="操作" width="100"><template #default="{row}"><el-button type="primary" link @click="editItem(row)">编辑</el-button></template></el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑分类':'添加分类'" width="400">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminCategories, saveCategory } from '@/api'
import { ElMessage } from 'element-plus'
const list = ref<any[]>([]), dialogVisible = ref(false), form = ref<any>({})
const loadData = async () => { const res:any=await getAdminCategories(); list.value=res.data }
const showAdd = () => { form.value={sort:0}; dialogVisible.value=true }
const editItem = (row:any) => { form.value={...row}; dialogVisible.value=true }
const handleSave = async () => { await saveCategory(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
onMounted(loadData)
</script>
