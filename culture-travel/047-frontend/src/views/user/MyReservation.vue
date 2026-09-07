<template>
  <el-card>
    <template #header>我的预约</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="160" />
      <el-table-column prop="reservationDate" label="预约日期" />
      <el-table-column prop="timeSlot" label="时间段" />
      <el-table-column prop="playerCount" label="人数" />
      <el-table-column prop="totalPrice" label="金额" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{row}"><el-button v-if="row.status===0" type="danger" link @click="handleCancel(row.id)">取消</el-button></template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getMyReservations, cancelReservation } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const statusMap:Record<number,string> = {0:'待确认',1:'已确认',2:'进行中',3:'已完成',4:'已取消'}
const statusType:Record<number,string> = {0:'warning',1:'primary',2:'info',3:'success',4:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:10 })
const loadData = async () => { loading.value=true; const res:any=await getMyReservations(query); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleCancel = async (id:number) => { await ElMessageBox.confirm('确定取消?'); await cancelReservation(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
