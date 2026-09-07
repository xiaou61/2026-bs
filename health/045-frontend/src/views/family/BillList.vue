<template>
  <div>
    <el-card>
      <template #header>费用账单</template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="billNo" label="账单编号" />
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
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button v-if="row.status !== 2" type="primary" link @click="handlePay(row)">缴费</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="payDialogVisible" title="账单缴费" width="400">
      <el-form label-width="80px">
        <el-form-item label="待支付">¥{{ (currentBill?.totalAmount || 0) - (currentBill?.paidAmount || 0) }}</el-form-item>
        <el-form-item label="支付金额">
          <el-input-number v-model="payAmount" :min="0" :max="(currentBill?.totalAmount || 0) - (currentBill?.paidAmount || 0)" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="payDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmPay">确认支付</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { getBillList, payBill } from '@/api'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const loading = ref(false)
const list = ref<any[]>([])
const payDialogVisible = ref(false)
const currentBill = ref<any>(null)
const payAmount = ref(0)

const loadData = async () => {
  const elderId = userStore.userInfo.elderId
  if (!elderId) return
  loading.value = true
  try {
    const res: any = await getBillList({ elderId, current: 1, size: 20 })
    list.value = res.data.records
  } finally {
    loading.value = false
  }
}

const handlePay = (row: any) => {
  currentBill.value = row
  payAmount.value = row.totalAmount - (row.paidAmount || 0)
  payDialogVisible.value = true
}

const confirmPay = async () => {
  await payBill(currentBill.value.id, payAmount.value)
  ElMessage.success('支付成功')
  payDialogVisible.value = false
  loadData()
}

onMounted(loadData)
</script>
