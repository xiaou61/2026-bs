<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="排课">
          <el-select v-model="filters.scheduleId" placeholder="全部排课" clearable style="width: 260px">
            <el-option v-for="item in scheduleOptions" :key="item.id" :label="`${item.courseName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isStudent" type="success" @click="openDialog()">提交评教</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="courseName" label="课程名称" min-width="180" />
        <el-table-column prop="studentName" label="学生姓名" min-width="120" />
        <el-table-column prop="evaluationScore" label="评分" width="90" />
        <el-table-column prop="evaluationContent" label="评教内容" min-width="260" show-overflow-tooltip />
        <el-table-column prop="createTime" label="提交时间" min-width="170" />
      </el-table>
      <div class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @current-change="loadData"
          @size-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" title="提交评教" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="排课" prop="scheduleId">
          <el-select v-model="form.scheduleId" placeholder="请选择排课" style="width: 100%">
            <el-option v-for="item in scheduleOptions" :key="item.id" :label="`${item.courseName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="evaluationScore">
          <el-slider v-model="form.evaluationScore" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="评教内容">
          <el-input v-model="form.evaluationContent" type="textarea" :rows="5" placeholder="请输入评教内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addEvaluation, getEvaluationList, getScheduleList, getStudentSchedule, getTeacherSchedule } from '../../api'
import { resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isStudent = computed(() => userStore.role === 'student')

const filters = reactive({
  scheduleId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const scheduleOptions = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  scheduleId: null,
  evaluationScore: 90,
  evaluationContent: ''
})
const rules = {
  scheduleId: [{ required: true, message: '请选择排课', trigger: 'change' }],
  evaluationScore: [{ required: true, message: '请填写评分', trigger: 'change' }]
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'admin') {
    const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
    scheduleOptions.value = res.data?.list || []
    return
  }
  const res = isStudent.value ? await getStudentSchedule() : await getTeacherSchedule()
  scheduleOptions.value = res.data || []
}

const loadData = async () => {
  const res = await getEvaluationList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const resetForm = () => {
  Object.assign(form, {
    scheduleId: null,
    evaluationScore: 90,
    evaluationContent: ''
  })
}

const openDialog = () => {
  resetForm()
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addEvaluation(form)
  ElMessage.success('评教提交成功')
  dialogVisible.value = false
  loadData()
}

const resetFilters = () => {
  filters.scheduleId = undefined
  pageNum.value = 1
  loadData()
}

onMounted(async () => {
  await loadScheduleOptions()
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.toolbar-actions,
.pagination {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.pagination {
  margin-top: 16px;
}
</style>
