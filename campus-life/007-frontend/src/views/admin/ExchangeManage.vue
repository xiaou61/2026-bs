<template>
  <div>
    <el-card>
      <el-table :data="exchanges" border>
        <el-table-column prop="rewardName" label="商品名称" />
        <el-table-column prop="userName" label="兑换人" width="120" />
        <el-table-column prop="points" label="消耗积分" width="120">
          <template #default="{ row }">
            <span style="color: #f56c6c; font-weight: bold">{{ row.points }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="address" label="收货地址" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="exchangeTime" label="兑换时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="success" size="small" @click="handleStatus(row.id, 1)">已发放</el-button>
            <el-button v-if="row.status === 0" type="danger" size="small" @click="handleStatus(row.id, 2)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getExchangeList, updateExchangeStatus } from '@/api/exchange'

const exchanges = ref([])

const getStatusType = (status) => {
  const types = { 0: 'warning', 1: 'success', 2: 'info' }
  return types[status]
}

const getStatusText = (status) => {
  const texts = { 0: '待发放', 1: '已发放', 2: '已取消' }
  return texts[status]
}

const loadExchanges = async () => {
  const res = await getExchangeList()
  exchanges.value = res.data
}

const handleStatus = async (id, status) => {
  try {
    const action = status === 1 ? '发放' : '取消'
    await ElMessageBox.confirm(`确定要${action}该兑换订单吗?`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await updateExchangeStatus(id, status)
    ElMessage.success('操作成功')
    loadExchanges()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadExchanges()
})
</script>

