<template>
  <div class="my-songs-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span class="card-title">我的作品</span>
          <el-button type="primary" @click="router.push('/publish')">发布新作品</el-button>
        </div>
      </template>
      <el-table :data="songs" stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="viewCount" label="浏览" width="80" />
        <el-table-column prop="likeCount" label="点赞" width="80" />
        <el-table-column label="审核状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.auditStatus === 1 ? 'success' : row.auditStatus === 2 ? 'danger' : 'warning'">
              {{ row.auditStatus === 1 ? '已通过' : row.auditStatus === 2 ? '未通过' : '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="发布时间" width="120">
          <template #default="{ row }">{{ formatTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" link @click="router.push(`/song/${row.id}`)">查看</el-button>
            <el-button type="danger" link @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-if="total > 0"
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="fetchData"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { userApi, songApi } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const songs = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const fetchData = async () => {
  const res = await userApi.getMySongs({ pageNum: pageNum.value, pageSize: pageSize.value })
  songs.value = res.data.records
  total.value = res.data.total
}

onMounted(fetchData)

const formatTime = (time) => new Date(time).toLocaleDateString()

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定要删除这首民歌吗？', '提示', { type: 'warning' })
  await songApi.delete(id)
  ElMessage.success('删除成功')
  fetchData()
}
</script>

<style scoped lang="scss">
.my-songs-page {
  max-width: 1000px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
