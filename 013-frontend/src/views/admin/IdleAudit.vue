<template>
  <div class="admin-idle-audit">
    <el-card>
      <template #header>
        <span>闲置物品审核</span>
      </template>

      <el-table :data="items" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="物品标题" width="200" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column label="价格" width="150">
          <template #default="{ row }">
            日租：¥{{ row.dailyPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="depositAmount" label="押金" width="100" />
        <el-table-column label="新旧程度" width="100">
          <template #default="{ row }">
            {{ getConditionText(row.conditionLevel) }}
          </template>
        </el-table-column>
        <el-table-column prop="pickupAddress" label="取货地址" width="200" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row)">查看</el-button>
            <el-button type="success" size="small" @click="handleAudit(row.id, 1)">通过</el-button>
            <el-button type="danger" size="small" @click="showRejectDialog(row.id)">拒绝</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadItems"
        style="margin-top: 20px; justify-content: center;"
      />
    </el-card>

    <el-dialog v-model="rejectDialogVisible" title="拒绝原因" width="400px">
      <el-input type="textarea" v-model="rejectReason" :rows="4" placeholder="请输入拒绝原因" />
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmReject">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailDialogVisible" title="物品详情" width="600px">
      <el-descriptions v-if="currentItem" :column="2" border>
        <el-descriptions-item label="物品标题" :span="2">{{ currentItem.title }}</el-descriptions-item>
        <el-descriptions-item label="分类">{{ currentItem.category }}</el-descriptions-item>
        <el-descriptions-item label="新旧程度">{{ getConditionText(currentItem.conditionLevel) }}</el-descriptions-item>
        <el-descriptions-item label="原价">¥{{ currentItem.originalPrice }}</el-descriptions-item>
        <el-descriptions-item label="日租金">¥{{ currentItem.dailyPrice }}</el-descriptions-item>
        <el-descriptions-item label="押金">¥{{ currentItem.depositAmount }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentItem.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="取货地址" :span="2">{{ currentItem.pickupAddress }}</el-descriptions-item>
        <el-descriptions-item label="物品描述" :span="2">{{ currentItem.description }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getIdleAuditList, auditIdle } from '@/api/admin'
import { ElMessage } from 'element-plus'

const page = ref(1)
const size = ref(10)
const total = ref(0)
const items = ref([])
const rejectDialogVisible = ref(false)
const detailDialogVisible = ref(false)
const rejectReason = ref('')
const currentItemId = ref(null)
const currentItem = ref(null)

const getConditionText = (level) => {
  const map = {
    1: '全新',
    2: '九九新',
    3: '九五新',
    4: '九成新',
    5: '八成以下'
  }
  return map[level] || '-'
}

const loadItems = async () => {
  try {
    const res = await getIdleAuditList({ page: page.value, size: size.value })
    items.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const viewDetail = (item) => {
  currentItem.value = item
  detailDialogVisible.value = true
}

const handleAudit = async (id, status) => {
  try {
    await auditIdle(id, { status })
    ElMessage.success('审核成功')
    loadItems()
  } catch (error) {
    console.error(error)
  }
}

const showRejectDialog = (id) => {
  currentItemId.value = id
  rejectDialogVisible.value = true
}

const confirmReject = async () => {
  try {
    await auditIdle(currentItemId.value, { 
      status: 3, 
      auditReason: rejectReason.value 
    })
    ElMessage.success('已拒绝')
    rejectDialogVisible.value = false
    rejectReason.value = ''
    loadItems()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadItems()
})
</script>

<style scoped>
</style>

