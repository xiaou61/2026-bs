<template>
  <el-card>
    <template #header><div style="display:flex;justify-content:space-between"><span>剧本管理</span><el-button type="primary" @click="showAdd">添加剧本</el-button></div></template>
    <el-table :data="list">
      <el-table-column prop="scriptId" label="剧本ID" />
      <el-table-column prop="price" label="定价" />
      <el-table-column prop="playCount" label="开本次数" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="row.status===1?'success':'info'">{{ row.status===1?'上架':'下架' }}</el-tag></template></el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" title="添加剧本" width="400">
      <el-form :model="form" label-width="80px">
        <el-form-item label="剧本ID"><el-input-number v-model="form.scriptId" :min="1" /></el-form-item>
        <el-form-item label="定价"><el-input-number v-model="form.price" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getOwnerScripts, addShopScript } from '@/api'
import { ElMessage } from 'element-plus'
const list = ref<any[]>([]), dialogVisible = ref(false), form = ref<any>({})
const loadData = async () => { const res:any = await getOwnerScripts(); list.value = res.data || [] }
const showAdd = () => { form.value = { price:200 }; dialogVisible.value = true }
const handleSave = async () => { await addShopScript(form.value); ElMessage.success('添加成功'); dialogVisible.value = false; loadData() }
onMounted(loadData)
</script>
