<template>
  <el-card>
    <template #header><el-button type="primary" @click="showDialog()">添加展厅</el-button></template>
    <el-table :data="list">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="floor" label="楼层" />
      <el-table-column prop="area" label="面积(㎡)" />
      <el-table-column prop="capacity" label="容纳人数" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'开放':'关闭' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button type="primary" link @click="showDialog(row)">编辑</el-button></template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑':'添加'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="楼层"><el-input-number v-model="form.floor" /></el-form-item>
        <el-form-item label="面积"><el-input-number v-model="form.area" /></el-form-item>
        <el-form-item label="容纳人数"><el-input-number v-model="form.capacity" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminHalls, saveHall, updateHall } from '@/api'
import { ElMessage } from 'element-plus'
const list = ref<any[]>([]), dialogVisible = ref(false), form = ref<any>({})
const loadData = async () => { const res:any = await getAdminHalls(); list.value = res.data }
const showDialog = (row?:any) => { form.value = row ? {...row} : {floor:1,status:1}; dialogVisible.value = true }
const handleSave = async () => { form.value.id ? await updateHall(form.value) : await saveHall(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
onMounted(loadData)
</script>
