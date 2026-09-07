<template>
  <div class="wallet-page">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="balance-card">
          <div class="stat-card">
            <div class="stat-value">¥{{ wallet.balance || '0.00' }}</div>
            <div class="stat-label">账户余额</div>
          </div>
          <el-button type="primary" class="mt-20" @click="showRechargeDialog(1)">充值</el-button>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="deposit-card">
          <div class="stat-card green">
            <div class="stat-value">¥{{ wallet.deposit || '0.00' }}</div>
            <div class="stat-label">押金 ({{ depositStatusText[wallet.depositStatus] }})</div>
          </div>
          <el-button v-if="wallet.depositStatus === 0" type="success" class="mt-20" @click="showRechargeDialog(2)">
            缴纳押金
          </el-button>
          <el-button v-else-if="wallet.depositStatus === 1" type="warning" class="mt-20" @click="handleRefund">
            申请退还
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="mt-20">
      <template #header>充值记录</template>
      <el-table :data="records" v-loading="loading">
        <el-table-column prop="createTime" label="时间" width="170" />
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="{ row }">¥{{ row.amount }}</template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">{{ row.type === 1 ? '余额充值' : '押金缴纳' }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已完成' : '待支付' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 充值弹窗 -->
    <el-dialog v-model="dialogVisible" :title="rechargeType === 1 ? '余额充值' : '缴纳押金'" width="400px">
      <el-form v-if="rechargeType === 1">
        <el-form-item label="充值金额">
          <el-radio-group v-model="rechargeAmount">
            <el-radio-button :label="10">10元</el-radio-button>
            <el-radio-button :label="20">20元</el-radio-button>
            <el-radio-button :label="50">50元</el-radio-button>
            <el-radio-button :label="100">100元</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div v-else class="text-center">
        <p>押金金额: <strong>¥99.00</strong></p>
        <p class="tip">信用分达到600分后可享受免押金服务</p>
      </div>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="rechargeLoading" @click="handleRecharge">确认支付</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { walletApi } from '@/api'

const loading = ref(false)
const wallet = ref({})
const records = ref([])
const dialogVisible = ref(false)
const rechargeType = ref(1)
const rechargeAmount = ref(20)
const rechargeLoading = ref(false)

const depositStatusText = { 0: '未缴纳', 1: '已缴纳', 2: '退还中', 3: '已退还' }

const loadWallet = async () => {
  const res = await walletApi.getBalance()
  wallet.value = res.data
}

const loadRecords = async () => {
  loading.value = true
  try {
    const res = await walletApi.getRechargeRecords({ page: 1, size: 20 })
    records.value = res.data.records
  } finally {
    loading.value = false
  }
}

const showRechargeDialog = (type) => {
  rechargeType.value = type
  rechargeAmount.value = 20
  dialogVisible.value = true
}

const handleRecharge = async () => {
  rechargeLoading.value = true
  try {
    await walletApi.recharge({
      amount: rechargeType.value === 1 ? rechargeAmount.value : 99,
      type: rechargeType.value
    })
    ElMessage.success('支付成功')
    dialogVisible.value = false
    await loadWallet()
    await loadRecords()
  } finally {
    rechargeLoading.value = false
  }
}

const handleRefund = () => {
  ElMessageBox.confirm('确定要申请退还押金吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    await walletApi.refundDeposit()
    ElMessage.success('退还成功')
    await loadWallet()
  })
}

onMounted(() => {
  loadWallet()
  loadRecords()
})
</script>

<style scoped lang="scss">
.balance-card, .deposit-card {
  text-align: center;
}

.tip {
  font-size: 12px;
  color: #999;
}
</style>
