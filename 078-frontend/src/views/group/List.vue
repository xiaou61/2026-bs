<template>
  <div class="page-container">
    <el-card>
      <el-tabs v-model="activeTab" @tab-change="loadData">
        <el-tab-pane label="全部" name="all" />
        <el-tab-pane label="拼团中" name="pending" />
        <el-tab-pane label="成功" name="success" />
        <el-tab-pane label="失败" name="failed" />
      </el-tabs>
      <div v-for="item in groupList" :key="item.id" class="group-item">
        <div class="group-header">
          <span class="order-no">团号: {{ item.id }}</span>
          <el-tag :type="statusMap[item.status]?.type">{{ statusMap[item.status]?.text }}</el-tag>
        </div>
        <div class="group-content">
          <el-image :src="item.activity?.product?.cover" style="width: 80px; height: 80px" fit="cover" />
          <div class="group-info">
            <div class="product-name">{{ item.activity?.product?.name }}</div>
            <div class="group-price">团购价: ¥{{ item.activity?.groupPrice }}</div>
            <div class="group-progress">
              <span>{{ item.currentCount }}/{{ item.targetCount }}人</span>
              <el-progress :percentage="Math.round(item.currentCount / item.targetCount * 100)" :stroke-width="10" style="width: 150px" />
            </div>
          </div>
          <div class="group-time">
            <div v-if="item.status === 0">剩余: {{ getRemainTime(item.expireTime) }}</div>
            <div v-if="item.isLeader">我发起的团</div>
          </div>
        </div>
        <div class="group-actions" v-if="item.status === 0">
          <el-button type="primary" size="small" @click="handleShare(item)">邀请好友</el-button>
        </div>
      </div>
      <el-empty v-if="!groupList.length" description="暂无拼团记录" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMyGroups } from '../../api'

const activeTab = ref('all')
const groupList = ref([])
const statusMap = {
  0: { text: '拼团中', type: 'warning' },
  1: { text: '成功', type: 'success' },
  2: { text: '失败', type: 'danger' }
}

const loadData = async () => {
  const params = {}
  if (activeTab.value === 'pending') params.status = 0
  else if (activeTab.value === 'success') params.status = 1
  else if (activeTab.value === 'failed') params.status = 2
  const res = await getMyGroups(params)
  groupList.value = res.data
}

const getRemainTime = (expireTime) => {
  const remain = new Date(expireTime) - new Date()
  if (remain <= 0) return '已过期'
  const hours = Math.floor(remain / 3600000)
  const minutes = Math.floor((remain % 3600000) / 60000)
  return `${hours}小时${minutes}分钟`
}

const handleShare = (item) => {
  const url = `${window.location.origin}/activity/${item.activityId}?groupId=${item.id}`
  navigator.clipboard.writeText(url)
  ElMessage.success('链接已复制，快去分享给好友吧')
}

onMounted(loadData)
</script>

<style scoped>
.page-container { padding: 20px; }
.group-item { border: 1px solid #eee; border-radius: 8px; padding: 15px; margin-bottom: 15px; }
.group-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
.order-no { color: #999; font-size: 12px; }
.group-content { display: flex; gap: 15px; }
.group-info { flex: 1; }
.product-name { font-weight: bold; margin-bottom: 8px; }
.group-price { color: #f56c6c; margin-bottom: 8px; }
.group-progress { display: flex; align-items: center; gap: 10px; }
.group-time { text-align: right; color: #999; font-size: 12px; }
.group-actions { margin-top: 10px; text-align: right; }
</style>
