<template>
  <div class="page-container">
    <el-card v-if="isCustomer">
      <template #header>
        <span>快速下单</span>
      </template>
      <el-form :model="buyForm" inline>
        <el-form-item label="门店">
          <el-select v-model="buyForm.shopId" placeholder="请选择门店" style="width: 220px">
            <el-option v-for="item in shopOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="菜单">
          <el-select v-model="buyForm.menuId" placeholder="请选择菜单" style="width: 260px">
            <el-option v-for="item in menuOptions" :key="item.id" :label="`${item.name} / ¥${item.price} / 库存${item.stock}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量">
          <el-input-number v-model="buyForm.quantity" :min="1" />
        </el-form-item>
        <el-form-item label="用餐方式">
          <el-select v-model="buyForm.dineType" style="width: 140px">
            <el-option label="IN_STORE" value="IN_STORE" />
            <el-option label="TAKE_AWAY" value="TAKE_AWAY" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleCreate">创建订单</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card :class="isCustomer ? 'mt16' : ''">
      <div class="search-bar">
        <el-input v-model="query.orderNo" placeholder="订单号" clearable />
        <el-select v-model="query.status" placeholder="订单状态" clearable>
          <el-option label="WAIT_PAY" value="WAIT_PAY" />
          <el-option label="PAID" value="PAID" />
          <el-option label="COMPLETED" value="COMPLETED" />
          <el-option label="CANCELED" value="CANCELED" />
        </el-select>
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="orderNo" label="订单号" min-width="170" />
        <el-table-column prop="shopName" label="门店" min-width="160" />
        <el-table-column prop="menuName" label="菜单" min-width="140" />
        <el-table-column prop="dineType" label="用餐方式" min-width="100" />
        <el-table-column prop="quantity" label="数量" min-width="80" />
        <el-table-column prop="payAmount" label="实付金额" min-width="100" />
        <el-table-column prop="status" label="状态" min-width="100" />
        <el-table-column prop="payTime" label="支付时间" min-width="180" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button v-if="isCustomer && row.status === 'WAIT_PAY'" link type="primary" @click="handlePay(row)">余额支付</el-button>
            <el-button v-if="row.status === 'WAIT_PAY'" link type="danger" @click="handleCancel(row.id)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { balancePay, cancelOrder, createOrder, getInfo, getMenuPublicList, getOrderList, getShopPublicList } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isCustomer = computed(() => (userStore.user?.role || '').toUpperCase() === 'CUSTOMER')

const loading = ref(false)
const tableData = ref([])
const shopOptions = ref([])
const menuOptions = ref([])
const query = reactive({
  pageNum: 1,
  pageSize: 20,
  orderNo: '',
  status: ''
})
const buyForm = reactive({
  shopId: null,
  menuId: null,
  quantity: 1,
  dineType: 'IN_STORE',
  remark: ''
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getOrderList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const loadOptions = async () => {
  const [shopRes, menuRes] = await Promise.all([getShopPublicList(), getMenuPublicList()])
  shopOptions.value = shopRes.data || []
  menuOptions.value = menuRes.data || []
}

const handleCreate = async () => {
  await createOrder(buyForm)
  ElMessage.success('订单已创建，请尽快支付')
  loadData()
}

const handlePay = async row => {
  await balancePay({ orderId: row.id })
  const info = await getInfo()
  userStore.user = info.data || null
  localStorage.setItem('user', JSON.stringify(info.data || null))
  ElMessage.success('支付成功')
  loadData()
}

const handleCancel = async id => {
  await cancelOrder(id)
  ElMessage.success('订单已取消')
  loadData()
}

onMounted(async () => {
  if (isCustomer.value) {
    await loadOptions()
  }
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
