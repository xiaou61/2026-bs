<template>
  <el-card>
    <template #header><el-button type="primary" @click="showDialog()">添加朝代</el-button></template>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="startYear" label="起始年份" />
      <el-table-column prop="endYear" label="结束年份" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button type="primary" link @click="showDialog(row)">编辑</el-button></template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑':'添加'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="起始年份"><el-input v-model="form.startYear" /></el-form-item>
        <el-form-item label="结束年份"><el-input v-model="form.endYear" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminDynasties, saveDynasty, updateDynasty } from '@/api'
import { ElMessage } from 'element-plus'
const list = ref<any[]>([]), dialogVisible = ref(false), form = ref<any>({})
const loadData = async () => { const res:any = await getAdminDynasties(); list.value = res.data }
const showDialog = (row?:any) => { form.value = row ? {...row} : {sort:0}; dialogVisible.value = true }
const handleSave = async () => { form.value.id ? await updateDynasty(form.value) : await saveDynasty(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
onMounted(loadData)
</script>
