<template>
  <div>
    <h2 style="margin-bottom: 20px">我的课表</h2>

    <el-table :data="myCourses" border style="width: 100%" v-loading="loading">
      <el-table-column prop="courseNo" label="课程编号" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="selectionTime" label="选课时间" width="180" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.status === 'selected'" type="success">已选课</el-tag>
          <el-tag v-else type="info">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="{ row }">
          <el-button 
            type="danger" 
            size="small" 
            @click="handleDrop(row)"
          >
            退课
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMySelections } from '@/api/selection'
import { dropCourse } from '@/api/selection'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const myCourses = ref([])

const loadMyCourses = async () => {
  loading.value = true
  try {
    const res = await getMySelections()
    myCourses.value = res.data
  } finally {
    loading.value = false
  }
}

const handleDrop = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确认退选课程《${row.courseName}》吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    await dropCourse(row.courseId)
    ElMessage.success('退课成功')
    loadMyCourses()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadMyCourses()
})
</script>

