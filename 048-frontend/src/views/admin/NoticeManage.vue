<template>
  <el-card>
    <template #header><el-button type="primary" @click="showDialog()">发布公告</el-button></template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="type" label="类型" width="120"><template #default="{row}"><el-tag>{{ typeMap[row.type] }}</el-tag></template></el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="180" />
      <el-table-column prop="status" label="状态" width="100"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'已发布':'草稿' }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button type="primary" link @click="showDialog(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑公告':'发布公告'" width="600">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.type"><el-option label="系统公告" :value="1" /><el-option label="展览通知" :value="2" /><el-option label="闭馆通知" :value="3" /></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="5" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="发布" inactive-text="草稿" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getAdminNotices, saveNotice, updateNotice, deleteNotice } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const typeMap:Record<number,string> = {1:'系统公告',2:'展览通知',3:'闭馆通知'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1), dialogVisible = ref(false)
const form = ref<any>({})
const loadData = async () => { loading.value=true; const res:any = await getAdminNotices({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const showDialog = (row?:any) => { form.value = row ? {...row} : {type:1,status:0,publishTime:new Date().toISOString().slice(0,19).replace('T',' ')}; dialogVisible.value = true }
const handleSave = async () => { form.value.id ? await updateNotice(form.value) : await saveNotice(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
const handleDelete = async (id:number) => { await ElMessageBox.confirm('确定删除?'); await deleteNotice(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
