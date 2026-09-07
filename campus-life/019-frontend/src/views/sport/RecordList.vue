<template>
  <div class="record-list">
    <el-card>
      <template #header>
        <div class="header">
          <span>运动记录</span>
          <el-button type="primary" icon="Plus" @click="$router.push('/sport/create')">
            新建打卡
          </el-button>
        </div>
      </template>
      
      <el-table :data="records" style="width: 100%" v-loading="loading">
        <el-table-column prop="sportType" label="运动类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getSportTypeName(row.sportType) }}</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="sportDate" label="运动日期" width="120" />
        
        <el-table-column prop="duration" label="时长(分钟)" width="120" />
        
        <el-table-column prop="distance" label="距离(km)" width="120">
          <template #default="{ row }">
            {{ row.distance || '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="steps" label="步数" width="100">
          <template #default="{ row }">
            {{ row.steps || '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="calories" label="卡路里" width="100">
          <template #default="{ row }">
            {{ row.calories || '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="avgSpeed" label="配速(分/km)" width="120">
          <template #default="{ row }">
            {{ row.avgSpeed || '-' }}
          </template>
        </el-table-column>
        
        <el-table-column prop="pointsEarned" label="获得积分" width="100">
          <template #default="{ row }">
            <el-tag type="success">+{{ row.pointsEarned }}</el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip />
      </el-table>
      
      <div class="pagination">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="size"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="loadRecords"
          @size-change="loadRecords"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRecords } from '@/api/sport'

const loading = ref(false)
const records = ref([])
const page = ref(1)
const size = ref(10)
const total = ref(0)

const getSportTypeName = (type) => {
  const map = {
    running: '跑步',
    gym: '健身房',
    basketball: '篮球',
    football: '足球',
    badminton: '羽毛球'
  }
  return map[type] || type
}

const loadRecords = async () => {
  loading.value = true
  try {
    const res = await getRecords({ page: page.value, size: size.value })
    records.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadRecords()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>

