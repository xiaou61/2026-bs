<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="16">
        <el-card class="mb16">
          <template #header>平台概览</template>
          <div class="card-grid">
            <el-statistic title="今日销售额" :value="Number(stats.todaySales || 0)" :precision="2" />
            <el-statistic title="今日订单" :value="Number(stats.todayOrders || 0)" />
            <el-statistic title="总订单" :value="Number(stats.totalOrders || 0)" />
            <el-statistic title="班次数量" :value="Number(stats.totalSchedules || 0)" />
          </div>
        </el-card>
        <el-card>
          <template #header>最近公告</template>
          <el-timeline>
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.publishTime">
              <strong>{{ item.title }}</strong>
              <div class="notice-text">{{ item.content }}</div>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="mb16">
          <template #header>角色提示</template>
          <div class="tip-block" v-if="userStore.isAdmin">
            当前为管理员账号，可维护用户、车站、列车和车厢模板，并查看全局运营统计。
          </div>
          <div class="tip-block" v-else-if="userStore.isDispatcher">
            当前为调度员账号，可维护班次、审核退改签、执行电子票核验并查看调度看板。
          </div>
          <div class="tip-block" v-else>
            当前为乘客账号，可查询余票、管理常用乘车人、在线购票并提交退改签申请。
          </div>
        </el-card>
        <el-card>
          <template #header>快捷入口</template>
          <div class="quick-list">
            <el-button type="primary" @click="$router.push(userStore.isUser ? '/seat' : '/schedule')">
              {{ userStore.isUser ? '去选座购票' : '去管理班次' }}
            </el-button>
            <el-button @click="$router.push('/notice')">查看公告</el-button>
            <el-button @click="$router.push(userStore.isUser ? '/my-order' : '/order')">
              {{ userStore.isUser ? '我的订单' : '订单中心' }}
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getDashboardStats, getPublicNoticeList } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const stats = ref({})
const notices = ref([])

const loadData = async () => {
  if (!userStore.isUser) {
    const res = await getDashboardStats()
    stats.value = res.data || {}
  }
  const noticeRes = await getPublicNoticeList()
  notices.value = noticeRes.data || []
}

onMounted(loadData)
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.tip-block {
  line-height: 1.8;
  color: #4b5563;
}

.notice-text {
  margin-top: 6px;
  color: #666;
}

.quick-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}
</style>
