<template>
  <div class="announcements-page">
    <el-card>
      <template #header><span class="card-title">系统公告</span></template>
      <div class="announcement-list">
        <div v-for="ann in announcements" :key="ann.id" class="announcement-item" @click="showDetail(ann)">
          <div class="announcement-title">
            <el-icon><Bell /></el-icon>
            <span>{{ ann.title }}</span>
          </div>
          <div class="announcement-time">{{ formatTime(ann.createTime) }}</div>
        </div>
        <el-empty v-if="announcements.length === 0" description="暂无公告" />
      </div>
      <el-pagination
        v-if="total > 0"
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchData"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="currentAnnouncement?.title" width="600px">
      <div class="announcement-content" v-html="currentAnnouncement?.content"></div>
      <div class="announcement-footer">发布时间：{{ formatTime(currentAnnouncement?.createTime) }}</div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { announcementApi } from '@/api'

const announcements = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const currentAnnouncement = ref(null)

const fetchData = async () => {
  const res = await announcementApi.getList({ pageNum: pageNum.value, pageSize: pageSize.value })
  announcements.value = res.data.records
  total.value = res.data.total
}

onMounted(fetchData)

const formatTime = (time) => time ? new Date(time).toLocaleString() : ''

const showDetail = (ann) => {
  currentAnnouncement.value = ann
  dialogVisible.value = true
}
</script>

<style scoped lang="scss">
.announcements-page {
  max-width: 800px;
  margin: 0 auto;
}

.announcement-list {
  .announcement-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #eee;
    cursor: pointer;

    &:hover {
      background: #f5f5f5;
    }

    .announcement-title {
      display: flex;
      align-items: center;
      gap: 10px;

      .el-icon {
        color: #f56c6c;
      }
    }

    .announcement-time {
      color: #999;
      font-size: 12px;
    }
  }
}

.announcement-content {
  line-height: 1.8;
  color: #666;
}

.announcement-footer {
  margin-top: 20px;
  text-align: right;
  color: #999;
  font-size: 12px;
}
</style>
