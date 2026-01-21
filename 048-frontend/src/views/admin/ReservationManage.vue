<template>
  <el-card>
    <template #header>参观预约管理</template>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="160" />
      <el-table-column prop="username" label="用户" />
      <el-table-column prop="exhibitionTitle" label="展览" />
      <el-table-column prop="visitDate" label="参观日期" />
      <el-table-column prop="visitorCount" label="人数" width="80" />
      <el-table-column prop="totalPrice" label="金额" width="80" />
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
import { getAdminReservations, confirmReservation, completeReservation } from '@/api'
import { ElMessage } from 'element-plus'
const statusMap:Record<number,string> = {0:'待确认',1:'已确认',2:'已完成',3:'已取消'}
const statusType:Record<number,string> = {0:'warning',1:'primary',2:'success',3:'info'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0), current = ref(1)
const loadData = async () => { loading.value=true; const res:any = await getAdminReservations({current:current.value,size:10}); list.value=res.data.records; total.value=res.data.total; loading.value=false }
const handleConfirm = async (id:number) => { await confirmReservation(id); ElMessage.success('已确认'); loadData() }
const handleComplete = async (id:number) => { await completeReservation(id); ElMessage.success('已完成'); loadData() }
onMounted(loadData)
</script>
