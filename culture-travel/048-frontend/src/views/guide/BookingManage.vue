<template>
  <el-card>
    <template #header>我的讲解预约</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="160" />
      <el-table-column prop="username" label="预约用户" />
      <el-table-column prop="visitDate" label="日期" />
      <el-table-column prop="startTime" label="开始时间" />
      <el-table-column prop="duration" label="时长(分钟)" />
      <el-table-column prop="visitorCount" label="人数" />
      <el-table-column prop="language" label="语言" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button v-if="row.status===0" type="success" link @click="handleConfirm(row.id)">确认</el-button>
          <el-button v-if="row.status===1" type="primary" link @click="handleComplete(row.id)">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="current" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getGuideBookings, confirmGuideBooking, completeGuideBooking } from '@/api'
import { ElMessage } from 'element-plus'
const statusMap:Record<number,string> = {0:'待确认',1:'已确认',2:'进行中',3:'已完成',4:'已取消'}
const statusType:Record<number,string> = {0:'warning',1:'primary',2:'info',3:'success',4:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1)
const loadData = async () => { loading.value=true; const res:any = await getGuideBookings({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleConfirm = async (id:number) => { await confirmGuideBooking(id); ElMessage.success('已确认'); loadData() }
const handleComplete = async (id:number) => { await completeGuideBooking(id); ElMessage.success('已完成'); loadData() }
onMounted(loadData)
</script>
