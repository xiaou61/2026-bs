<template>
  <div class="mall-container">
    <div class="page-header">
      <div>
        <h2>积分商城</h2>
        <p>使用创作积分兑换勋章、装扮和创作权益，兑换记录会写入积分明细。</p>
      </div>
      <el-card class="points-card" shadow="never">
        <div class="points-label">当前积分</div>
        <div class="points-value">{{ currentPoints }}</div>
      </el-card>
    </div>

    <div class="item-grid">
      <el-card v-for="item in items" :key="item.id" class="item-card" shadow="hover">
        <div class="item-tag">{{ item.tag }}</div>
        <div class="item-name">{{ item.name }}</div>
        <div class="item-type">{{ item.type }}</div>
        <div class="item-desc">{{ item.description }}</div>
        <div class="item-note">{{ item.note }}</div>
        <div class="item-footer">
          <div class="item-points">{{ item.points }} 积分</div>
          <el-button
            type="primary"
            :disabled="currentPoints < item.points"
            :loading="exchangingId === item.id"
            @click="handleExchange(item)"
          >
            立即兑换
          </el-button>
        </div>
      </el-card>
    </div>

    <el-card class="record-card">
      <template #header>
        <div class="record-title">兑换记录</div>
      </template>
      <el-table v-if="exchangeRecords.length > 0" :data="exchangeRecords" stripe>
        <el-table-column prop="reason" label="兑换内容" min-width="240" />
        <el-table-column label="消耗积分" width="120">
          <template #default="{ row }">
            <span class="cost-text">{{ Math.abs(row.changePoints) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="afterPoints" label="兑换后余额" width="120" />
        <el-table-column prop="createTime" label="兑换时间" width="180" />
      </el-table>
      <el-empty v-else description="还没有兑换记录，先挑一份奖励带走吧" />
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { exchangeMallItem, getMallItems, getPointsLog } from '@/api/user'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const items = ref([])
const pointsLog = ref([])
const exchangingId = ref(null)

const currentPoints = computed(() => userStore.userInfo?.points || 0)
const exchangeRecords = computed(() =>
  pointsLog.value.filter(item => item.changeType === 'EXCHANGE')
)

const fetchBaseData = async () => {
  try {
    if (!userStore.userInfo) {
      await userStore.getUserInfo()
    }
    const [itemsRes, logRes] = await Promise.all([
      getMallItems(),
      getPointsLog()
    ])
    items.value = itemsRes.data || []
    pointsLog.value = logRes.data || []
  } catch (error) {
    ElMessage.error('获取积分商城数据失败')
  }
}

const handleExchange = async (item) => {
  try {
    await ElMessageBox.confirm(
      `确定花费 ${item.points} 积分兑换「${item.name}」吗？`,
      '积分兑换',
      { type: 'warning' }
    )
    exchangingId.value = item.id
    await exchangeMallItem(item.id)
    await userStore.getUserInfo()
    const logRes = await getPointsLog()
    pointsLog.value = logRes.data || []
    ElMessage.success('兑换成功')
  } catch (error) {
  } finally {
    exchangingId.value = null
  }
}

onMounted(() => {
  fetchBaseData()
})
</script>

<style scoped>
.mall-container {
  padding: 24px;
  height: 100%;
  overflow-y: auto;
}

.page-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: stretch;
  margin-bottom: 24px;
}

.page-header h2 {
  margin: 0 0 8px;
  font-size: 28px;
}

.page-header p {
  margin: 0;
  color: #6b7280;
}

.points-card {
  min-width: 180px;
  border-radius: 16px;
  background: linear-gradient(135deg, #fef3c7 0%, #fffbeb 100%);
}

.points-label {
  color: #92400e;
  margin-bottom: 8px;
}

.points-value {
  font-size: 30px;
  font-weight: 700;
  color: #78350f;
}

.item-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 18px;
  margin-bottom: 24px;
}

.item-card {
  position: relative;
  border-radius: 16px;
}

.item-tag {
  display: inline-flex;
  align-items: center;
  padding: 4px 10px;
  border-radius: 999px;
  background: #eff6ff;
  color: #2563eb;
  font-size: 12px;
  margin-bottom: 14px;
}

.item-name {
  font-size: 18px;
  font-weight: 600;
  color: #111827;
  margin-bottom: 6px;
}

.item-type {
  color: #f97316;
  margin-bottom: 12px;
}

.item-desc {
  color: #4b5563;
  line-height: 1.7;
  min-height: 48px;
  margin-bottom: 10px;
}

.item-note {
  color: #94a3b8;
  font-size: 12px;
  margin-bottom: 18px;
}

.item-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

.item-points {
  font-size: 20px;
  font-weight: 700;
  color: #111827;
}

.record-card {
  border-radius: 16px;
}

.record-title {
  font-weight: 600;
}

.cost-text {
  color: #ef4444;
  font-weight: 600;
}
</style>
