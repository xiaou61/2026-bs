<template>
  <div class="my-favorites">
    <el-card>
      <template #header>
        <div class="header">
          <span>我的收藏</span>
          <el-radio-group v-model="query.type" @change="loadData">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button label="spot">景点</el-radio-button>
            <el-radio-button label="note">游记</el-radio-button>
          </el-radio-group>
        </div>
      </template>
      <el-table :data="list" v-loading="loading">
        <el-table-column prop="targetType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.targetType === 'spot' ? 'success' : 'warning'">{{ row.targetType === 'spot' ? '景点' : '游记' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="targetName" label="名称" />
        <el-table-column prop="createTime" label="收藏时间" width="170" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewDetail(row)">查看</el-button>
            <el-button type="danger" link @click="cancelFavorite(row)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-if="total > 0" background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="loadData" class="pagination" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyFavorites, removeFavorite } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const query = ref({ type: '', pageNum: 1, pageSize: 10 })
const list = ref([])
const total = ref(0)
const loading = ref(false)

const loadData = async () => {
  loading.value = true
  const res = await getMyFavorites(query.value)
  list.value = res.data.records || []
  total.value = res.data.total || 0
  loading.value = false
}

const viewDetail = (row) => {
  if (row.targetType === 'spot') {
    router.push(`/spots/${row.targetId}`)
  } else {
    router.push(`/notes/${row.targetId}`)
  }
}

const cancelFavorite = async (row) => {
  await ElMessageBox.confirm('确认取消收藏？', '提示')
  await removeFavorite(row.targetId, row.targetType)
  ElMessage.success('已取消收藏')
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 20px; justify-content: center; }
</style>
