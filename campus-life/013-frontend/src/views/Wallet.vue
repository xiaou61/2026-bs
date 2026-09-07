<template>
  <div class="wallet-page">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>押金账户</span>
              <el-button type="primary" size="small" @click="depositDialogVisible = true">充值</el-button>
            </div>
          </template>

          <div class="balance-info">
            <div class="balance-amount">¥{{ userInfo?.depositBalance || 0 }}</div>
            <div class="balance-label">押金余额</div>
          </div>

          <el-divider />

          <h4>押金记录</h4>
          <div class="record-list">
            <div v-for="record in depositRecords" :key="record.id" class="record-item">
              <div class="record-info">
                <span>{{ record.reason }}</span>
                <span class="time">{{ record.createTime }}</span>
              </div>
              <div class="record-amount" :class="record.amount > 0 ? 'positive' : 'negative'">
                {{ record.amount > 0 ? '+' : '' }}{{ record.amount }}
              </div>
            </div>
            <el-empty v-if="depositRecords.length === 0" description="暂无记录" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span>账户余额</span>
              <el-button type="primary" size="small" @click="balanceDialogVisible = true">充值</el-button>
            </div>
          </template>

          <div class="balance-info">
            <div class="balance-amount">¥{{ userInfo?.accountBalance || 0 }}</div>
            <div class="balance-label">账户余额</div>
          </div>

          <el-divider />

          <h4>余额记录</h4>
          <div class="record-list">
            <div v-for="record in balanceRecords" :key="record.id" class="record-item">
              <div class="record-info">
                <span>{{ record.remark }}</span>
                <span class="time">{{ record.createTime }}</span>
              </div>
              <div class="record-amount" :class="record.amount > 0 ? 'positive' : 'negative'">
                {{ record.amount > 0 ? '+' : '' }}{{ record.amount }}
              </div>
            </div>
            <el-empty v-if="balanceRecords.length === 0" description="暂无记录" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="depositDialogVisible" title="充值押金" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number v-model="depositAmount" :min="10" :precision="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="depositDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleChargeDeposit">确认充值</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="balanceDialogVisible" title="充值余额" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number v-model="balanceAmount" :min="10" :precision="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="balanceDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleChargeBalance">确认充值</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getProfile } from '@/api/user'
import { chargeDeposit, chargeBalance, getDepositRecord, getBalanceRecord } from '@/api/payment'
import { ElMessage } from 'element-plus'

const userInfo = ref(null)
const depositRecords = ref([])
const balanceRecords = ref([])
const depositDialogVisible = ref(false)
const balanceDialogVisible = ref(false)
const depositAmount = ref(100)
const balanceAmount = ref(100)

const loadUserInfo = async () => {
  try {
    const res = await getProfile()
    userInfo.value = res.data
  } catch (error) {
    console.error(error)
  }
}

const loadDepositRecords = async () => {
  try {
    const res = await getDepositRecord()
    depositRecords.value = res.data.slice(0, 10)
  } catch (error) {
    console.error(error)
  }
}

const loadBalanceRecords = async () => {
  try {
    const res = await getBalanceRecord()
    balanceRecords.value = res.data.slice(0, 10)
  } catch (error) {
    console.error(error)
  }
}

const handleChargeDeposit = async () => {
  try {
    await chargeDeposit({ amount: depositAmount.value })
    ElMessage.success('充值成功')
    depositDialogVisible.value = false
    loadUserInfo()
    loadDepositRecords()
  } catch (error) {
    console.error(error)
  }
}

const handleChargeBalance = async () => {
  try {
    await chargeBalance({ amount: balanceAmount.value })
    ElMessage.success('充值成功')
    balanceDialogVisible.value = false
    loadUserInfo()
    loadBalanceRecords()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadUserInfo()
  loadDepositRecords()
  loadBalanceRecords()
})
</script>

<style scoped>
.balance-info {
  text-align: center;
  padding: 30px 0;
}

.balance-amount {
  font-size: 48px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 10px;
}

.balance-label {
  font-size: 16px;
  color: #909399;
}

.record-list {
  max-height: 400px;
  overflow-y: auto;
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

.credit-score {
  text-align: center;
  padding: 20px;
}

.score-circle {
  width: 180px;
  height: 180px;
  margin: 0 auto 30px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.score-value {
  font-size: 60px;
  font-weight: bold;
}

.score-label {
  font-size: 18px;
  margin-top: 8px;
}

.credit-level {
  margin-bottom: 20px;
}

.credit-desc {
  color: #606266;
}

.log-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.log-score {
  font-size: 18px;
  font-weight: bold;
}

.log-balance {
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
}

.rule-list {
  padding-left: 20px;
  line-height: 2;
  color: #606266;
}

.level-card {
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  color: #fff;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.level-card h4 {
  margin-bottom: 10px;
}

.level-card p {
  font-size: 14px;
  opacity: 0.9;
}
</style>

