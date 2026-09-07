<template>
  <div>
    <h2 style="margin-bottom: 20px">浏览课程</h2>
    
    <el-form :inline="true" :model="searchForm" style="margin-bottom: 20px">
      <el-form-item label="搜索">
        <el-input v-model="searchForm.keyword" placeholder="课程名称/课程编号/教师" clearable />
      </el-form-item>
      <el-form-item label="课程类型">
        <el-select v-model="searchForm.courseType" placeholder="请选择" clearable>
          <el-option label="专业必修" value="专业必修" />
          <el-option label="专业选修" value="专业选修" />
          <el-option label="通识必修" value="通识必修" />
          <el-option label="通识选修" value="通识选修" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadCourses">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="courseList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="courseNo" label="课程编号" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="courseType" label="课程类型" width="120" />
      <el-table-column label="容量" width="120">
        <template #default="{ row }">
          <span :style="{ color: row.selectedCount >= row.totalCapacity ? 'red' : '' }">
            {{ row.selectedCount }} / {{ row.totalCapacity }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="classroom" label="教室" width="120" />
      <el-table-column prop="timeSlot" label="上课时间" width="180" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="{ row }">
          <el-button 
            type="primary" 
            size="small" 
            @click="handleSelect(row)"
            :disabled="row.selectedCount >= row.totalCapacity"
          >
            选课
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCourseList } from '@/api/course'
import { selectCourse } from '@/api/selection'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const courseList = ref([])

const searchForm = reactive({
  keyword: '',
  courseType: ''
})

const loadCourses = async () => {
  loading.value = true
  try {
    const res = await getCourseList(searchForm)
    courseList.value = res.data
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.keyword = ''
  searchForm.courseType = ''
  loadCourses()
}

const handleSelect = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确认选择课程《${row.courseName}》吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    await selectCourse(row.id)
    ElMessage.success('选课成功')
    loadCourses()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadCourses()
})
</script>

