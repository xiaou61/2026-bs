<template>
  <el-card>
    <template #header>编辑研究成果</template>
    <el-form :model="form" label-width="100px" style="max-width:800px">
      <el-form-item label="标题"><el-input v-model="form.title" /></el-form-item>
      <el-form-item label="关联文物"><el-select v-model="form.relicId" filterable placeholder="选择文物"><el-option v-for="r in relics" :key="r.id" :label="r.name" :value="r.id" /></el-select></el-form-item>
      <el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" :rows="2" /></el-form-item>
      <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="10" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">保存</el-button><el-button @click="router.back()">取消</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { updateResearch, getResearchDetail, getRelics } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute()
const router = useRouter()
const form = ref<any>({})
const relics = ref<any[]>([])
onMounted(async () => {
  const [r, d]:any = await Promise.all([getRelics({current:1,size:100}), getResearchDetail(Number(route.params.id))])
  relics.value = r.data.records
  form.value = d.data
})
const handleSubmit = async () => { await updateResearch(form.value); ElMessage.success('保存成功'); router.push('/researcher/research') }
</script>
