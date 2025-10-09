<template>
  <div>
    <el-page-header @back="goBack" style="margin-bottom: 20px">
      <template #content>
        <span style="font-size: 18px; font-weight: bold">选课学生列表</span>
      </template>
    </el-page-header>

    <el-card style="margin-bottom: 20px" v-if="courseInfo">
      <div style="font-size: 16px; font-weight: bold; margin-bottom: 10px">{{ courseInfo.courseName }}</div>
      <div style="color: #909399">课程编号：{{ courseInfo.courseNo }} | 学分：{{ courseInfo.credit }} | 选课人数：{{ studentList.length }}</div>
    </el-card>

    <el-table :data="studentList" border style="width: 100%" v-loading="loading">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="studentNo" label="学号" width="150" />
      <el-table-column prop="studentName" label="姓名" width="120" />
      <el-table-column prop="selectionTime" label="选课时间" width="180" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'selected'" type="success">已选课</el-tag>
          <el-tag v-else type="info">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCourseById } from '@/api/course'
import { getSelectionsByCourse } from '@/api/selection'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const courseInfo = ref(null)
const studentList = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const courseId = route.params.id
    const [courseRes, studentsRes] = await Promise.all([
      getCourseById(courseId),
      getSelectionsByCourse(courseId)
    ])
    courseInfo.value = courseRes.data
    studentList.value = studentsRes.data
  } finally {
    loading.value = false
  }
}

const goBack = () => {
  router.back()
}

onMounted(() => {
  loadData()
})
</script>

