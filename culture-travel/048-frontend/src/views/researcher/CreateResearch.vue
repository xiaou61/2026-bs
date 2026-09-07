<template>
  <el-card>
    <template #header>发布研究成果</template>
    <el-form :model="form" label-width="100px" style="max-width:800px">
      <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
      <el-form-item label="关联文物"><el-select v-model="form.relicId" filterable placeholder="选择文物"><el-option v-for="r in relics" :key="r.id" :label="r.name" :value="r.id" /></el-select></el-form-item>
      <el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" :rows="2" /></el-form-item>
      <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="10" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">提交审核</el-button><el-button @click="router.back()">取消</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { saveResearch, getRelics } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const form = ref({ title:'', relicId:null, summary:'', content:'' })
const relics = ref<any[]>([])
onMounted(async () => { const res:any = await getRelics({current:1,size:100}); relics.value = res.data.records })
const handleSubmit = async () => { await saveResearch(form.value); ElMessage.success('提交成功,等待审核'); router.push('/researcher/research') }
</script>
