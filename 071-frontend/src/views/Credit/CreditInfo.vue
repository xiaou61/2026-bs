<template>
  <div>
    <el-card>
      <template #header>信用信息</template>
      <el-row :gutter="20">
        <el-col :span="8">
          <div style="text-align:center">
            <div style="font-size:48px;font-weight:bold" :style="{ color: scoreColor }">{{ score }}</div>
            <div style="color:#909399;margin-top:10px">当前信用分</div>
            <el-tag :type="levelType" size="large" style="margin-top:10px">{{ levelText }}</el-tag>
          </div>
        </el-col>
        <el-col :span="16">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="信用等级">{{ levelText }}</el-descriptions-item>
            <el-descriptions-item label="骑行权限"><el-tag :type="score >= 60 ? 'success' : 'danger'">{{ score >= 60 ? '正常' : '已限制' }}</el-tag></el-descriptions-item>
            <el-descriptions-item label="评分规则">正常还车+2，超时-5，违规停放-10，损坏-20</el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin-top:15px">
      <template #header>信用变更记录</template>
      <el-table :data="tableData" stripe>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column label="变更" width="100"><template #default="{ row }"><span :style="{ color: row.scoreChange > 0 ? '#67C23A' : '#F56C6C', fontWeight: 'bold' }">{{ row.scoreChange > 0 ? '+' : '' }}{{ row.scoreChange }}</span></template></el-table-column>
        <el-table-column prop="scoreAfter" label="变更后" width="80" />
        <el-table-column prop="description" label="说明" />
        <el-table-column prop="createTime" label="时间" width="170" />
      </el-table>
      <el-pagination style="margin-top:15px;justify-content:end" v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" @current-change="loadRecords" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { getCreditScore, getCreditRecords } from '../../api'

const score = ref(100)
const tableData = ref([])
const total = ref(0)
const query = reactive({ pageNum: 1, pageSize: 10 })

const scoreColor = computed(() => score.value >= 90 ? '#67C23A' : score.value >= 70 ? '#409EFF' : score.value >= 50 ? '#E6A23C' : '#F56C6C')
const levelType = computed(() => score.value >= 90 ? 'success' : score.value >= 70 ? '' : score.value >= 50 ? 'warning' : 'danger')
const levelText = computed(() => score.value >= 90 ? '优秀' : score.value >= 70 ? '良好' : score.value >= 50 ? '一般' : '较差')

const loadRecords = async () => { const res = await getCreditRecords(query); tableData.value = res.data.list; total.value = res.data.total }

onMounted(async () => {
  const res = await getCreditScore()
  score.value = res.data
  loadRecords()
})
</script>
