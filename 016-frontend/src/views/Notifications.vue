<template>
  <div class="notifications-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>系统通知</span>
          <el-button v-if="unreadCount > 0" type="primary" size="small" @click="markAllRead">
            全部标记为已读
          </el-button>
        </div>
      </template>

      <el-empty v-if="notifications.length === 0" description="暂无通知" />
      <div v-else class="notification-list">
        <div
          v-for="notification in notifications"
          :key="notification.id"
          class="notification-item"
          :class="{ unread: !notification.isRead }"
          @click="handleClick(notification)"
        >
          <div class="notification-icon">
            <el-icon v-if="notification.type === 1" color="#409eff"><bell /></el-icon>
            <el-icon v-else-if="notification.type === 2" color="#67c23a"><box /></el-icon>
            <el-icon v-else color="#e6a23c"><coin /></el-icon>
          </div>
          <div class="notification-content">
            <div class="notification-title">{{ notification.title }}</div>
            <div class="notification-text">{{ notification.content }}</div>
            <div class="notification-time">{{ formatTime(notification.createTime) }}</div>
          </div>
          <el-badge v-if="!notification.isRead" is-dot class="notification-badge" />
        </div>
      </div>

      <el-pagination
        v-if="total > 0"
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadNotifications"
        class="pagination"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getNotifications, markAsRead } from '../api/notification'
import { Bell, Box, Coin } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const notifications = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const unreadCount = computed(() => notifications.value.filter(n => !n.isRead).length)

const loadNotifications = async () => {
  try {
    const data = await getNotifications({
      pageNum: currentPage.value,
      pageSize: pageSize.value
    })
    notifications.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('加载通知失败', error)
  }
}

const handleClick = async (notification) => {
  if (!notification.isRead) {
    try {
      await markAsRead(notification.id)
      notification.isRead = 1
    } catch (error) {
      console.error('标记已读失败', error)
    }
  }

  if (notification.linkType && notification.linkId) {
    if (notification.linkType === 'order') {
      router.push(`/order-detail/${notification.linkId}`)
    }
  }
}

const markAllRead = async () => {
  try {
    for (const notification of notifications.value) {
      if (!notification.isRead) {
        await markAsRead(notification.id)
      }
    }
    ElMessage.success('已全部标记为已读')
    loadNotifications()
  } catch (error) {
    console.error('标记失败', error)
  }
}

const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  
  if (diff < 60000) {
    return '刚刚'
  } else if (diff < 3600000) {
    return Math.floor(diff / 60000) + '分钟前'
  } else if (diff < 86400000) {
    return Math.floor(diff / 3600000) + '小时前'
  } else if (diff < 604800000) {
    return Math.floor(diff / 86400000) + '天前'
  } else {
    return date.toLocaleString('zh-CN')
  }
}

onMounted(() => {
  loadNotifications()
})
</script>

<style scoped>
.notifications-container {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notification-list {
  display: flex;
  flex-direction: column;
}

.notification-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 16px;
  border-bottom: 1px solid #ebeef5;
  cursor: pointer;
  transition: background-color 0.3s;
  position: relative;
}

.notification-item:hover {
  background-color: #f5f7fa;
}

.notification-item.unread {
  background-color: #ecf5ff;
}

.notification-icon {
  font-size: 24px;
  margin-top: 4px;
}

.notification-content {
  flex: 1;
}

.notification-title {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 8px;
}

.notification-text {
  font-size: 14px;
  color: #606266;
  margin-bottom: 8px;
}

.notification-time {
  font-size: 12px;
  color: #909399;
}

.notification-badge {
  position: absolute;
  right: 16px;
  top: 16px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>

