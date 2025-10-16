<template>
  <div class="income-page">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-value">¥{{ userInfo?.totalIncome || 0 }}</div>
            <div class="stat-label">累计收益</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-value">¥{{ userInfo?.accountBalance || 0 }}</div>
            <div class="stat-label">可提现金额</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-value">{{ userInfo?.totalOrders || 0 }}</div>
            <div class="stat-label">订单总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-button type="primary" size="large" style="width: 100%" @click="withdrawDialogVisible = true">
            申请提现
          </el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <template #header>
        <span>收益明细</span>
      </template>

      <el-empty description="收益明细开发中" />
    </el-card>

    <el-dialog v-model="withdrawDialogVisible" title="申请提现" width="500px">
      <el-form :model="withdrawForm" label-width="100px">
        <el-form-item label="提现金额">
          <el-input-number v-model="withdrawForm.amount" :min="10" :max="userInfo?.accountBalance" :precision="2" />
          <div style="margin-top: 8px; font-size: 12px; color: #909399;">
            最低提现10元，手续费2%，实际到账：¥{{ (withdrawForm.amount * 0.98).toFixed(2) }}
          </div>
        </el-form-item>

        <el-form-item label="提现方式">
          <el-radio-group v-model="withdrawForm.withdrawType">
            <el-radio label="ALIPAY">支付宝</el-radio>
            <el-radio label="WECHAT">微信</el-radio>
            <el-radio label="BANK">银行卡</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="账户名">
          <el-input v-model="withdrawForm.accountName" placeholder="请输入账户名" />
        </el-form-item>

        <el-form-item label="账户号">
          <el-input v-model="withdrawForm.accountNo" placeholder="请输入账号" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="withdrawDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleWithdraw">提交申请</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getProfile } from '@/api/user'
import { ElMessage } from 'element-plus'

const userInfo = ref(null)
const withdrawDialogVisible = ref(false)

const withdrawForm = reactive({
  amount: 10,
  withdrawType: 'ALIPAY',
  accountName: '',
  accountNo: ''
})

const loadUserInfo = async () => {
  try {
    const res = await getProfile()
    userInfo.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const handleWithdraw = () => {
  ElMessage.success('提现申请已提交，等待审核')
  withdrawDialogVisible.value = false
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.stat-card {
  text-align: center;
  padding: 30px 20px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.balance-info {
  text-align: center;
  padding: 40px 0;
}

.balance-amount {
  font-size: 48px;
  font-weight: bold;
  color: #67c23a;
  margin-bottom: 10px;
}

.balance-label {
  font-size: 16px;
  color: #909399;
}

.record-list {
  max-height: 400px;
  overflow-y: auto;
  margin-top: 15px;
}

.record-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #ebeef5;
}

.record-item:last-child {
  border-bottom: none;
}

.record-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.time {
  font-size: 12px;
  color: #909399;
}

.record-amount {
  font-size: 18px;
  font-weight: bold;
}

.record-amount.positive {
  color: #67c23a;
}

.record-amount.negative {
  color: #f56c6c;
}
</style>

