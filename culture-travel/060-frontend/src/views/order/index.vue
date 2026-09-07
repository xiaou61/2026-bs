<template>
  <div>
    <el-card>
      <div style="display:flex;gap:10px;margin-bottom:15px">
        <el-input v-model="query.orderNo" placeholder="搜索订单号" style="width:200px" clearable />
        <el-select v-model="query.status" placeholder="订单状态" style="width:130px" clearable>
          <el-option label="待支付" :value="0" /><el-option label="已支付" :value="1" /><el-option label="已完成" :value="2" /><el-option label="已取消" :value="3" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="username" label="用户" width="80" />
        <el-table-column prop="movieTitle" label="电影" />
        <el-table-column prop="cinemaName" label="影院" />
        <el-table-column prop="seats" label="座位" />
        <el-table-column prop="totalPrice" label="总价" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="['warning','success','primary','info'][row.status]">{{ ['待支付','已支付','已完成','已取消'][row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="success" v-if="row.status === 1" @click="handleComplete(row.id)">完成</el-button>
            <el-button link type="danger" v-if="row.status < 2" @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:flex-end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total,prev,pager,next" @current-change="loadData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getOrderPage, completeOrder, cancelOrder } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10, orderNo: '', status: null })

const loadData = async () => { loading.value = true; try { const res = await getOrderPage(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
const handleComplete = async (id) => { await completeOrder(id); ElMessage.success('操作成功'); loadData() }
const handleCancel = async (id) => { await cancelOrder(id); ElMessage.success('已取消'); loadData() }
onMounted(loadData)
</script>
