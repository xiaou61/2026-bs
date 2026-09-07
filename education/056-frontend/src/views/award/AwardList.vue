<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="selectedCompetition" placeholder="选择竞赛" style="width: 300px" @change="loadAwards">
          <el-option v-for="c in competitions" :key="c.id" :label="c.title" :value="c.id" />
        </el-select>
        <el-button type="primary" @click="showGenerateDialog" :disabled="!selectedCompetition">生成获奖名单</el-button>
      </div>
      <el-table :data="awardList" v-loading="loading">
        <el-table-column prop="rank" label="排名" width="80" />
        <el-table-column prop="workTitle" label="作品名称" min-width="200" />
        <el-table-column prop="userName" label="作者" width="120" />
        <el-table-column prop="score" label="得分" width="100" />
        <el-table-column prop="awardLevel" label="奖项" width="120">
          <template #default="{ row }">
            <el-tag :type="awardType[row.awardLevel]">{{ row.awardLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="获奖时间" width="160" />
      </el-table>
    </el-card>
    <el-dialog v-model="generateDialogVisible" title="生成获奖名单" width="500px">
      <p>将根据作品最终得分自动排名并生成获奖名单</p>
      <div class="award-config" v-for="(item, index) in awardConfig" :key="index">
        <span>第 {{ item.rank }} 名</span>
        <el-input v-model="item.level" placeholder="奖项名称" style="width: 150px" />
        <el-button link type="danger" @click="awardConfig.splice(index, 1)">删除</el-button>
      </div>
      <el-button type="primary" link @click="addAwardConfig">+ 添加奖项</el-button>
      <template #footer>
        <el-button @click="generateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleGenerate">生成</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getCompetitionList, getAwardList, generateAwards } from '../../api'

const loading = ref(false)
const competitions = ref([])
const selectedCompetition = ref(null)
const awardList = ref([])
const generateDialogVisible = ref(false)
const awardConfig = reactive([
  { rank: 1, level: '一等奖' },
  { rank: 2, level: '二等奖' },
  { rank: 3, level: '三等奖' }
])
const awardType = { '一等奖': 'danger', '二等奖': 'warning', '三等奖': 'success' }

const loadAwards = async () => {
  if (!selectedCompetition.value) return
  loading.value = true
  try {
    const res = await getAwardList(selectedCompetition.value)
    awardList.value = res.data
  } finally {
    loading.value = false
  }
}

const showGenerateDialog = () => {
  generateDialogVisible.value = true
}

const addAwardConfig = () => {
  const maxRank = awardConfig.length > 0 ? Math.max(...awardConfig.map(a => a.rank)) : 0
  awardConfig.push({ rank: maxRank + 1, level: '' })
}

const handleGenerate = async () => {
  const awards = awardConfig.filter(a => a.level)
  await generateAwards(selectedCompetition.value, { awards })
  ElMessage.success('生成成功')
  generateDialogVisible.value = false
  loadAwards()
}

onMounted(async () => {
  const res = await getCompetitionList({ pageNum: 1, pageSize: 100, status: 2 })
  competitions.value = res.data.records
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; }
.award-config { display: flex; align-items: center; gap: 10px; margin-bottom: 10px; }
</style>
