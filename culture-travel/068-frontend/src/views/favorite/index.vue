<template>
  <div class="page-container">
    <el-card>
      <template #header>景点列表</template>
      <div class="search-bar">
        <el-input v-model="spotQuery.name" placeholder="景点名称" clearable style="width: 220px" />
        <el-input v-model="spotQuery.city" placeholder="城市" clearable style="width: 180px" />
        <el-button type="primary" @click="loadSpots">查询</el-button>
      </div>
      <el-table :data="spotData" v-loading="spotLoading" border>
        <el-table-column prop="name" label="景点" min-width="160" />
        <el-table-column prop="city" label="城市" width="100" />
        <el-table-column prop="tags" label="标签" min-width="180" />
        <el-table-column prop="price" label="参考价" width="100" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link :type="isFavored(row.id) ? 'danger' : 'primary'" @click="handleToggle(row.id)">
              {{ isFavored(row.id) ? '取消收藏' : '收藏' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card>
      <template #header>我的收藏</template>
      <div class="search-bar">
        <el-input v-model="query.name" placeholder="景点名称" clearable style="width: 220px" />
        <el-input v-model="query.city" placeholder="城市" clearable style="width: 180px" />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" border>
        <el-table-column prop="spotName" label="景点名称" min-width="160" />
        <el-table-column prop="city" label="城市" width="100" />
        <el-table-column prop="tags" label="标签" min-width="180" />
        <el-table-column prop="price" label="参考价" width="100" />
        <el-table-column prop="createTime" label="收藏时间" min-width="170" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="danger" @click="handleToggle(row.spotId)">取消收藏</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager">
        <el-pagination
          v-model:current-page="query.pageNum"
          v-model:page-size="query.pageSize"
          :total="total"
          :page-sizes="[10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getFavoritePage, getSpotList, toggleFavorite } from '../../api'

const loading = ref(false)
const total = ref(0)
const tableData = ref([])
const spotLoading = ref(false)
const spotData = ref([])
const favoredSet = ref(new Set())

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  city: ''
})

const spotQuery = reactive({
  name: '',
  city: ''
})

const loadFavoriteSet = async () => {
  const res = await getFavoritePage({ pageNum: 1, pageSize: 1000 })
  const ids = (res.data.records || []).map(item => item.spotId)
  favoredSet.value = new Set(ids)
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getFavoritePage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const loadSpots = async () => {
  spotLoading.value = true
  try {
    const res = await getSpotList(spotQuery)
    spotData.value = res.data || []
  } finally {
    spotLoading.value = false
  }
}

const isFavored = (spotId) => favoredSet.value.has(spotId)

const handleToggle = async (spotId) => {
  const res = await toggleFavorite({ spotId })
  ElMessage.success(res.data.favored ? '收藏成功' : '已取消收藏')
  await Promise.all([loadFavoriteSet(), loadData()])
}

onMounted(async () => {
  await Promise.all([loadFavoriteSet(), loadData(), loadSpots()])
})
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.search-bar {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 14px;
}

.pager {
  margin-top: 14px;
  display: flex;
  justify-content: flex-end;
}
</style>
