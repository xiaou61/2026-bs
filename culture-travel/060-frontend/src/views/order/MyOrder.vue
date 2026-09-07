<template>
  <div>
    <el-card>
      <h3 style="margin-bottom:15px">我的订单</h3>
      <el-table :data="orders" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单号" width="200" />
        <el-table-column prop="movieTitle" label="电影" />
        <el-table-column prop="cinemaName" label="影院" />
        <el-table-column prop="hallName" label="影厅" width="80" />
        <el-table-column label="场次" width="180"><template #default="{ row }">{{ row.showDate }} {{ row.startTime }}</template></el-table-column>
        <el-table-column prop="seats" label="座位" />
        <el-table-column prop="totalPrice" label="总价" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }"><el-tag :type="['warning','success','primary','info'][row.status]">{{ ['待支付','已支付','已完成','已取消'][row.status] }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" v-if="row.status === 0" @click="handlePay(row.id)">支付</el-button>
            <el-button link type="danger" v-if="row.status === 0" @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyOrders, payOrder, cancelOrder } from '../../api'

const loading = ref(false)
const orders = ref([])

const loadData = async () => { loading.value = true; try { const res = await getMyOrders(); orders.value = res.data } finally { loading.value = false } }
const handlePay = async (id) => { await payOrder(id); ElMessage.success('支付成功'); loadData() }
const handleCancel = async (id) => { await cancelOrder(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
