<template>
  <el-card>
    <div class="bar">
      <el-select v-model="query.courseId" placeholder="课程" style="width: 220px" clearable>
        <el-option v-for="item in courses" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-select v-if="isStaff" v-model="query.studentId" placeholder="学生" style="width: 180px" clearable>
        <el-option v-for="item in students" :key="item.id" :label="item.nickname || item.username" :value="item.id" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" style="width: 130px" clearable>
        <el-option label="已选" :value="1" />
        <el-option label="退选" :value="2" />
      </el-select>
      <el-button type="primary" @click="loadData">查询</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading">
      <el-table-column prop="courseName" label="课程" min-width="180" />
      <el-table-column prop="teacherName" label="教师" width="120" />
      <el-table-column prop="studentName" label="学生" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '已选' : '退选' }}</el-tag></template>
      </el-table-column>
      <el-table-column prop="score" label="成绩" width="90" />
      <el-table-column prop="remark" label="备注" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button v-if="isStaff" link type="primary" @click="openHandle(row)">处理</el-button>
          <el-popconfirm v-if="!isStaff || isAdmin" title="确认删除？" @confirm="handleDelete(row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-model:current-page="query.pageNum" v-model:page-size="query.pageSize" :total="total" layout="total, prev, pager, next" style="margin-top: 12px" @current-change="loadData" />
  </el-card>

  <el-dialog v-model="handleVisible" title="处理选课" width="520px">
    <el-form :model="handleForm" label-width="80px">
      <el-form-item label="状态">
        <el-select v-model="handleForm.status" style="width: 100%">
          <el-option label="已选" :value="1" />
          <el-option label="退选" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="成绩">
        <el-input-number v-model="handleForm.score" :min="0" :max="100" :step="1" style="width: 100%" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="handleForm.remark" type="textarea" :rows="3" maxlength="255" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleVisible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { deleteEnroll, getCourseList, getEnrollPage, getMyEnrollPage, getStudentList, updateEnrollStatus } from '../../api'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isAdmin = computed(() => userStore.user?.role === 'ADMIN')
const isTeacher = computed(() => userStore.user?.role === 'TEACHER')
const isStaff = computed(() => isAdmin.value || isTeacher.value)

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const courses = ref([])
const students = ref([])
const handleVisible = ref(false)

const query = reactive({ pageNum: 1, pageSize: 10, courseId: null, studentId: null, status: null })
const handleForm = reactive({ id: null, status: 1, score: null, remark: '' })

const loadBase = async () => {
  const tasks = [getCourseList({ status: null })]
  if (isStaff.value) {
    tasks.push(getStudentList())
  }
  const res = await Promise.all(tasks)
  courses.value = res[0].data || []
  if (isStaff.value) {
    students.value = res[1].data || []
  }
}

const loadData = async () => {
  loading.value = true
  try {
    const res = isStaff.value ? await getEnrollPage(query) : await getMyEnrollPage(query)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openHandle = (row) => {
  Object.assign(handleForm, { id: row.id, status: row.status, score: row.score, remark: row.remark || '' })
  handleVisible.value = true
}

const submitHandle = async () => {
  await updateEnrollStatus(handleForm)
  ElMessage.success('处理成功')
  handleVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await deleteEnroll(id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.bar {
  display: flex;
  gap: 10px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
