<template>
  <div>
    <el-card>
      <template #header>兑换记录管理</template>
      <el-form inline>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option label="待发放" :value="0" />
            <el-option label="已发放" :value="1" />
            <el-option label="已取消" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="userId" label="用户ID" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="points" label="消耗积分" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="['warning', 'success', 'info'][row.status]">{{ ['待发放', '已发放', '已取消'][row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="兑换时间" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="success" link @click="handleDeliver(row.id)">发放</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getAdminExchanges, deliverExchange } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, status: undefined as number | undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getAdminExchanges(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleDeliver = async (id: number) => {
  await deliverExchange(id)
  ElMessage.success('发放成功')
  loadData()
}

onMounted(loadData)
</script>
