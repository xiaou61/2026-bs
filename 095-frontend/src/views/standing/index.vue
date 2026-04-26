<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.seasonId" placeholder="赛季" clearable>
          <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
        </el-select>
        <el-input v-model="query.teamName" placeholder="球队名称" clearable />
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="!isFan && query.seasonId" type="success" @click="handleRefresh">重算积分榜</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="ranking" label="排名" min-width="80" />
        <el-table-column prop="teamName" label="球队" min-width="160" />
        <el-table-column prop="seasonName" label="赛季" min-width="140" />
        <el-table-column prop="playedCount" label="场次" min-width="80" />
        <el-table-column prop="winCount" label="胜" min-width="70" />
        <el-table-column prop="drawCount" label="平" min-width="70" />
        <el-table-column prop="lossCount" label="负" min-width="70" />
        <el-table-column prop="goalFor" label="进球" min-width="70" />
        <el-table-column prop="goalAgainst" label="失球" min-width="70" />
        <el-table-column prop="goalDiff" label="净胜球" min-width="80" />
        <el-table-column prop="points" label="积分" min-width="80" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getSeasonOptions, getStandingList, refreshStanding } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isFan = computed(() => (userStore.user?.role || '').toUpperCase() === 'FAN')
const loading = ref(false)
const tableData = ref([])
const seasonOptions = ref([])
const query = reactive({ pageNum: 1, pageSize: 20, seasonId: null, teamName: '' })

const loadOptions = async () => {
  const res = await getSeasonOptions({})
  seasonOptions.value = res.data || []
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getStandingList(query)
    tableData.value = res.data.records || []
  } finally {
    loading.value = false
  }
}

const handleRefresh = async () => {
  await refreshStanding(query.seasonId)
  ElMessage.success('积分榜已重算')
  loadData()
}

onMounted(async () => {
  await loadOptions()
  loadData()
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
