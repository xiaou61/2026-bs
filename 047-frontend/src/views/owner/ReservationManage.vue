<template>
  <el-card>
    <template #header>预约管理</template>
    <el-form inline>
      <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部" clearable @change="loadData">
        <el-option label="待确认" :value="0" /><el-option label="已确认" :value="1" /><el-option label="已完成" :value="3" /><el-option label="已取消" :value="4" />
      </el-select></el-form-item>
    </el-form>
    <el-table :data="list" v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="160" />
      <el-table-column prop="contactName" label="联系人" />
      <el-table-column prop="contactPhone" label="电话" />
      <el-table-column prop="reservationDate" label="预约日期" />
      <el-table-column prop="timeSlot" label="时间段" />
      <el-table-column prop="playerCount" label="人数" />
      <el-table-column prop="totalPrice" label="金额" />
      <el-table-column prop="status" label="状态"><template #default="{row}"><el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-button v-if="row.status===0" type="success" link @click="handleConfirm(row.id)">确认</el-button>
          <el-button v-if="row.status===1" type="primary" link @click="handleComplete(row.id)">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top:16px" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getShopReservations, confirmReservation, completeReservation } from '@/api'
import { ElMessage } from 'element-plus'
const statusMap:Record<number,string> = {0:'待确认',1:'已确认',2:'进行中',3:'已完成',4:'已取消'}
const statusType:Record<number,string> = {0:'warning',1:'primary',2:'info',3:'success',4:'danger'}
const loading = ref(false), list = ref<any[]>([]), total = ref(0)
const query = reactive({ current:1, size:10, status:undefined as number|undefined })
const loadData = async () => { loading.value=true; const res:any=await getShopReservations(query); list.value=res.data.records||[]; total.value=res.data.total||0; loading.value=false }
const handleConfirm = async (id:number) => { await confirmReservation(id); ElMessage.success('已确认'); loadData() }
const handleComplete = async (id:number) => { await completeReservation(id); ElMessage.success('已完成'); loadData() }
onMounted(loadData)
</script>
