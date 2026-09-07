<template>
  <div>
    <el-row :gutter="15">
      <el-col :span="8">
        <el-card><el-statistic title="账户余额(元)" :value="userInfo.balance || 0" :precision="2" :value-style="{ color: '#F56C6C', fontSize: '28px' }" /></el-card>
      </el-col>
      <el-col :span="8">
        <el-card><el-statistic title="信用分" :value="userInfo.creditScore || 0" :value-style="{ color: '#409EFF', fontSize: '28px' }" /></el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div style="text-align:center">
            <div style="color:#909399;font-size:14px;margin-bottom:10px">押金状态</div>
            <el-tag :type="userInfo.depositPaid ? 'success' : 'warning'" size="large">{{ userInfo.depositPaid ? '已缴纳 (¥99)' : '未缴纳' }}</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top:15px">
      <template #header>快捷操作</template>
      <div style="display:flex;gap:15px;flex-wrap:wrap">
        <el-button type="primary" size="large" @click="showRecharge = true">余额充值</el-button>
        <el-button v-if="!userInfo.depositPaid" type="success" size="large" @click="handlePayDeposit">缴纳押金(¥99)</el-button>
        <el-button v-if="userInfo.depositPaid" type="warning" size="large" @click="handleRefundDeposit">退还押金</el-button>
      </div>
    </el-card>
    <el-dialog v-model="showRecharge" title="余额充值" width="400px">
      <el-form label-width="80px">
        <el-form-item label="充值金额">
          <el-input-number v-model="rechargeAmount" :min="1" :max="10000" :step="10" style="width:100%" />
        </el-form-item>
        <div style="display:flex;gap:10px;margin-bottom:15px">
          <el-button v-for="a in [10, 20, 50, 100, 200]" :key="a" @click="rechargeAmount = a">¥{{ a }}</el-button>
        </div>
      </el-form>
      <template #footer><el-button @click="showRecharge = false">取消</el-button><el-button type="primary" @click="handleRecharge">确认充值</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserInfo, rechargeWallet, payDeposit, refundDeposit } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const userInfo = ref({})
const showRecharge = ref(false)
const rechargeAmount = ref(50)

const loadInfo = async () => { const res = await getUserInfo(); userInfo.value = res.data }
onMounted(loadInfo)

const handleRecharge = async () => {
  await rechargeWallet({ amount: rechargeAmount.value })
  ElMessage.success('充值成功')
  showRecharge.value = false
  loadInfo()
}

const handlePayDeposit = async () => {
  await ElMessageBox.confirm('确认缴纳押金 ¥99？', '缴纳押金')
  await payDeposit()
  ElMessage.success('押金缴纳成功')
  loadInfo()
}

const handleRefundDeposit = async () => {
  await ElMessageBox.confirm('确认退还押金？退还后将无法骑行', '退还押金')
  await refundDeposit()
  ElMessage.success('押金已退还')
  loadInfo()
}
</script>
