<template>
  <div>
    <el-card>
      <template #header>订单管理</template>
      <el-form inline>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option v-for="(v, k) in statusMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </el-form-item>
      </el-form>
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
        <el-table-column prop="amount" label="金额">
          <template #default="{ row }">¥{{ row.amount || 0 }}</template>
        </el-table-column>
        <el-table-column prop="points" label="积分" />
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminOrders } from '@/api'

const statusMap: Record<number, string> = { 0: '待接单', 1: '已接单', 2: '进行中', 3: '已完成', 4: '已取消' }
const statusType: Record<number, string> = { 0: 'warning', 1: 'primary', 2: 'info', 3: 'success', 4: 'danger' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, status: undefined as number | undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getAdminOrders(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>
