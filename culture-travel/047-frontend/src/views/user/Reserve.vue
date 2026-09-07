<template>
  <el-card>
    <template #header>预约 - {{ shop?.name }}</template>
    <el-form :model="form" label-width="100px" style="max-width:600px">
      <el-form-item label="选择房间"><el-select v-model="form.roomId" placeholder="请选择"><el-option v-for="r in rooms" :key="r.id" :label="`${r.name} (${r.capacity}人)`" :value="r.id" /></el-select></el-form-item>
      <el-form-item label="预约日期"><el-date-picker v-model="form.reservationDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
      <el-form-item label="时间段"><el-select v-model="form.timeSlot"><el-option label="14:00-18:00" value="14:00-18:00" /><el-option label="19:00-23:00" value="19:00-23:00" /></el-select></el-form-item>
      <el-form-item label="玩家人数"><el-input-number v-model="form.playerCount" :min="1" :max="10" /></el-form-item>
      <el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item>
      <el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      <el-form-item><el-button type="primary" @click="handleSubmit">提交预约</el-button><el-button @click="router.back()">取消</el-button></el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getShopDetail, getShopRooms, createReservation } from '@/api'
import { ElMessage } from 'element-plus'
const route = useRoute(), router = useRouter()
const shop = ref<any>(null), rooms = ref<any[]>([])
const form = ref({ shopId:Number(route.params.shopId), roomId:null, scriptId:null, reservationDate:'', timeSlot:'14:00-18:00', playerCount:6, contactName:'', contactPhone:'', totalPrice:0, remark:'' })
onMounted(async () => {
  const id = Number(route.params.shopId)
  const [s, r]:any = await Promise.all([getShopDetail(id), getShopRooms(id)])
  shop.value = s.data; rooms.value = r.data
})
const handleSubmit = async () => { await createReservation(form.value); ElMessage.success('预约成功'); router.push('/user/reservation') }
</script>
