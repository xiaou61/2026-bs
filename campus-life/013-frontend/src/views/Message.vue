<template>
  <div class="message-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>消息通知</span>
          <el-button type="primary" size="small" @click="handleMarkAllRead">全部标为已读</el-button>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="loadNotifications">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="订单通知" name="ORDER" />
        <el-tab-pane label="支付通知" name="PAYMENT" />
        <el-tab-pane label="审核通知" name="AUDIT" />
        <el-tab-pane label="系统通知" name="SYSTEM" />
      </el-tabs>

      <div class="notification-list">
        <div 
          v-for="item in notifications" 
          :key="item.id" 
          class="notification-item"
          :class="{ unread: item.isRead === 0 }"
          @click="handleRead(item)"
        >
          <div class="notification-icon">
            <el-icon :size="30" :color="getTypeColor(item.type)">
              <Bell v-if="item.type === 'ORDER'" />
              <Wallet v-else-if="item.type === 'PAYMENT'" />
              <Checked v-else-if="item.type === 'AUDIT'" />
              <InfoFilled v-else />
            </el-icon>
          </div>

          <div class="notification-content">
            <h4>{{ item.title }}</h4>
            <p>{{ item.content }}</p>
            <span class="time">{{ item.createTime }}</span>
          </div>

          <div class="notification-status">
            <el-badge v-if="item.isRead === 0" is-dot />
          </div>
        </div>

        <el-empty v-if="notifications.length === 0" description="暂无消息" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNotificationList, markRead, markAllRead } from '@/api/notification'
import { ElMessage } from 'element-plus'

const activeTab = ref('all')
const notifications = ref([])

const getTypeColor = (type) => {
  const map = {
    'ORDER': '#409eff',
    'PAYMENT': '#67c23a',
    'AUDIT': '#e6a23c',
    'SYSTEM': '#909399'
  }
  return map[type] || '#909399'
}

const loadNotifications = async () => {
  try {
    const res = await getNotificationList()
    if (activeTab.value === 'all') {
      notifications.value = res.data
    } else {
      notifications.value = res.data.filter(item => item.type === activeTab.value)
    }
  } catch (error) {
    console.error(error)
  }
}

const handleRead = async (item) => {
  if (item.isRead === 0) {
    try {
      await markRead(item.id)
      item.isRead = 1
    } catch (error) {
      console.error(error)
    }
  }
}

const handleMarkAllRead = async () => {
  try {
    await markAllRead()
    ElMessage.success('已全部标为已读')
    loadNotifications()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadNotifications()
})
</script>

<style scoped>
.notification-list {
  min-height: 400px;
}

.notification-item {
  display: flex;
  gap: 15px;
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
  cursor: pointer;
  transition: background 0.3s;
}

.notification-item:hover {
  background: #f5f7fa;
}

.notification-item.unread {
  background: #ecf5ff;
}

.notification-icon {
  flex-shrink: 0;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 50%;
}

.notification-content {
  flex: 1;
}

.notification-content h4 {
  margin-bottom: 8px;
  color: #303133;
}

.notification-content p {
  margin-bottom: 8px;
  color: #606266;
  font-size: 14px;
}

.time {
  color: #909399;
  font-size: 12px;
}

.notification-status {
  flex-shrink: 0;
}
</style>

