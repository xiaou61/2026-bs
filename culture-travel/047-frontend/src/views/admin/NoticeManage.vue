<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between"><span>公告管理</span><el-button type="primary" @click="showAdd">发布公告</el-button></div></template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="type" label="类型"><template #default="{row}">{{ row.type===1?'系统公告':'活动通知' }}</template></el-table-column>
      <el-table-column prop="publishTime" label="发布时间" />
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button type="primary" link @click="editItem(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    <el-dialog v-model="dialogVisible" :title="form.id?'编辑公告':'发布公告'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.type"><el-option label="系统公告" :value="1" /><el-option label="活动通知" :value="2" /></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminNotices, saveNotice, deleteNotice } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false), list = ref<any[]>([]), total = ref(0), dialogVisible = ref(false), form = ref<any>({})
const query = reactive({ current:1, size:10 })
const loadData = async () => { loading.value=true; const res:any=await getAdminNotices(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const showAdd = () => { form.value={type:1}; dialogVisible.value=true }
const editItem = (row:any) => { form.value={...row}; dialogVisible.value=true }
const handleSave = async () => { await saveNotice(form.value); ElMessage.success('保存成功'); dialogVisible.value=false; loadData() }
const handleDelete = async (id:number) => { await ElMessageBox.confirm('确定删除?'); await deleteNotice(id); ElMessage.success('删除成功'); loadData() }
onMounted(loadData)
</script>
