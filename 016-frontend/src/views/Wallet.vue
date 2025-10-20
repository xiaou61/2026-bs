<template>
  <div class="wallet-container">
    <el-card class="balance-card">
      <div class="balance-info">
        <div class="balance-item">
          <div class="balance-label">可用余额</div>
          <div class="balance-value">¥{{ wallet?.balance || 0 }}</div>
          <div class="balance-actions">
            <el-button type="primary" @click="showRechargeDialog = true">充值</el-button>
            <el-button @click="showWithdrawDialog = true">提现</el-button>
          </div>
        </div>
        <el-divider direction="vertical" />
        <div class="balance-item">
          <div class="balance-label">冻结金额</div>
          <div class="balance-value frozen">¥{{ wallet?.frozenAmount || 0 }}</div>
        </div>
        <el-divider direction="vertical" />
        <div class="balance-item">
          <div class="balance-label">累计收入</div>
          <div class="balance-value income">¥{{ wallet?.totalIncome || 0 }}</div>
        </div>
        <el-divider direction="vertical" />
        <div class="balance-item">
          <div class="balance-label">累计支出</div>
          <div class="balance-value expense">¥{{ wallet?.totalExpense || 0 }}</div>
        </div>
      </div>
    </el-card>

    <el-card class="transaction-card">
      <template #header>
        <div class="card-header">
          <span>交易记录</span>
        </div>
      </template>
      <el-table :data="transactions" style="width: 100%">
        <el-table-column prop="transactionNo" label="交易流水号" width="200" />
        <el-table-column label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getTypeColor(row.type)">{{ getTypeText(row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="金额" width="120">
          <template #default="{ row }">
            <span :class="getAmountClass(row.type)">{{ getAmountText(row.type, row.amount) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column label="交易后余额" width="120">
          <template #default="{ row }">¥{{ row.balanceAfter }}</template>
        </el-table-column>
        <el-table-column label="交易时间" width="180">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-if="total > 0"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadTransactions"
        class="pagination"
      />
    </el-card>

    <el-dialog v-model="showRechargeDialog" title="余额充值" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number v-model="rechargeAmount" :min="1" :max="10000" :step="10" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRechargeDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleRecharge">确认充值</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showWithdrawDialog" title="余额提现" width="400px">
      <el-form label-width="80px">
        <el-form-item label="提现金额">
          <el-input-number v-model="withdrawAmount" :min="1" :max="wallet?.balance || 0" :step="10" />
        </el-form-item>
        <el-alert title="提现金额将在1-3个工作日内到账" type="info" :closable="false" />
      </el-form>
      <template #footer>
        <el-button @click="showWithdrawDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleWithdraw">确认提现</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBalance, recharge, withdraw, getTransactions } from '../api/wallet'
import { ElMessage } from 'element-plus'

const wallet = ref(null)
const transactions = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showRechargeDialog = ref(false)
const showWithdrawDialog = ref(false)
const rechargeAmount = ref(100)
const withdrawAmount = ref(100)
const loading = ref(false)

const loadWallet = async () => {
  try {
    wallet.value = await getBalance()
  } catch (error) {
    console.error('获取余额失败', error)
  }
}

const loadTransactions = async () => {
  try {
    const data = await getTransactions({
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    transactions.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('获取交易记录失败', error)
  }
}

const handleRecharge = async () => {
  loading.value = true
  try {
    await recharge(rechargeAmount.value)
    ElMessage.success('充值成功')
    showRechargeDialog.value = false
    loadWallet()
    loadTransactions()
  } catch (error) {
    console.error('充值失败', error)
  } finally {
    loading.value = false
  }
}

const handleWithdraw = async () => {
  loading.value = true
  try {
    await withdraw(withdrawAmount.value)
    ElMessage.success('提现申请已提交')
    showWithdrawDialog.value = false
    loadWallet()
    loadTransactions()
  } catch (error) {
    console.error('提现失败', error)
  } finally {
    loading.value = false
  }
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
  loadWallet()
  loadTransactions()
})
</script>

<style scoped>
.wallet-container {
  max-width: 1200px;
  margin: 0 auto;
}

.balance-card {
  margin-bottom: 20px;
}

.balance-info {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 20px 0;
}

.balance-item {
  flex: 1;
  text-align: center;
}

.balance-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.balance-value {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 15px;
}

.balance-value.frozen {
  color: #e6a23c;
}

.balance-value.income {
  color: #67c23a;
}

.balance-value.expense {
  color: #f56c6c;
}

.balance-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}

.transaction-card {
  margin-top: 20px;
}

.card-header {
  font-size: 16px;
  font-weight: 500;
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

