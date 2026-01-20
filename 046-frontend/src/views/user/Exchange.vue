<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>积分商城</span>
          <span style="color: #67c23a;">我的积分: {{ myPoints }}</span>
        </div>
      </template>
      <el-row :gutter="20">
        <el-col :span="6" v-for="p in products" :key="p.id">
          <el-card shadow="hover" style="margin-bottom: 20px;">
            <div style="text-align: center;">
              <el-icon size="64" color="#67c23a"><Gift /></el-icon>
              <h3>{{ p.name }}</h3>
              <p style="color: #909399; font-size: 13px;">{{ p.description }}</p>
              <div style="color: #f56c6c; font-size: 20px; font-weight: bold;">{{ p.points }} 积分</div>
              <div style="color: #909399; font-size: 12px; margin: 5px 0;">库存: {{ p.stock }}</div>
              <el-button type="primary" size="small" :disabled="myPoints < p.points || p.stock <= 0" @click="handleExchange(p)">
                {{ p.stock <= 0 ? '已售罄' : '立即兑换' }}
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>兑换记录</template>
      <el-table :data="records">
        <el-table-column prop="productName" label="商品" />
        <el-table-column prop="points" label="消耗积分" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="['warning', 'success', 'info'][row.status]">{{ ['待发放', '已发放', '已取消'][row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="兑换时间" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getMyPoints, getPointsProducts, exchangeProduct, getExchangeRecords } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const myPoints = ref(0)
const products = ref<any[]>([])
const records = ref<any[]>([])

const loadData = async () => {
  const [p1, p2, p3]: any = await Promise.all([getMyPoints(), getPointsProducts({}), getExchangeRecords({})])
  myPoints.value = p1.data
  products.value = p2.data
  records.value = p3.data.records || p3.data
}

const handleExchange = async (p: any) => {
  await ElMessageBox.confirm(`确定使用 ${p.points} 积分兑换 "${p.name}"?`, '确认兑换')
  await exchangeProduct(p.id)
  ElMessage.success('兑换成功')
  loadData()
}

onMounted(loadData)
</script>
