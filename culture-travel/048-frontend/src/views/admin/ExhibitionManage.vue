<template>
  <el-card>
    <template #header><el-button type="primary" @click="showDialog()">添加展览</el-button></template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="hallName" label="展厅" />
      <el-table-column prop="startDate" label="开始日期" />
      <el-table-column prop="endDate" label="结束日期" />
      <el-table-column prop="ticketPrice" label="票价" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="['info','success','warning'][row.status]">{{ ['未开始','进行中','已结束'][row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button type="primary" link @click="showDialog(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑':'添加'" width="600">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="展厅"><el-select v-model="form.hallId"><el-option v-for="h in halls" :key="h.id" :label="h.name" :value="h.id" /></el-select></el-form-item>
        <el-form-item label="开始日期"><el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="结束日期"><el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="票价"><el-input-number v-model="form.ticketPrice" :precision="2" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="form.status"><el-option label="未开始" :value="0" /><el-option label="进行中" :value="1" /><el-option label="已结束" :value="2" /></el-select></el-form-item>
        <el-form-item label="介绍"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminExhibitions, saveExhibition, updateExhibition, deleteExhibition, getAdminHalls } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1), dialogVisible = ref(false), halls = ref<any[]>([])
const form = ref<any>({})
const loadData = async () => { loading.value=true; const res:any = await getAdminExhibitions({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const showDialog = (row?:any) => { form.value = row ? {...row} : {status:0,ticketPrice:0}; dialogVisible.value = true }
const handleSave = async () => { form.value.id ? await updateExhibition(form.value) : await saveExhibition(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
const handleDelete = async (id:number) => { await ElMessageBox.confirm('确定删除?'); await deleteExhibition(id); ElMessage.success('删除成功'); loadData() }
onMounted(async () => { const h:any = await getAdminHalls(); halls.value = h.data; loadData() })
</script>
