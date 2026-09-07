<template>
  <el-card>
    <template #header>店铺信息</template>
    <el-form :model="form" label-width="100px" style="max-width:600px">
      <el-form-item label="店铺名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="店铺地址"><el-input v-model="form.address" /></el-form-item>
      <el-form-item label="联系电话"><el-input v-model="form.phone" /></el-form-item>
      <el-form-item label="营业时间"><el-input v-model="form.businessHours" placeholder="如: 14:00-23:00" /></el-form-item>
      <el-form-item label="店铺简介"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSave">保存</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyShop, saveMyShop } from '@/api'
import { ElMessage } from 'element-plus'
const form = ref<any>({})
onMounted(async () => { const res:any = await getMyShop(); if(res.data) form.value = res.data })
const handleSave = async () => { await saveMyShop(form.value); ElMessage.success('保存成功') }
</script>
