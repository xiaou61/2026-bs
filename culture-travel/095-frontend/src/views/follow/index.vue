<template>
  <div class="page-container">
    <template v-if="isFan">
      <el-row :gutter="16">
        <el-col :span="8">
          <el-card>
            <el-statistic title="已关注球队" :value="followStats.total" />
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <div class="summary-card">
              <div class="summary-title">当前筛选赛季</div>
              <div class="summary-value">{{ followStats.seasonLabel }}</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card>
            <el-statistic title="可浏览球队" :value="teamList.length" />
          </el-card>
        </el-col>
      </el-row>

      <el-card class="mt16">
        <div class="search-bar">
          <el-select v-model="query.seasonId" placeholder="筛选赛季" clearable @change="loadTeams">
            <el-option v-for="item in seasonOptions" :key="item.id" :label="item.seasonName" :value="item.id" />
          </el-select>
          <el-button type="primary" @click="loadTeams">刷新球队</el-button>
        </div>

        <div class="team-grid">
          <el-card v-for="item in teamList" :key="item.id" class="team-card" shadow="hover">
            <div class="team-head">
              <div>
                <div class="team-name">{{ item.teamName }}</div>
                <div class="team-sub">{{ item.clubName }} · {{ item.seasonName }}</div>
              </div>
              <el-button :type="followedTeamIds.has(item.id) ? 'danger' : 'primary'" plain @click="handleToggle(item)">
                {{ followedTeamIds.has(item.id) ? '取消关注' : '关注球队' }}
              </el-button>
            </div>
            <div class="team-body">
              <div class="meta-item">
                <span>主场球场</span>
                <strong>{{ item.venueName || '待分配' }}</strong>
              </div>
              <div class="meta-item">
                <span>赛季目标</span>
                <strong>{{ item.goalTarget || '稳步提升' }}</strong>
              </div>
              <div class="color-row">
                <div class="color-chip">
                  <span>主场色</span>
                  <em :style="{ background: item.homeColor || '#1d4f87' }"></em>
                  <strong>{{ item.homeColor || '#1d4f87' }}</strong>
                </div>
                <div class="color-chip">
                  <span>客场色</span>
                  <em :style="{ background: item.awayColor || '#d9e2ef' }"></em>
                  <strong>{{ item.awayColor || '#d9e2ef' }}</strong>
                </div>
              </div>
            </div>
          </el-card>
        </div>
        <el-empty v-if="!teamList.length" description="当前筛选条件下暂无球队" />
      </el-card>

      <el-card class="mt16">
        <template #header>
          <span>我的关注列表</span>
        </template>
        <el-table :data="followList" v-loading="loading">
          <el-table-column prop="teamName" label="球队" min-width="180" />
          <el-table-column prop="createTime" label="关注时间" min-width="180" />
          <el-table-column label="操作" width="120">
            <template #default="{ row }">
              <el-button link type="danger" @click="handleToggle({ id: row.teamId, teamName: row.teamName })">取消关注</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </template>

    <el-card v-else>
      <template #header>
        <span>球迷关注情况</span>
      </template>
      <el-table :data="followList" v-loading="loading">
        <el-table-column prop="username" label="球迷昵称" min-width="160" />
        <el-table-column prop="teamName" label="关注球队" min-width="180" />
        <el-table-column prop="createTime" label="关注时间" min-width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getFollowList, getSeasonOptions, getTeamPublicList, toggleFollow } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const role = computed(() => (userStore.user?.role || '').toUpperCase())
const isFan = computed(() => role.value === 'FAN')

const loading = ref(false)
const seasonOptions = ref([])
const teamList = ref([])
const followList = ref([])
const followedTeamIds = ref(new Set())

const query = reactive({
  pageNum: 1,
  pageSize: 20,
  seasonId: null
})

const followStats = computed(() => ({
  total: followList.value.length,
  seasonLabel: seasonOptions.value.find(item => item.id === query.seasonId)?.seasonName || '全部赛季'
}))

const loadSeasons = async () => {
  const res = await getSeasonOptions({})
  seasonOptions.value = res.data || []
}

const loadFollows = async () => {
  loading.value = true
  try {
    const res = await getFollowList({ pageNum: 1, pageSize: 100 })
    followList.value = res.data.records || []
    followedTeamIds.value = new Set(followList.value.map(item => item.teamId))
  } finally {
    loading.value = false
  }
}

const loadTeams = async () => {
  const res = await getTeamPublicList({ seasonId: query.seasonId })
  teamList.value = res.data || []
}

const handleToggle = async item => {
  await toggleFollow({ teamId: item.id })
  await loadFollows()
  ElMessage.success(followedTeamIds.value.has(item.id) ? `已关注 ${item.teamName}` : `已取消关注 ${item.teamName}`)
}

onMounted(async () => {
  await loadSeasons()
  await loadFollows()
  if (isFan.value) {
    await loadTeams()
  }
})
</script>

<style scoped>
.page-container {
  padding: 8px;
}

.mt16 {
  margin-top: 16px;
}

.summary-card {
  min-height: 63px;
}

.summary-title {
  color: #909399;
  font-size: 14px;
}

.summary-value {
  margin-top: 12px;
  font-size: 28px;
  font-weight: 700;
  color: #17324d;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.team-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.team-card {
  border-radius: 16px;
}

.team-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.team-name {
  font-size: 20px;
  font-weight: 700;
  color: #17324d;
}

.team-sub {
  margin-top: 6px;
  color: #667085;
}

.team-body {
  margin-top: 18px;
}

.meta-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #eef2f6;
  color: #475467;
}

.meta-item strong {
  color: #17324d;
}

.color-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
  margin-top: 16px;
}

.color-chip {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 12px;
  background: #f7f9fc;
  border-radius: 12px;
}

.color-chip span {
  color: #667085;
  font-size: 13px;
}

.color-chip em {
  width: 100%;
  height: 16px;
  border-radius: 999px;
  display: block;
}

.color-chip strong {
  color: #17324d;
  font-style: normal;
}
</style>
