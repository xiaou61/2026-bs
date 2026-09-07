<template>
  <el-card>
    <template #header>创作新剧本</template>
    <el-form :model="form" label-width="100px" style="max-width:700px">
      <el-form-item label="剧本名称"><el-input v-model="form.title" /></el-form-item>
      <el-form-item label="分类"><el-select v-model="form.categoryId"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" /></el-select></el-form-item>
      <el-form-item label="类型"><el-select v-model="form.type"><el-option label="情感" :value="1" /><el-option label="推理" :value="2" /><el-option label="恐怖" :value="3" /><el-option label="欢乐" :value="4" /><el-option label="机制" :value="5" /></el-select></el-form-item>
      <el-form-item label="难度"><el-select v-model="form.difficulty"><el-option label="新手" :value="1" /><el-option label="进阶" :value="2" /><el-option label="硬核" :value="3" /></el-select></el-form-item>
      <el-form-item label="适合人数"><el-input v-model="form.playerCount" placeholder="如: 6人、5-7人" /></el-form-item>
      <el-form-item label="游戏时长"><el-input v-model="form.duration" placeholder="如: 4-5小时" /></el-form-item>
      <el-form-item label="授权价格"><el-input-number v-model="form.price" :min="0" /></el-form-item>
      <el-form-item label="剧本简介"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">提交审核</el-button><el-button @click="router.back()">取消</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategories, createScript } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const categories = ref<any[]>([])
const form = ref({ title:'', categoryId:null, type:1, difficulty:2, playerCount:'', duration:'', price:2000, description:'' })
onMounted(async () => { const res:any = await getCategories(); categories.value = res.data })
const handleSubmit = async () => { await createScript(form.value); ElMessage.success('提交成功'); router.push('/author/script') }
</script>
