<template>
  <div class="announcement-list">
    <el-card>
      <template #header><span>系统公告</span></template>
      <div v-if="list.length">
        <div v-for="item in list" :key="item.id" class="announcement-item" @click="openDetail(item)">
          <div class="item-header">
            <el-tag v-if="item.isTop" type="danger" size="small">置顶</el-tag>
            <span class="title">{{ item.title }}</span>
            <span class="time">{{ item.createTime }}</span>
          </div>
          <p class="summary">{{ item.content?.substring(0, 100) }}...</p>
        </div>
      </div>
      <el-empty v-else description="暂无公告" />
      <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    </el-card>
    <el-dialog v-model="detailDialog" :title="currentItem?.title" width="600px">
      <div class="detail-content">
        <p class="meta"><el-icon><Clock /></el-icon>{{ currentItem?.createTime }}</p>
        <el-divider />
        <div class="content">{{ currentItem?.content }}</div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAnnouncementList } from '../api'

const query = ref({ pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const detailDialog = ref(false)
const currentItem = ref(null)

const loadData = async () => {
  const res = await getAnnouncementList(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
}

const openDetail = (item) => {
  currentItem.value = item
  detailDialog.value = true
}

onMounted(() => loadData())
</script>

<style scoped>
.announcement-item { padding: 20px; border-bottom: 1px solid #eee; cursor: pointer; transition: background 0.3s; }
.announcement-item:hover { background: #f5f7fa; }
.announcement-item:last-child { border-bottom: none; }
.item-header { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }
.title { font-size: 16px; font-weight: bold; flex: 1; }
.time { color: #999; font-size: 13px; }
.summary { color: #666; font-size: 14px; line-height: 1.6; }
.pagination { margin-top: 20px; justify-content: center; }
.detail-content .meta { color: #999; font-size: 14px; display: flex; align-items: center; gap: 4px; }
.detail-content .content { line-height: 1.8; white-space: pre-wrap; }
</style>
