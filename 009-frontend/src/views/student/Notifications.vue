<template>
  <div class="notifications-container">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">消息通知</span>
      </template>
      <template #extra>
        <el-button @click="handleMarkAllRead" :disabled="unreadCount === 0">
          全部标记已读
        </el-button>
      </template>
    </el-page-header>

    <el-card class="content-card">
      <el-empty v-if="tableData.length === 0" description="暂无消息" />
      
      <el-timeline v-else>
        <el-timeline-item
          v-for="item in tableData"
          :key="item.id"
          :timestamp="item.createTime"
          placement="top"
          :color="item.isRead ? '#909399' : '#409EFF'"
        >
          <el-card :class="{ 'unread-card': !item.isRead }">
            <div class="notification-header">
              <el-tag :type="getTypeTag(item.type)">{{ getTypeName(item.type) }}</el-tag>
              <el-tag v-if="!item.isRead" type="danger" size="small">未读</el-tag>
            </div>
            <h4 class="title">{{ item.title }}</h4>
            <p class="content">{{ item.content }}</p>
            <div class="actions">
              <el-button
                v-if="!item.isRead"
                size="small"
                @click="handleMarkRead(item.id)"
              >
                标记已读
              </el-button>
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <el-pagination
        v-if="tableData.length > 0"
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { getNotificationList, markRead, markAllRead } from '@/api/notification'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref([])

const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

const unreadCount = computed(() => {
  return tableData.value.filter(item => !item.isRead).length
})

const getTypeName = (type) => {
  const map = {
    'ARRIVAL': '到达通知',
    'OVERDUE': '超期提醒',
    'RETURN': '退件通知'
  }
  return map[type] || type
}

const getTypeTag = (type) => {
  const map = {
    'ARRIVAL': 'success',
    'OVERDUE': 'warning',
    'RETURN': 'danger'
  }
  return map[type] || 'info'
}

const handleMarkRead = async (id) => {
  try {
    await markRead(id)
    ElMessage.success('标记成功')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const handleMarkAllRead = async () => {
  try {
    await markAllRead()
    ElMessage.success('全部标记已读')
    loadData()
  } catch (error) {
    console.error(error)
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getNotificationList({ page: pagination.page, size: pagination.size })
    tableData.value = res.data.records
    pagination.total = res.data.total
  } catch (error) {
    console.error(error)
    ElMessage.error('加载失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.notifications-container {
  padding: 20px;
}

.page-title {
  font-size: 18px;
  font-weight: bold;
}

.content-card {
  margin-top: 20px;
}

.unread-card {
  background: #f0f9ff;
  border-left: 3px solid #409EFF;
}

.notification-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.title {
  margin: 10px 0;
  font-size: 16px;
  font-weight: bold;
}

.content {
  color: #606266;
  line-height: 1.6;
  margin: 10px 0;
}

.actions {
  margin-top: 10px;
}
</style>

