<template>
  <div>
    <h2 style="margin-bottom: 20px">我的成绩</h2>

    <el-card style="margin-bottom: 20px">
      <div style="display: flex; gap: 40px">
        <div>
          <div style="color: #909399">总学分</div>
          <div style="font-size: 24px; font-weight: bold; color: #409eff">{{ totalCredit }}</div>
        </div>
        <div>
          <div style="color: #909399">平均分</div>
          <div style="font-size: 24px; font-weight: bold; color: #67c23a">{{ averageScore }}</div>
        </div>
        <div>
          <div style="color: #909399">平均绩点</div>
          <div style="font-size: 24px; font-weight: bold; color: #e6a23c">{{ averageGpa }}</div>
        </div>
      </div>
    </el-card>

    <el-table :data="gradeList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="courseNo" label="课程编号" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="score" label="成绩" width="100">
        <template #default="{ row }">
          <span v-if="row.score !== null" :style="{ color: row.score >= 60 ? '#67c23a' : '#f56c6c', fontWeight: 'bold' }">
            {{ row.score }}
          </span>
          <span v-else style="color: #909399">未录入</span>
        </template>
      </el-table-column>
      <el-table-column prop="gpa" label="绩点" width="80" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'submitted'" type="success">已提交</el-tag>
          <el-tag v-else type="info">待录入</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="submitTime" label="提交时间" width="180" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMyGrades } from '@/api/grade'

const loading = ref(false)
const gradeList = ref([])

const totalCredit = computed(() => {
  return gradeList.value
    .filter(g => g.score !== null && g.score >= 60)
    .reduce((sum, g) => sum + Number(g.credit), 0)
    .toFixed(1)
})

const averageScore = computed(() => {
  const validGrades = gradeList.value.filter(g => g.score !== null)
  if (validGrades.length === 0) return 0
  const sum = validGrades.reduce((sum, g) => sum + Number(g.score), 0)
  return (sum / validGrades.length).toFixed(2)
})

const averageGpa = computed(() => {
  const validGrades = gradeList.value.filter(g => g.gpa !== null)
  if (validGrades.length === 0) return 0
  const sum = validGrades.reduce((sum, g) => sum + Number(g.gpa), 0)
  return (sum / validGrades.length).toFixed(2)
})

const loadGrades = async () => {
  loading.value = true
  try {
    const res = await getMyGrades()
    gradeList.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadGrades()
})
</script>

