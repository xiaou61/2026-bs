<template>
  <div class="page-container">
    <el-card v-if="isCustomer">
      <template #header>
        <span>钱包充值</span>
      </template>
      <div class="wallet-box">
        <div class="balance-panel">
          <div class="label">当前余额</div>
          <div class="value">¥{{ Number(userStore.user?.balance || 0).toFixed(2) }}</div>
        </div>
        <div class="recharge-panel">
          <el-input-number v-model="amount" :min="1" :precision="2" />
          <el-button type="primary" @click="handleRecharge">立即充值</el-button>
        </div>
      </div>
    </el-card>

    <el-card :class="isCustomer ? 'mt16' : ''">
      <div class="search-bar">
        <el-select v-model="query.payType" placeholder="支付类型" clearable>
          <el-option label="RECHARGE" value="RECHARGE" />
          <el-option label="BALANCE" value="BALANCE" />
        </el-select>
        <el-select v-model="query.status" placeholder="支付状态" clearable>
          <el-option label="SUCCESS" value="SUCCESS" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="payNo" label="支付流水号" min-width="170" />
        <el-table-column prop="nickname" label="用户" min-width="120" />
        <el-table-column prop="orderNo" label="关联订单" min-width="160" />
        <el-table-column prop="payType" label="支付类型" min-width="100" />
        <el-table-column prop="payAmount" label="金额" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column prop="payTime" label="支付时间" min-width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getInfo, getPaymentList, recharge } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isCustomer = computed(() => (userStore.user?.role || '').toUpperCase() === 'CUSTOMER')

const loading = ref(false)
const tableData = ref([])
const amount = ref(50)
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  payType: '',
  status: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getPaymentList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleRecharge = async () => {
  await recharge({ amount: amount.value })
  const info = await getInfo()
  userStore.user = info.data || null
  localStorage.setItem('user', JSON.stringify(info.data || null))
  ElMessage.success('充值成功')
  loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.wallet-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.balance-panel {
  padding: 18px 22px;
  border-radius: 12px;
  background: #f3f8fb;
}

.label {
  color: #667085;
  margin-bottom: 8px;
}

.value {
  font-size: 28px;
  font-weight: 700;
  color: #17324d;
}

.recharge-panel {
  display: flex;
  align-items: center;
  gap: 12px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
