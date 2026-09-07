<template>
  <div>
    <el-card>
      <template #header>探访审批</template>
      <el-form inline>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable>
            <el-option v-for="(v, k) in statusMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="visitorName" label="访客姓名" />
        <el-table-column prop="visitorPhone" label="联系电话" />
        <el-table-column prop="visitTime" label="预约时间" />
        <el-table-column prop="purpose" label="探访目的" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="statusType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <template v-if="row.status === 0">
              <el-button type="success" link @click="handleApprove(row.id, 1)">通过</el-button>
              <el-button type="danger" link @click="handleApprove(row.id, 2)">拒绝</el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top: 16px;" :total="total" v-model:current-page="query.current" v-model:page-size="query.size" @change="loadData" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { getVisitList, approveVisit } from '@/api'
import { ElMessage } from 'element-plus'

const statusMap: Record<number, string> = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已完成', 4: '已取消' }
const statusType: Record<number, string> = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info', 4: 'info' }
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const query = reactive({ current: 1, size: 10, status: undefined as number | undefined })

const loadData = async () => {
  loading.value = true
  try {
    const res: any = await getVisitList(query)
    list.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const handleApprove = async (id: number, status: number) => {
  await approveVisit(id, status)
  ElMessage.success('操作成功')
  loadData()
}

onMounted(loadData)
</script>
