<template>
  <el-card>
    <template #header>钱包流水</template>
    <el-table :data="tableData" v-loading="loading" stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column label="类型" width="100"><template #default="{ row }"><el-tag :type="typeStyle[row.type]" size="small">{{ typeMap[row.type] }}</el-tag></template></el-table-column>
      <el-table-column label="金额" width="120"><template #default="{ row }"><span :style="{ color: row.amount > 0 ? '#67C23A' : '#F56C6C' }">{{ row.amount > 0 ? '+' : '' }}{{ row.amount }}</span></template></el-table-column>
      <el-table-column prop="balanceAfter" label="余额" width="100" />
      <el-table-column prop="description" label="说明" />
      <el-table-column prop="createTime" label="时间" width="170" />
    </el-table>
    <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadData" />
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getWalletRecords } from '../../api'

const typeMap = { 1: '充值', 2: '骑行扣费', 3: '缴纳押金', 4: '退还押金' }
const typeStyle = { 1: 'success', 2: 'danger', 3: 'warning', 4: '' }
const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10 })

const loadData = async () => { loading.value = true; try { const res = await getWalletRecords(query); tableData.value = res.data.list; total.value = res.data.total } finally { loading.value = false } }
onMounted(loadData)
</script>
