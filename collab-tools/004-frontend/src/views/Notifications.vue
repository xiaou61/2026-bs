<template>
  <div class="notifications-container">
    <div class="notifications-header">
      <h2>通知中心</h2>
      <el-button type="primary" @click="handleMarkAllRead" :disabled="unreadCount === 0">
        <el-icon><Check /></el-icon>
        全部已读
      </el-button>
    </div>
    
    <div class="notifications-content">
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="全部通知" name="all">
          <div class="notification-list">
            <div 
              v-for="notify in notifications" 
              :key="notify.id"
              class="notification-item"
              :class="{ unread: notify.isRead === 0 }"
              @click="handleRead(notify)"
            >
              <div class="notification-icon">
                <el-icon v-if="notify.notifyType === 'LIKE'" color="#f56c6c">
                  <Star />
                </el-icon>
                <el-icon v-else-if="notify.notifyType === 'COMMENT'" color="#409eff">
                  <ChatDotRound />
                </el-icon>
                <el-icon v-else color="#67c23a">
                  <Bell />
                </el-icon>
              </div>
              
              <div class="notification-content">
                <div class="notification-title">{{ notify.title }}</div>
                <div class="notification-text">{{ notify.content }}</div>
                <div class="notification-time">{{ formatTime(notify.createTime) }}</div>
              </div>
              
              <div v-if="notify.isRead === 0" class="unread-dot"></div>
            </div>
            
            <el-empty v-if="notifications.length === 0" description="暂无通知" />
          </div>
        </el-tab-pane>
        
        <el-tab-pane name="unread">
          <template #label>
            <el-badge :value="unreadCount" :hidden="unreadCount === 0">
              <span>未读通知</span>
            </el-badge>
          </template>
          
          <div class="notification-list">
            <div 
              v-for="notify in unreadNotifications" 
              :key="notify.id"
              class="notification-item unread"
              @click="handleRead(notify)"
            >
              <div class="notification-icon">
                <el-icon v-if="notify.notifyType === 'LIKE'" color="#f56c6c">
                  <Star />
                </el-icon>
                <el-icon v-else-if="notify.notifyType === 'COMMENT'" color="#409eff">
                  <ChatDotRound />
                </el-icon>
                <el-icon v-else color="#67c23a">
                  <Bell />
                </el-icon>
              </div>
              
              <div class="notification-content">
                <div class="notification-title">{{ notify.title }}</div>
                <div class="notification-text">{{ notify.content }}</div>
                <div class="notification-time">{{ formatTime(notify.createTime) }}</div>
              </div>
              
              <div class="unread-dot"></div>
            </div>
            
            <el-empty v-if="unreadNotifications.length === 0" description="暂无未读通知" />
          </div>
        </el-tab-pane>
      </el-tabs>
      
      <div v-if="hasMore" class="load-more">
        <el-button @click="loadMore" :loading="loading">加载更多</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getNotifications, getUnreadNotifications, markAsRead, markAllAsRead } from '@/api/notification'
import websocket from '@/utils/websocket'

const activeTab = ref('all')
const notifications = ref([])
const unreadNotifications = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(20)
const hasMore = ref(true)

const unreadCount = computed(() => unreadNotifications.value.length)

const loadNotifications = async (reset = false) => {
  if (reset) {
    currentPage.value = 1
    notifications.value = []
  }
  
  loading.value = true
  try {
    const data = await getNotifications(currentPage.value, pageSize.value)
    
    if (reset) {
      notifications.value = data?.records || []
    } else {
      notifications.value.push(...(data?.records || []))
    }
    
    hasMore.value = notifications.value.length < (data?.total || 0)
  } catch (error) {
    console.error('加载通知失败', error)
  } finally {
    loading.value = false
  }
}

const loadUnreadNotifications = async () => {
  try {
    const data = await getUnreadNotifications()
    unreadNotifications.value = data || []
  } catch (error) {
    console.error('加载未读通知失败', error)
  }
}

const handleTabChange = (name) => {
  if (name === 'unread') {
    loadUnreadNotifications()
  }
}

const handleRead = async (notify) => {
  if (notify.isRead === 1) return
  
  try {
    await markAsRead(notify.id)
    notify.isRead = 1
    
    const index = unreadNotifications.value.findIndex(n => n.id === notify.id)
    if (index !== -1) {
      unreadNotifications.value.splice(index, 1)
    }
  } catch (error) {
    console.error('标记已读失败', error)
  }
}

const handleMarkAllRead = async () => {
  try {
    await markAllAsRead()
    ElMessage.success('已全部标记为已读')
    
    notifications.value.forEach(n => n.isRead = 1)
    unreadNotifications.value = []
  } catch (error) {
    console.error('标记全部已读失败', error)
  }
}

const loadMore = () => {
  currentPage.value++
  loadNotifications()
}

const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  const now = new Date()
  const diff = now - date
  
  const minute = 60 * 1000
  const hour = 60 * minute
  const day = 24 * hour
  
  if (diff < minute) {
    return '刚刚'
  } else if (diff < hour) {
    return Math.floor(diff / minute) + '分钟前'
  } else if (diff < day) {
    return Math.floor(diff / hour) + '小时前'
  } else if (diff < 7 * day) {
    return Math.floor(diff / day) + '天前'
  } else {
    return date.toLocaleDateString('zh-CN')
  }
}

onMounted(() => {
  loadNotifications(true)
  loadUnreadNotifications()
  
  websocket.onNotification((notification) => {
    notifications.value.unshift(notification)
    unreadNotifications.value.unshift(notification)
  })
})
</script>

<style scoped>
.notifications-container {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.notifications-header {
  padding: 20px 30px;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notifications-header h2 {
  margin: 0;
  font-size: 24px;
}

.notifications-content {
  flex: 1;
  padding: 20px 30px;
  overflow-y: auto;
}

.notification-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.notification-item {
  display: flex;
  align-items: center;
  padding: 20px;
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
  gap: 15px;
}

.notification-item:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transform: translateX(5px);
}

.notification-item.unread {
  background-color: #f0f7ff;
  border-color: #409eff;
}

.notification-icon {
  font-size: 30px;
  flex-shrink: 0;
}

.notification-content {
  flex: 1;
}

.notification-title {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #333;
}

.notification-text {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
  line-height: 1.5;
}

.notification-time {
  font-size: 12px;
  color: #999;
}

.unread-dot {
  width: 8px;
  height: 8px;
  background-color: #f56c6c;
  border-radius: 50%;
  flex-shrink: 0;
}

.load-more {
  text-align: center;
  margin-top: 20px;
  padding: 20px 0;
}
</style>

