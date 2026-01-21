<template>
  <el-card>
    <template #header>预约参观 - {{ exhibition?.title }}</template>
    <el-form :model="form" label-width="100px" style="max-width:600px">
      <el-form-item label="参观日期"><el-date-picker v-model="form.visitDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item label="时间段"><el-select v-model="form.timeSlot"><el-option label="09:00-12:00" value="09:00-12:00" /><el-option label="14:00-17:00" value="14:00-17:00" /></el-select></el-form-item>
      <el-form-item label="参观人数"><el-input-number v-model="form.visitorCount" :min="1" :max="10" /></el-form-item>
      <el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item>
      <el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item>
      <el-form-item label="总金额"><span style="font-size:20px;color:#f56c6c;font-weight:bold">¥{{ totalPrice }}</span></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">提交预约</el-button><el-button @click="router.back()">取消</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getExhibitionDetail, createReservation } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute(), router = useRouter()
const exhibition = ref<any>(null)
const form = ref({ exhibitionId:Number(route.params.exhibitionId), visitDate:'', timeSlot:'09:00-12:00', visitorCount:1, contactName:'', contactPhone:'', totalPrice:0, remark:'' })
const totalPrice = computed(() => (exhibition.value?.ticketPrice || 0) * form.value.visitorCount)
onMounted(async () => { const res:any = await getExhibitionDetail(Number(route.params.exhibitionId)); exhibition.value = res.data })
const handleSubmit = async () => { form.value.totalPrice = totalPrice.value; await createReservation(form.value); ElMessage.success('预约成功'); router.push('/user/reservation') }
</script>
