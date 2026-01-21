<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between"><span>房间管理</span><el-button type="primary" @click="showAdd">添加房间</el-button></div></template>
    <el-table :data="list">
      <el-table-column prop="name" label="房间名称" />
      <el-table-column prop="capacity" label="容纳人数" />
      <el-table-column prop="price" label="基础价格" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="100"><template #default="{row}"><el-button type="primary" link @click="editItem(row)">编辑</el-button></template></el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑房间':'添加房间'" width="400">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="容纳人数"><el-input-number v-model="form.capacity" :min="1" /></el-form-item>
        <el-form-item label="价格"><el-input-number v-model="form.price" :min="0" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyRooms, saveRoom } from '@/api'
import { ElMessage } from 'element-plus'
const list = ref<any[]>([]), dialogVisible = ref(false), form = ref<any>({})
const loadData = async () => { const res:any = await getMyRooms(); list.value = res.data || [] }
const showAdd = () => { form.value = { capacity:6, price:50 }; dialogVisible.value = true }
const editItem = (row:any) => { form.value = {...row}; dialogVisible.value = true }
const handleSave = async () => { await saveRoom(form.value); ElMessage.success('保存成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>
