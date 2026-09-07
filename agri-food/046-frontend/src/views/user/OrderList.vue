<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>我的订单</span>
          <el-button type="primary" @click="router.push('/user/create')">预约回收</el-button>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="orderNo" label="订单编号" width="180" />
        <el-table-column prop="appointmentTime" label="预约时间" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额">
          <template #default="{ row }">¥{{ row.amount || 0 }}</template>
        </el-table-column>
        <el-table-column prop="points" label="获得积分" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="danger" link @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyOrders, cancelOrder } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const statusMap: Record<number, string> = { 0: '待接单', 1: '已接单', 2: '进行中', 3: '已完成', 4: '已取消' }
const statusType: Record<number, string> = { 0: 'warning', 1: 'primary', 2: 'info', 3: 'success', 4: 'danger' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10 })

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getMyOrders(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleCancel = async (id: number) => {
  await ElMessageBox.confirm('确定取消此订单?', '提示')
  await cancelOrder(id)
  ElMessage.success('已取消')
  loadData()
}

onMounted(loadData)
</script>
