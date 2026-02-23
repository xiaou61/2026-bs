<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.bizType" placeholder="业务类型" style="width: 140px" clearable>
        <el-option label="采购入库" value="PURCHASE" />
        <el-option label="销售出库" value="SALE" />
      </el-select>
      <el-input v-model="query.bizNo" placeholder="业务单号" style="width: 220px" clearable />
      <el-select v-model="query.productId" placeholder="商品" style="width: 180px" clearable>
        <el-option v-for="item in products" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
      <el-button @click="resetQuery">重置</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column label="业务类型" width="120">
        <template #default="{ row }">
          <el-tag :type="row.bizType === 'PURCHASE' ? 'success' : 'warning'">{{ row.bizType === 'PURCHASE' ? '采购入库' : '销售出库' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="bizNo" label="业务单号" min-width="190" />
      <el-table-column prop="productName" label="商品" width="160" />
      <el-table-column label="库存变动" width="100">
        <template #default="{ row }">
          <span :class="row.changeQty >= 0 ? 'up' : 'down'">{{ row.changeQty }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="beforeStock" label="变动前" width="90" />
      <el-table-column prop="afterStock" label="变动后" width="90" />
      <el-table-column prop="operatorName" label="操作人" width="120" />
      <el-table-column prop="remark" label="备注" min-width="180" show-overflow-tooltip />
      <el-table-column prop="createTime" label="创建时间" width="180" />
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getProductList, getStockPage } from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const products = ref([])
const query = reactive({ pageNum: 1, pageSize: 10, bizType: '', bizNo: '', productId: null })

const loadProducts = async () => {
  const res = await getProductList()
  products.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getStockPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const resetQuery = () => {
  Object.assign(query, { pageNum: 1, pageSize: 10, bizType: '', bizNo: '', productId: null })
  loadData()
}

onMounted(async () => {
  await loadProducts()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
}

.up {
  color: #16a34a;
  font-weight: 600;
}

.down {
  color: #dc2626;
  font-weight: 600;
}
</style>
