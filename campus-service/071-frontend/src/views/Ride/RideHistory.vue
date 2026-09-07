<template>
  <el-card>
    <template #header>我的骑行记录</template>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="orderNo" label="订单号" width="180" />
      <el-table-column prop="bikeNo" label="车辆" width="120" />
      <el-table-column prop="startStationName" label="出发站点" />
      <el-table-column prop="endStationName" label="还车站点" />
      <el-table-column prop="startTime" label="开始时间" width="160" />
      <el-table-column prop="duration" label="时长(分)" width="80" />
      <el-table-column prop="amount" label="费用(元)" width="80" />
      <el-table-column label="状态" width="90"><template #default="{ row }"><el-tag :type="{ 1: 'primary', 2: 'warning', 3: 'success' }[row.status]" size="small">{{ { 1: '骑行中', 2: '待支付', 3: '已完成' }[row.status] }}</el-tag></template></el-table-column>
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMyRides } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10 })

const loadData = async () => { loading.value = true; try { const res = await getMyRides(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
onMounted(loadData)
</script>
