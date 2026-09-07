<template>
  <el-card>
    <template #header>预约讲解服务</template>
    <el-form :model="form" label-width="100px" style="max-width:600px">
      <el-form-item label="选择讲解员"><el-select v-model="form.guideId" placeholder="请选择"><el-option v-for="g in guides" :key="g.id" :label="g.realName" :value="g.id" /></el-select></el-form-item>
      <el-form-item label="预约日期"><el-date-picker v-model="form.visitDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item label="开始时间"><el-time-picker v-model="form.startTime" format="HH:mm" value-format="HH:mm:ss" /></el-form-item>
      <el-form-item label="时长(分钟)"><el-select v-model="form.duration"><el-option label="60分钟" :value="60" /><el-option label="90分钟" :value="90" /><el-option label="120分钟" :value="120" /></el-select></el-form-item>
      <el-form-item label="人数"><el-input-number v-model="form.visitorCount" :min="1" :max="20" /></el-form-item>
      <el-form-item label="语言"><el-select v-model="form.language"><el-option label="中文" value="中文" /><el-option label="英文" value="英文" /></el-select></el-form-item>
      <el-form-item label="价格"><el-input-number v-model="form.price" :precision="2" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">提交预约</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getGuides, createGuideBooking } from '@/api'
import { ElMessage } from 'element-plus'
const router = useRouter()
const guides = ref<any[]>([])
const form = ref({ guideId:null, visitDate:'', startTime:'10:00:00', duration:60, visitorCount:1, language:'中文', price:200, remark:'' })
onMounted(async () => { const res:any = await getGuides(); guides.value = res.data })
const handleSubmit = async () => { await createGuideBooking(form.value); ElMessage.success('预约成功'); router.push('/user/reservation') }
</script>
