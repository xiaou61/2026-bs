<template>
  <div>
    <h2 style="margin-bottom: 20px">成绩管理</h2>

    <el-form :inline="true" style="margin-bottom: 20px">
      <el-form-item label="选择课程">
        <el-select v-model="selectedCourseId" placeholder="请选择课程" @change="loadGrades" style="width: 300px">
          <el-option 
            v-for="course in courseList" 
            :key="course.id" 
            :label="`${course.courseName}（${course.courseNo}）`" 
            :value="course.id" 
          />
        </el-select>
      </el-form-item>
    </el-form>

    <el-table :data="gradeList" border style="width: 100%" v-loading="loading">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="studentNo" label="学号" width="120" />
      <el-table-column prop="studentName" label="姓名" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column label="成绩" width="150">
        <template #default="{ row }">
          <el-input-number 
            v-model="row.score" 
            :min="0" 
            :max="100" 
            :precision="2"
            size="small"
            style="width: 120px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="gpa" label="绩点" width="80" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'submitted'" type="success">已提交</el-tag>
          <el-tag v-else type="warning">待提交</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="submitTime" label="提交时间" width="180" />
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleSubmit(row)">提交成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getMyCourses } from '@/api/course'
import { getGradesByCourse, submitGrade } from '@/api/grade'
import { getSelectionsByCourse } from '@/api/selection'
import { ElMessage } from 'element-plus'

const route = useRoute()
const loading = ref(false)
const courseList = ref([])
const selectedCourseId = ref(null)
const gradeList = ref([])

const loadCourses = async () => {
  const res = await getMyCourses()
  courseList.value = res.data
  
  if (route.query.courseId) {
    selectedCourseId.value = Number(route.query.courseId)
    await loadGrades()
  }
}

const loadGrades = async () => {
  if (!selectedCourseId.value) return
  
  loading.value = true
  try {
    const [gradesRes, selectionsRes] = await Promise.all([
      getGradesByCourse(selectedCourseId.value),
      getSelectionsByCourse(selectedCourseId.value)
    ])
    
    const gradesMap = new Map(gradesRes.data.map(g => [g.studentId, g]))
    
    gradeList.value = selectionsRes.data.map(selection => {
      const grade = gradesMap.get(selection.studentId)
      if (grade) {
        return grade
      } else {
        return {
          studentId: selection.studentId,
          studentNo: selection.studentNo,
          studentName: selection.studentName,
          courseId: selection.courseId,
          courseName: selection.courseName,
          courseNo: selection.courseNo,
          score: null,
          gpa: null,
          status: 'pending'
        }
      }
    })
  } finally {
    loading.value = false
  }
}

const handleSubmit = async (row) => {
  if (row.score === null || row.score === undefined) {
    ElMessage.warning('请先输入成绩')
    return
  }
  
  try {
    await submitGrade({
      studentId: row.studentId,
      courseId: selectedCourseId.value,
      score: row.score
    })
    ElMessage.success('成绩提交成功')
    await loadGrades()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadCourses()
})
</script>

