<template>
  <div>
    <el-card>
      <template #header>待处理订单</template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="orderNo" label="订单编号" width="180" />
        <el-table-column prop="contactName" label="联系人" />
        <el-table-column prop="contactPhone" label="电话" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="appointmentTime" label="预约时间" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="primary" size="small" @click="handleAccept(row.id)">接单</el-button>
            <el-button v-if="row.status === 1 || row.status === 2" type="success" size="small" @click="goComplete(row.id)">完成回收</el-button>
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
import { getCollectorOrders, acceptOrder } from '@/api'
import { ElMessage } from 'element-plus'

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
    const res: any = await getCollectorOrders(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleAccept = async (id: number) => {
  await acceptOrder(id)
  ElMessage.success('接单成功')
  loadData()
}

const goComplete = (id: number) => {
  router.push(`/collector/complete/${id}`)
}

onMounted(loadData)
</script>
