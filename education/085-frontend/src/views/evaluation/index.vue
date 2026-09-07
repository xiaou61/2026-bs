<template>
  <div class="page-container">
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.taskId" placeholder="任务" clearable style="width: 240px">
          <el-option v-for="item in taskOptions" :key="item.id" :label="item.taskName" :value="item.id" />
        </el-select>
        <el-select v-model="query.courseId" placeholder="课程" clearable style="width: 220px">
          <el-option v-for="item in courseOptions" :key="item.id" :label="item.courseName" :value="item.id" />
        </el-select>
        <el-button type="primary" @click="loadData">查询我的评价</el-button>
        <el-button type="success" @click="openSubmit">提交课程评价</el-button>
      </div>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="taskName" label="任务" min-width="220" />
        <el-table-column prop="courseName" label="课程" min-width="160" />
        <el-table-column prop="totalScore" label="总分" width="100" />
        <el-table-column prop="submitTime" label="提交时间" width="180" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewDetail(row.id)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadData"
      />
    </el-card>

    <el-dialog v-model="submitDialog" title="提交课程评价" width="760px">
      <el-form :model="submitForm" label-width="100px">
        <el-form-item label="评价任务" required>
          <el-select v-model="submitForm.taskId" style="width: 100%" placeholder="请选择任务">
            <el-option v-for="item in activeTasks" :key="item.id" :label="item.taskName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="指标评分" required>
          <div class="indicator-list">
            <div class="indicator-item" v-for="item in submitForm.items" :key="item.indicatorId">
              <div class="indicator-name">{{ item.indicatorName }}（权重 {{ item.weight }}）</div>
              <el-input-number v-model="item.score" :min="0" :max="100" />
            </div>
          </div>
        </el-form-item>
        <el-form-item label="评价意见">
          <el-input v-model="submitForm.commentContent" type="textarea" :rows="4" placeholder="可选，填写改进建议" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailDialog" title="评价详情" width="760px">
      <el-descriptions :column="2" border style="margin-bottom: 12px" v-if="detailData.record">
        <el-descriptions-item label="任务ID">{{ detailData.record.taskId }}</el-descriptions-item>
        <el-descriptions-item label="课程ID">{{ detailData.record.courseId }}</el-descriptions-item>
        <el-descriptions-item label="总分">{{ detailData.record.totalScore }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ detailData.record.submitTime }}</el-descriptions-item>
        <el-descriptions-item label="评价意见" :span="2">{{ detailData.record.commentContent || '-' }}</el-descriptions-item>
      </el-descriptions>

      <el-table :data="detailData.items || []">
        <el-table-column prop="indicatorName" label="指标" min-width="180" />
        <el-table-column prop="weight" label="权重" width="100" />
        <el-table-column prop="score" label="得分" width="100" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  getActiveTaskList,
  getCourseEnabled,
  getEvaluationDetail,
  getIndicatorEnabled,
  getMyEvaluationList,
  submitEvaluation
} from '../../api'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const activeTasks = ref([])
const taskOptions = ref([])
const courseOptions = ref([])
const indicators = ref([])

const submitDialog = ref(false)
const detailDialog = ref(false)
const detailData = ref({})

const query = reactive({ pageNum: 1, pageSize: 10, taskId: null, courseId: null })
const submitForm = reactive({ taskId: null, commentContent: '', items: [] })

const initSubmitItems = () => {
  submitForm.items = (indicators.value || []).map(item => ({
    indicatorId: item.id,
    indicatorName: item.indicatorName,
    weight: item.weight,
    score: 90
  }))
}

const loadBase = async () => {
  const [taskRes, courseRes, indicatorRes] = await Promise.all([
    getActiveTaskList(),
    getCourseEnabled(),
    getIndicatorEnabled()
  ])
  activeTasks.value = taskRes.data || []
  taskOptions.value = taskRes.data || []
  courseOptions.value = courseRes.data || []
  indicators.value = indicatorRes.data || []
  initSubmitItems()
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getMyEvaluationList(query)
    tableData.value = res.data.list || []
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openSubmit = () => {
  if (!activeTasks.value.length) {
    ElMessage.warning('当前没有可参与的评价任务')
    return
  }
  submitForm.taskId = activeTasks.value[0].id
  submitForm.commentContent = ''
  initSubmitItems()
  submitDialog.value = true
}

const handleSubmit = async () => {
  if (!submitForm.taskId) {
    ElMessage.warning('请选择评价任务')
    return
  }
  const payload = {
    taskId: submitForm.taskId,
    commentContent: submitForm.commentContent,
    items: submitForm.items.map(item => ({ indicatorId: item.indicatorId, score: item.score }))
  }
  await submitEvaluation(payload)
  ElMessage.success('提交成功')
  submitDialog.value = false
  loadData()
}

const viewDetail = async (id) => {
  const res = await getEvaluationDetail(id)
  detailData.value = res.data || {}
  detailDialog.value = true
}

onMounted(async () => {
  await loadBase()
  await loadData()
})
</script>

<style scoped>
.page-container { padding: 10px; }
.search-bar { display: flex; gap: 10px; margin-bottom: 15px; flex-wrap: wrap; }
.indicator-list { width: 100%; display: flex; flex-direction: column; gap: 8px; }
.indicator-item { display: flex; align-items: center; justify-content: space-between; border: 1px solid #ebeef5; border-radius: 6px; padding: 10px 12px; }
.indicator-name { color: #303133; }
</style>
