<template>
  <div>
    <h2 style="margin-bottom: 20px">我的课程</h2>

    <el-table :data="courseList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="courseNo" label="课程编号" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="courseType" label="课程类型" width="120" />
      <el-table-column label="选课人数" width="120">
        <template #default="{ row }">
          {{ row.selectedCount }} / {{ row.totalCapacity }}
        </template>
      </el-table-column>
      <el-table-column prop="classroom" label="教室" width="120" />
      <el-table-column prop="timeSlot" label="上课时间" width="180" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="viewStudents(row)">查看学生</el-button>
          <el-button type="success" size="small" @click="manageGrades(row)">成绩管理</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyCourses } from '@/api/course'

const router = useRouter()
const loading = ref(false)
const courseList = ref([])

const loadCourses = async () => {
  loading.value = true
  try {
    const res = await getMyCourses()
    courseList.value = res.data
  } finally {
    loading.value = false
  }
}

const viewStudents = (row) => {
  router.push(`/teacher/course/${row.id}/students`)
}

const manageGrades = (row) => {
  router.push({ path: '/teacher/grades', query: { courseId: row.id } })
}

onMounted(() => {
  loadCourses()
})
</script>

