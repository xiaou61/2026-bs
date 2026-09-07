<template>
  <div>
    <el-card>
      <template #header>
        <h3>积分排行榜</h3>
      </template>
      <el-table :data="ranking" border>
        <el-table-column label="排名" width="80">
          <template #default="{ $index }">
            <el-tag v-if="$index < 3" :type="['', 'warning', 'info'][$index]">
              {{ $index + 1 }}
            </el-tag>
            <span v-else>{{ $index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="志愿者" />
        <el-table-column prop="totalPoints" label="总积分" width="150">
          <template #default="{ row }">
            <span style="color: #409eff; font-weight: bold">{{ row.totalPoints }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="volunteerHours" label="志愿时长(小时)" width="180">
          <template #default="{ row }">
            <span style="color: #e6a23c">{{ row.volunteerHours }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPointsRanking } from '@/api/points'

const ranking = ref([])

const loadRanking = async () => {
  const res = await getPointsRanking()
  ranking.value = res.data
}

onMounted(() => {
  loadRanking()
})
</script>

