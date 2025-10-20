<template>
  <div class="transactions-container">
    <el-card>
      <el-form :inline="true" :model="filters" class="search-form">
        <el-form-item label="交易类型">
          <el-select v-model="filters.type" placeholder="全部" clearable>
            <el-option label="充值" :value="1" />
            <el-option label="消费" :value="2" />
            <el-option label="收入" :value="3" />
            <el-option label="提现" :value="4" />
            <el-option label="退款" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户ID">
          <el-input v-model="filters.userId" placeholder="请输入用户ID" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadTransactions">查询</el-button>
          <el-button @click="resetFilters">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="summary">
        <el-statistic title="充值总额" :value="summary.rechargeTotal" prefix="¥" />
        <el-statistic title="消费总额" :value="summary.expenseTotal" prefix="¥" />
        <el-statistic title="收入总额" :value="summary.incomeTotal" prefix="¥" />
        <el-statistic title="提现总额" :value="summary.withdrawTotal" prefix="¥" />
      </div>

      <el-table :data="transactions" border style="width: 100%; margin-top: 20px">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="transactionNo" label="交易流水号" width="200" />
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column label="交易类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getTypeColor(row.type)">{{ getTypeText(row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="金额" width="120">
          <template #default="{ row }">
            <span :class="getAmountClass(row.type)">{{ getAmountText(row.type, row.amount) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="交易前余额" width="120">
          <template #default="{ row }">¥{{ row.balanceBefore }}</template>
        </el-table-column>
        <el-table-column label="交易后余额" width="120">
          <template #default="{ row }">¥{{ row.balanceAfter }}</template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column label="交易时间" width="160">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 0"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadTransactions"
        class="pagination"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getTransactionList } from '../../api/admin'

const transactions = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const filters = reactive({
  type: null,
  userId: ''
})

const summary = reactive({
  rechargeTotal: 0,
  expenseTotal: 0,
  incomeTotal: 0,
  withdrawTotal: 0
})

const loadTransactions = async () => {
  try {
    const data = await getTransactionList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...filters
    })
    transactions.value = data.records
    total.value = data.total
    
    calculateSummary()
  } catch (error) {
    console.error('加载交易记录失败', error)
  }
}

const resetFilters = () => {
  filters.type = null
  filters.userId = ''
  loadTransactions()
}

const calculateSummary = () => {
  summary.rechargeTotal = transactions.value.filter(t => t.type === 1).reduce((sum, t) => sum + parseFloat(t.amount), 0).toFixed(2)
  summary.expenseTotal = transactions.value.filter(t => t.type === 2).reduce((sum, t) => sum + parseFloat(t.amount), 0).toFixed(2)
  summary.incomeTotal = transactions.value.filter(t => t.type === 3).reduce((sum, t) => sum + parseFloat(t.amount), 0).toFixed(2)
  summary.withdrawTotal = transactions.value.filter(t => t.type === 4).reduce((sum, t) => sum + parseFloat(t.amount), 0).toFixed(2)
}

const getTypeText = (type) => {
  const texts = { 1: '充值', 2: '消费', 3: '收入', 4: '提现', 5: '退款' }
  return texts[type] || '未知'
}

const getTypeColor = (type) => {
  const colors = { 1: 'success', 2: 'danger', 3: 'success', 4: 'warning', 5: 'info' }
  return colors[type] || ''
}

const getAmountClass = (type) => {
  return type === 1 || type === 3 || type === 5 ? 'amount-income' : 'amount-expense'
}

const getAmountText = (type, amount) => {
  const prefix = type === 1 || type === 3 || type === 5 ? '+' : '-'
  return `${prefix}¥${amount}`
}

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

onMounted(() => {
  loadTransactions()
})
</script>

<style scoped>
.transactions-container {
  max-width: 100%;
}

.search-form {
  margin-bottom: 20px;
}

.summary {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.amount-income {
  color: #67c23a;
  font-weight: bold;
}

.amount-expense {
  color: #f56c6c;
  font-weight: bold;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>

