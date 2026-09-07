<template>
  <div>
    <el-card>
      <template #header>费用管理</template>
      <el-form inline>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option label="待支付" :value="0" />
            <el-option label="部分支付" :value="1" />
            <el-option label="已支付" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="billNo" label="账单编号" />
        <el-table-column prop="elderId" label="老人ID" />
        <el-table-column prop="billMonth" label="账单月份" />
        <el-table-column prop="totalAmount" label="总金额">
          <template #default="{ row }">¥{{ row.totalAmount }}</template>
        </el-table-column>
        <el-table-column prop="paidAmount" label="已支付">
          <template #default="{ row }">¥{{ row.paidAmount || 0 }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="['danger', 'warning', 'success'][row.status]">{{ ['待支付', '部分支付', '已支付'][row.status] }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getBillList } from '@/api'

const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, status: undefined as number | undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getBillList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>
