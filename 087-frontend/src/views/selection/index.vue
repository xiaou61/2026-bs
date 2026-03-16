<template>
  <div class="page">
    <template v-if="isStudent">
      <el-card shadow="never">
        <template #header>
          <div class="panel-head">
            <span>可选课程</span>
            <el-button type="primary" @click="loadStudentData">刷新</el-button>
          </div>
        </template>
        <el-table :data="availableList" stripe>
          <el-table-column prop="courseName" label="课程名称" min-width="160" />
          <el-table-column prop="teacherName" label="授课教师" min-width="110" />
          <el-table-column prop="className" label="班级" min-width="130" />
          <el-table-column label="上课时间" min-width="160">
            <template #default="{ row }">
              {{ row.weekDay }} {{ row.startSection }}-{{ row.endSection }}节
            </template>
          </el-table-column>
          <el-table-column prop="classroom" label="教室" min-width="100" />
          <el-table-column label="剩余名额" width="120">
            <template #default="{ row }">
              {{ (row.maxStudentCount || 0) - (row.selectedCount || 0) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="{ row }">
              <el-button
                type="primary"
                link
                :disabled="selectedIds.includes(row.id) || (row.maxStudentCount || 0) <= (row.selectedCount || 0)"
                @click="handleSelect(row.id)"
              >
                {{ selectedIds.includes(row.id) ? '已选' : '立即选课' }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never">
        <template #header>我的选课记录</template>
        <el-table :data="mySelectionList" stripe>
          <el-table-column prop="courseName" label="课程名称" min-width="180" />
          <el-table-column prop="studentName" label="学生姓名" min-width="120" />
          <el-table-column prop="scheduleInfo" label="上课时间" min-width="160" />
          <el-table-column label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="row.selectStatus === 1 ? 'success' : 'info'">{{ getOptionLabel(selectionStatusOptions, row.selectStatus) }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </template>

    <template v-else>
      <div class="toolbar">
        <el-form :inline="true" :model="filters">
          <el-form-item label="排课">
            <el-select v-model="filters.scheduleId" placeholder="全部排课" clearable style="width: 260px">
              <el-option v-for="item in scheduleOptions" :key="item.id" :label="`${item.courseName} / ${item.weekDay}`" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="filters.selectStatus" placeholder="全部状态" clearable style="width: 140px">
              <el-option v-for="item in selectionStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-form>
        <div class="toolbar-actions">
          <el-button @click="resetFilters">重置</el-button>
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
      </div>

      <el-card shadow="never">
        <el-table :data="list" stripe>
          <el-table-column prop="courseName" label="课程名称" min-width="180" />
          <el-table-column prop="studentName" label="学生姓名" min-width="120" />
          <el-table-column prop="scheduleInfo" label="上课时间" min-width="160" />
          <el-table-column label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="row.selectStatus === 1 ? 'success' : 'info'">{{ getOptionLabel(selectionStatusOptions, row.selectStatus) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="选课时间" min-width="170" />
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
    </template>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getScheduleList, getSelectionList, getStudentSchedule, getTeacherSchedule, selectCourse } from '../../api'
import { getOptionLabel, resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isStudent = computed(() => userStore.role === 'student')
const selectionStatusOptions = [
  { label: '已选', value: 1 },
  { label: '退选', value: 0 }
]

const availableList = ref([])
const mySelectionList = ref([])
const filters = reactive({
  scheduleId: undefined,
  selectStatus: undefined
})
const scheduleOptions = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const selectedIds = computed(() => mySelectionList.value.map((item) => item.scheduleId))

const loadStudentData = async () => {
  const [scheduleRes, selectionRes] = await Promise.all([
    getScheduleList({ pageNum: 1, pageSize: 200, status: 1 }),
    getSelectionList({ pageNum: 1, pageSize: 200 })
  ])
  availableList.value = scheduleRes.data?.list || []
  mySelectionList.value = selectionRes.data?.list || []
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'teacher') {
    const res = await getTeacherSchedule()
    scheduleOptions.value = res.data || []
    return
  }
  const res = await getStudentSchedule()
  scheduleOptions.value = res.data || []
}

const loadAdminOptions = async () => {
  const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
  scheduleOptions.value = res.data?.list || []
}

const loadData = async () => {
  const res = await getSelectionList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const handleSelect = async (scheduleId) => {
  await selectCourse({ scheduleId })
  ElMessage.success('选课成功')
  loadStudentData()
}

const resetFilters = () => {
  filters.scheduleId = undefined
  filters.selectStatus = undefined
  pageNum.value = 1
  loadData()
}

onMounted(async () => {
  if (isStudent.value) {
    await loadStudentData()
    return
  }
  if (userStore.role === 'admin') {
    await loadAdminOptions()
  } else {
    await loadScheduleOptions()
  }
  await loadData()
})
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.toolbar,
.panel-head {
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
