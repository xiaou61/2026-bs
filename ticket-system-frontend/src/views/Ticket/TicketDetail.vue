<template>
  <div class="ticket-detail-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>票券详情</span>
          <el-button @click="goBack">返回</el-button>
        </div>
      </template>

      <div class="ticket-content">
        <div class="ticket-info">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="票券号">{{ ticket.ticketNo }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag :type="getStatusType(ticket.status)">
                {{ getStatusText(ticket.status) }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="电影名称">{{ ticket.movieTitle }}</el-descriptions-item>
            <el-descriptions-item label="影院名称">{{ ticket.cinemaName }}</el-descriptions-item>
            <el-descriptions-item label="影厅">{{ ticket.hallName }}</el-descriptions-item>
            <el-descriptions-item label="放映日期">{{ ticket.showDate }}</el-descriptions-item>
            <el-descriptions-item label="放映时间">{{ ticket.showTime }}</el-descriptions-item>
            <el-descriptions-item label="座位信息">{{ ticket.seatInfo }}</el-descriptions-item>
            <el-descriptions-item label="票价">
              <span style="color: #f56c6c; font-size: 16px; font-weight: bold;">¥{{ ticket.price }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="购买时间">{{ ticket.createTime }}</el-descriptions-item>
            <el-descriptions-item label="核销时间">{{ ticket.useTime || '未核销' }}</el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="qrcode-section">
          <div class="qrcode-title">电子票二维码</div>
          <div class="qrcode-wrapper">
            <el-image v-if="ticket.qrCode" :src="ticket.qrCode" fit="contain" class="qrcode-image" />
            <div v-else class="qrcode-placeholder">暂无二维码</div>
          </div>
          <div class="qrcode-tip">请在检票时出示此二维码</div>
        </div>
      </div>

      <div class="action-section">
        <el-alert v-if="ticket.status === 'unused'" type="success" :closable="false" center>
          <template #title>
            <div style="font-size: 16px;">票券有效，请按时到场观影</div>
          </template>
        </el-alert>
        <el-alert v-if="ticket.status === 'used'" type="info" :closable="false" center>
          <template #title>
            <div style="font-size: 16px;">票券已使用</div>
          </template>
        </el-alert>
        <el-alert v-if="ticket.status === 'expired'" type="error" :closable="false" center>
          <template #title>
            <div style="font-size: 16px;">票券已过期</div>
          </template>
        </el-alert>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ticketApi } from '@/api'

const route = useRoute()
const router = useRouter()

const ticket = ref({
  ticketNo: '',
  status: '',
  movieTitle: '',
  cinemaName: '',
  hallName: '',
  showDate: '',
  showTime: '',
  seatInfo: '',
  price: 0,
  qrCode: '',
  createTime: '',
  useTime: ''
})

const loadTicketDetail = async () => {
  try {
    const res = await ticketApi.getTicketDetail(route.params.id)
    ticket.value = res.data
  } catch (error) {
    ElMessage.error('获取票券详情失败')
  }
}

const goBack = () => {
  router.back()
}

const getStatusType = (status) => {
  const types = {
    unused: 'success',
    used: 'info',
    expired: 'danger'
  }
  return types[status] || ''
}

const getStatusText = (status) => {
  const texts = {
    unused: '未使用',
    used: '已使用',
    expired: '已过期'
  }
  return texts[status] || status
}

onMounted(() => {
  loadTicketDetail()
})
</script>

<style scoped>
.ticket-detail-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.ticket-content {
  display: flex;
  gap: 40px;
  margin-bottom: 30px;
}

.ticket-info {
  flex: 1;
}

.qrcode-section {
  width: 300px;
  text-align: center;
}

.qrcode-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.qrcode-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 250px;
  height: 250px;
  margin: 0 auto;
  border: 2px dashed #dcdfe6;
  border-radius: 8px;
}

.qrcode-image {
  width: 230px;
  height: 230px;
}

.qrcode-placeholder {
  color: #909399;
  font-size: 14px;
}

.qrcode-tip {
  margin-top: 15px;
  color: #909399;
  font-size: 12px;
}

.action-section {
  margin-top: 30px;
}
</style>
