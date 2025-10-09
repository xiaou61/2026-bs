<template>
  <div>
    <h2 style="margin-bottom: 20px">课程管理</h2>

    <el-form :inline="true" :model="searchForm" style="margin-bottom: 20px">
      <el-form-item label="搜索">
        <el-input v-model="searchForm.keyword" placeholder="课程名称/课程编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadCourses">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="success" @click="handleAdd">新增课程</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="courseList" border style="width: 100%" v-loading="loading">
      <el-table-column prop="courseNo" label="课程编号" width="120" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="credit" label="学分" width="80" />
      <el-table-column prop="courseType" label="课程类型" width="120" />
      <el-table-column label="容量" width="120">
        <template #default="{ row }">
          {{ row.selectedCount }} / {{ row.totalCapacity }}
        </template>
      </el-table-column>
      <el-table-column prop="classroom" label="教室" width="120" />
      <el-table-column prop="timeSlot" label="上课时间" width="180" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="课程编号" prop="courseNo">
              <el-input v-model="form.courseNo" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程名称" prop="courseName">
              <el-input v-model="form.courseName" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="授课教师" prop="teacherId">
              <el-select v-model="form.teacherId" placeholder="请选择" style="width: 100%" @change="handleTeacherChange">
                <el-option 
                  v-for="teacher in teacherList" 
                  :key="teacher.id" 
                  :label="teacher.name" 
                  :value="teacher.id" 
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学分" prop="credit">
              <el-input-number v-model="form.credit" :min="0" :max="10" :precision="1" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="课程类型" prop="courseType">
              <el-select v-model="form.courseType" placeholder="请选择" style="width: 100%">
                <el-option label="专业必修" value="专业必修" />
                <el-option label="专业选修" value="专业选修" />
                <el-option label="通识必修" value="通识必修" />
                <el-option label="通识选修" value="通识选修" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="容量" prop="totalCapacity">
              <el-input-number v-model="form.totalCapacity" :min="1" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="教室" prop="classroom">
              <el-input v-model="form.classroom" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学期" prop="semester">
              <el-input v-model="form.semester" placeholder="如：2024-2025-1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上课时间" prop="timeSlot">
          <el-input v-model="form.timeSlot" placeholder="如：周一3-4节，周三3-4节" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCourseList, createCourse, updateCourse, deleteCourse } from '@/api/course'
import { getUserList } from '@/api/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const courseList = ref([])
const teacherList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)

const searchForm = reactive({
  keyword: ''
})

const form = reactive({
  id: null,
  courseNo: '',
  courseName: '',
  teacherId: null,
  teacherName: '',
  credit: 3.0,
  totalCapacity: 50,
  selectedCount: 0,
  courseType: '',
  semester: '',
  classroom: '',
  timeSlot: '',
  description: '',
  status: 1
})

const rules = {
  courseNo: [{ required: true, message: '请输入课程编号', trigger: 'blur' }],
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  teacherId: [{ required: true, message: '请选择授课教师', trigger: 'change' }],
  credit: [{ required: true, message: '请输入学分', trigger: 'blur' }],
  totalCapacity: [{ required: true, message: '请输入容量', trigger: 'blur' }],
  courseType: [{ required: true, message: '请选择课程类型', trigger: 'change' }],
  semester: [{ required: true, message: '请输入学期', trigger: 'blur' }]
}

const loadCourses = async () => {
  loading.value = true
  try {
    const res = await getCourseList(searchForm)
    courseList.value = res.data
  } finally {
    loading.value = false
  }
}

const loadTeachers = async () => {
  const res = await getUserList({ role: 'teacher' })
  teacherList.value = res.data
}

const resetSearch = () => {
  searchForm.keyword = ''
  loadCourses()
}

const handleTeacherChange = (teacherId) => {
  const teacher = teacherList.value.find(t => t.id === teacherId)
  if (teacher) {
    form.teacherName = teacher.name
  }
}

const handleAdd = () => {
  dialogTitle.value = '新增课程'
  Object.assign(form, {
    id: null,
    courseNo: '',
    courseName: '',
    teacherId: null,
    teacherName: '',
    credit: 3.0,
    totalCapacity: 50,
    selectedCount: 0,
    courseType: '',
    semester: '',
    classroom: '',
    timeSlot: '',
    description: '',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑课程'
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          await updateCourse(form.id, form)
          ElMessage.success('更新成功')
        } else {
          await createCourse(form)
          ElMessage.success('新增成功')
        }
        dialogVisible.value = false
        loadCourses()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该课程吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteCourse(row.id)
    ElMessage.success('删除成功')
    loadCourses()
  } catch (error) {
    if (error !== 'cancel') {
      console.error(error)
    }
  }
}

onMounted(() => {
  loadCourses()
  loadTeachers()
})
</script>

