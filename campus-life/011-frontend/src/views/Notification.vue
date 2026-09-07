<template>
  <div class="notification-container">
    <el-card>
      <div class="header">
        <h2>消息通知</h2>
        <el-button type="primary" size="small" @click="handleMarkAllRead">全部已读</el-button>
      </div>
      
      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部" name="all">
          <div class="notification-list">
            <div
              v-for="item in filteredList"
              :key="item.id"
              class="notification-item"
              :class="{ unread: !item.isRead }"
              @click="handleRead(item)"
            >
              <el-avatar :src="item.fromUser?.avatar" :size="50" />
              <div class="content">
                <div class="text">
                  <span class="nickname">{{ item.fromUser?.nickname }}</span>
                  {{ item.content }}
                </div>
                <div class="time">{{ item.createTime }}</div>
              </div>
              <el-badge v-if="!item.isRead" is-dot />
            </div>
          </div>
          
          <el-empty v-if="filteredList.length === 0" description="暂无消息" />
        </el-tab-pane>
        
        <el-tab-pane label="点赞" name="LIKE">
          <div class="notification-list">
            <div
              v-for="item in filteredList"
              :key="item.id"
              class="notification-item"
              :class="{ unread: !item.isRead }"
              @click="handleRead(item)"
            >
              <el-avatar :src="item.fromUser?.avatar" :size="50" />
              <div class="content">
                <div class="text">
                  <span class="nickname">{{ item.fromUser?.nickname }}</span>
                  {{ item.content }}
                </div>
                <div class="time">{{ item.createTime }}</div>
              </div>
              <el-badge v-if="!item.isRead" is-dot />
            </div>
          </div>
          
          <el-empty v-if="filteredList.length === 0" description="暂无点赞" />
        </el-tab-pane>
        
        <el-tab-pane label="评论" name="COMMENT">
          <div class="notification-list">
            <div
              v-for="item in filteredList"
              :key="item.id"
              class="notification-item"
              :class="{ unread: !item.isRead }"
              @click="handleRead(item)"
            >
              <el-avatar :src="item.fromUser?.avatar" :size="50" />
              <div class="content">
                <div class="text">
                  <span class="nickname">{{ item.fromUser?.nickname }}</span>
                  {{ item.content }}
                </div>
                <div class="time">{{ item.createTime }}</div>
              </div>
              <el-badge v-if="!item.isRead" is-dot />
            </div>
          </div>
          
          <el-empty v-if="filteredList.length === 0" description="暂无评论" />
        </el-tab-pane>
        
        <el-tab-pane label="关注" name="FOLLOW">
          <div class="notification-list">
            <div
              v-for="item in filteredList"
              :key="item.id"
              class="notification-item"
              :class="{ unread: !item.isRead }"
              @click="handleRead(item)"
            >
              <el-avatar :src="item.fromUser?.avatar" :size="50" />
              <div class="content">
                <div class="text">
                  <span class="nickname">{{ item.fromUser?.nickname }}</span>
                  {{ item.content }}
                </div>
                <div class="time">{{ item.createTime }}</div>
              </div>
              <el-badge v-if="!item.isRead" is-dot />
            </div>
          </div>
          
          <el-empty v-if="filteredList.length === 0" description="暂无关注" />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getNotificationList, markAsRead, markAllAsRead } from '@/api/notification'

const router = useRouter()
const activeTab = ref('all')
const notifications = ref([])

const filteredList = computed(() => {
  if (activeTab.value === 'all') {
    return notifications.value
  }
  return notifications.value.filter(item => item.type === activeTab.value)
})

const fetchNotifications = async () => {
  try {
    const res = await getNotificationList()
    notifications.value = res.data
  } catch (error) {
    ElMessage.error('获取通知失败')
  }
}

const handleRead = async (item) => {
  if (!item.isRead) {
    try {
      await markAsRead(item.id)
      item.isRead = 1
    } catch (error) {
      console.error('标记已读失败:', error)
    }
  }
  
  if (item.relatedId) {
    if (item.type === 'FOLLOW') {
      router.push(`/user/${item.fromUserId}`)
    } else {
      router.push(`/video/${item.relatedId}`)
    }
  }
}

const handleMarkAllRead = async () => {
  try {
    await markAllAsRead()
    notifications.value.forEach(item => {
      item.isRead = 1
    })
    ElMessage.success('已全部标记为已读')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  fetchNotifications()
})
</script>

<style scoped>
.notification-container {
  padding: 20px;
  height: 100%;
  overflow-y: auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h2 {
  margin: 0;
}

.notification-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.notification-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.notification-item:hover {
  background: #f5f5f5;
}

.notification-item.unread {
  background: #f0f8ff;
}

.content {
  flex: 1;
}

.text {
  color: #333;
  line-height: 1.6;
  margin-bottom: 5px;
}

.nickname {
  font-weight: 500;
  color: #409eff;
}

.time {
  font-size: 12px;
  color: #999;
}
</style>

