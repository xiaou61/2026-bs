<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item v-if="!isMember" label="排期">
          <el-select v-model="filters.performanceId" placeholder="全部排期" clearable style="width: 260px" @change="handleFilterScheduleChange">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="!isMember" label="会员">
          <el-select v-model="filters.memberId" placeholder="全部会员" clearable style="width: 180px">
            <el-option v-for="item in bookingOptions" :key="item.memberId" :label="item.memberName" :value="item.memberId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="canEdit" type="success" @click="openDialog()">新增签到</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="repertoireName" label="剧目名称" min-width="160" />
        <el-table-column prop="memberName" label="会员姓名" min-width="120" />
        <el-table-column prop="checkinDate" label="签到日期" min-width="120" />
        <el-table-column label="签到状态" width="100">
          <template #default="{ row }">
            {{ getOptionLabel(checkinStatusOptions, row.checkinStatus) }}
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
        <el-table-column v-if="canEdit" label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openDialog(row)">编辑</el-button>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑签到' : '新增签到'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="排期" prop="performanceId">
          <el-select v-model="form.performanceId" placeholder="请选择排期" style="width: 100%" @change="handleFormScheduleChange">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="会员" prop="memberId">
          <el-select v-model="form.memberId" placeholder="请选择会员" style="width: 100%">
            <el-option v-for="item in bookingOptions" :key="item.memberId" :label="item.memberName" :value="item.memberId" />
          </el-select>
        </el-form-item>
        <el-form-item label="签到日期">
          <el-date-picker v-model="form.checkinDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="签到状态">
          <el-select v-model="form.checkinStatus" placeholder="请选择状态" style="width: 100%">
            <el-option v-for="item in checkinStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { addAttendance, getAttendanceList, getScheduleList, getSelectionList, getArtistSchedule, updateAttendance } from '../../api'
import { checkinStatusOptions, getOptionLabel, resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isMember = computed(() => userStore.role === 'member')
const canEdit = computed(() => userStore.role === 'artist' || userStore.role === 'admin')

const filters = reactive({
  performanceId: undefined,
  memberId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const performanceOptions = ref([])
const bookingOptions = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  id: null,
  performanceId: null,
  repertoireId: null,
  memberId: null,
  checkinDate: '',
  checkinStatus: 'present',
  remark: ''
})
const rules = {
  performanceId: [{ required: true, message: '请选择排期', trigger: 'change' }],
  memberId: [{ required: true, message: '请选择会员', trigger: 'change' }]
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'admin') {
    const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
    performanceOptions.value = res.data?.list || []
    return
  }
  const res = await getArtistSchedule()
  performanceOptions.value = res.data || []
}

const loadSelectionOptions = async (performanceId) => {
  if (!performanceId) {
    bookingOptions.value = []
    return
  }
  const res = await getSelectionList({ pageNum: 1, pageSize: 200, performanceId, selectStatus: 1 })
  bookingOptions.value = res.data?.list || []
}

const loadData = async () => {
  const res = await getAttendanceList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const handleFilterScheduleChange = async (performanceId) => {
  filters.memberId = undefined
  await loadSelectionOptions(performanceId)
}

const handleFormScheduleChange = async (performanceId) => {
  form.memberId = null
  const current = performanceOptions.value.find((item) => item.id === performanceId)
  form.repertoireId = current?.repertoireId || null
  await loadSelectionOptions(performanceId)
}

const resetForm = () => {
  Object.assign(form, {
    id: null,
    performanceId: null,
    repertoireId: null,
    memberId: null,
    checkinDate: '',
    checkinStatus: 'present',
    remark: ''
  })
}

const openDialog = async (row) => {
  resetForm()
  if (row) {
    Object.assign(form, { ...row })
    await loadSelectionOptions(row.performanceId)
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateAttendance(form)
    ElMessage.success('签到更新成功')
  } else {
    await addAttendance(form)
    ElMessage.success('签到新增成功')
  }
  dialogVisible.value = false
  loadData()
}

const resetFilters = async () => {
  filters.performanceId = undefined
  filters.memberId = undefined
  pageNum.value = 1
  bookingOptions.value = []
  await loadData()
}

onMounted(async () => {
  if (!isMember.value) {
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


