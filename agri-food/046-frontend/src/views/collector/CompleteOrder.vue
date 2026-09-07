<template>
  <div>
    <el-card>
      <template #header>完成回收</template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单编号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ order.contactName }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ order.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="地址">{{ order.address }}</el-descriptions-item>
        <el-descriptions-item label="预约时间">{{ order.appointmentTime }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ order.remark || '无' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>回收明细</span>
          <el-button type="primary" @click="addDetail">添加明细</el-button>
        </div>
      </template>
      <el-table :data="details">
        <el-table-column prop="categoryId" label="分类">
          <template #default="{ row }">
            <el-select v-model="row.categoryId" placeholder="选择分类" @change="onCategoryChange(row)">
              <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="重量(kg)">
          <template #default="{ row }">
            <el-input-number v-model="row.weight" :min="0.1" :precision="2" @change="calcAmount(row)" />
          </template>
        </el-table-column>
        <el-table-column prop="price" label="单价(元/kg)" />
        <el-table-column prop="amount" label="金额" />
        <el-table-column prop="points" label="积分" />
        <el-table-column label="操作" width="80">
          <template #default="{ $index }">
            <el-button type="danger" link @click="details.splice($index, 1)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px; text-align: right;">
        <span style="margin-right: 20px;">总金额: ¥{{ totalAmount }}</span>
        <span>总积分: {{ totalPoints }}</span>
      </div>
      <div style="margin-top: 20px; text-align: center;">
        <el-button @click="router.back()">返回</el-button>
        <el-button type="success" @click="handleComplete" :disabled="details.length === 0">确认完成</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getOrderDetail, getCategoryList, completeOrder } from '@/api'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const orderId = Number(route.params.id)
const order = ref<any>({})
const categories = ref<any[]>([])
const details = ref<any[]>([])

const totalAmount = computed(() => details.value.reduce((s, d) => s + (d.amount || 0), 0).toFixed(2))
const totalPoints = computed(() => details.value.reduce((s, d) => s + (d.points || 0), 0))

const loadOrder = async () => {
  const res: any = await getOrderDetail(orderId)
  order.value = res.data
}

const loadCategories = async () => {
  const res: any = await getCategoryList()
  categories.value = res.data
}

const addDetail = () => {
  details.value.push({ categoryId: null, weight: 1, price: 0, amount: 0, points: 0 })
}

const onCategoryChange = (row: any) => {
  const cat = categories.value.find(c => c.id === row.categoryId)
  if (cat) {
    row.price = cat.price
    row.pointsRate = cat.pointsRate
    calcAmount(row)
  }
}

const calcAmount = (row: any) => {
  row.amount = Number((row.weight * row.price).toFixed(2))
  row.points = Math.floor(row.weight * (row.pointsRate || 0))
}

const handleComplete = async () => {
  await completeOrder(orderId, details.value)
  ElMessage.success('回收完成')
  router.push('/collector/order')
}

onMounted(() => {
  loadOrder()
  loadCategories()
})
</script>
