<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item v-if="!isMember" label="排期">
          <el-select v-model="filters.performanceId" placeholder="全部排期" clearable style="width: 260px" @change="handleFilterScheduleChange">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="canEdit" type="success" @click="openDialog()">录入研学评分</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="repertoireName" label="剧目名称" min-width="180" />
        <el-table-column prop="memberName" label="会员姓名" min-width="120" />
        <el-table-column prop="usualScore" label="平时研学评分" width="100" />
        <el-table-column prop="examScore" label="考试研学评分" width="100" />
        <el-table-column prop="totalScore" label="总评研学评分" width="100" />
        <el-table-column prop="gradeLevel" label="等级" width="90" />
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

    <el-dialog v-model="dialogVisible" :title="form.bookingId ? '编辑研学评分' : '录入研学评分'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="排期" prop="performanceId">
          <el-select v-model="form.performanceId" placeholder="请选择排期" style="width: 100%" @change="handleFormScheduleChange">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约会员" prop="bookingId">
          <el-select v-model="form.bookingId" placeholder="请选择会员" style="width: 100%">
            <el-option v-for="item in bookingOptions" :key="item.id" :label="`${item.memberName} / ${item.repertoireName}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="平时研学评分">
          <el-input-number v-model="form.usualScore" :min="0" :max="100" style="width: 100%" />
        </el-form-item>
        <el-form-item label="考试研学评分">
          <el-input-number v-model="form.examScore" :min="0" :max="100" style="width: 100%" />
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
import { getScheduleList, getScoreList, getSelectionList, getArtistSchedule, saveScore } from '../../api'
import { resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isMember = computed(() => userStore.role === 'member')
const canEdit = computed(() => userStore.role === 'artist' || userStore.role === 'admin')

const filters = reactive({
  performanceId: undefined
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
  bookingId: null,
  performanceId: null,
  usualScore: 60,
  examScore: 60,
  remark: ''
})
const rules = {
  performanceId: [{ required: true, message: '请选择排期', trigger: 'change' }],
  bookingId: [{ required: true, message: '请选择预约会员', trigger: 'change' }]
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
  const res = await getScoreList({
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    ...filters
  })
  const page = resolvePage(res.data)
  list.value = page.list
  total.value = page.total
}

const handleFilterScheduleChange = async (performanceId) => {
  await loadSelectionOptions(performanceId)
}

const handleFormScheduleChange = async (performanceId) => {
  form.bookingId = null
  await loadSelectionOptions(performanceId)
}

const resetForm = () => {
  Object.assign(form, {
    bookingId: null,
    performanceId: null,
    usualScore: 60,
    examScore: 60,
    remark: ''
  })
}

const openDialog = async (row) => {
  resetForm()
  if (row) {
    Object.assign(form, {
      bookingId: row.bookingId,
      performanceId: row.performanceId,
      usualScore: row.usualScore,
      examScore: row.examScore,
      remark: row.remark
    })
    await loadSelectionOptions(row.performanceId)
  }
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await saveScore(form)
  ElMessage.success('研学评分保存成功')
  dialogVisible.value = false
  loadData()
}

const resetFilters = async () => {
  filters.performanceId = undefined
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


