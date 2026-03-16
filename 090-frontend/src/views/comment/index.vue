<template>
  <div class="page">
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item label="排期">
          <el-select v-model="filters.performanceId" placeholder="全部排期" clearable style="width: 260px">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div class="toolbar-actions">
        <el-button @click="resetFilters">重置</el-button>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button v-if="isMember" type="success" @click="openDialog()">提交赏析互动</el-button>
      </div>
    </div>

    <el-card shadow="never">
      <el-table :data="list" stripe>
        <el-table-column prop="repertoireName" label="剧目名称" min-width="180" />
        <el-table-column prop="memberName" label="会员姓名" min-width="120" />
        <el-table-column prop="commentScore" label="评分" width="90" />
        <el-table-column prop="commentContent" label="赏析互动内容" min-width="260" show-overflow-tooltip />
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

    <el-dialog v-model="dialogVisible" title="提交赏析互动" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="排期" prop="performanceId">
          <el-select v-model="form.performanceId" placeholder="请选择排期" style="width: 100%">
            <el-option v-for="item in performanceOptions" :key="item.id" :label="`${item.repertoireName} / ${item.weekDay}`" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="评分" prop="commentScore">
          <el-slider v-model="form.commentScore" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="赏析互动内容">
          <el-input v-model="form.commentContent" type="textarea" :rows="5" placeholder="请输入赏析互动内容" />
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
import { addEvaluation, getEvaluationList, getScheduleList, getMemberSchedule, getArtistSchedule } from '../../api'
import { resolvePage } from '../../utils'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()
const isMember = computed(() => userStore.role === 'member')

const filters = reactive({
  performanceId: undefined
})
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const list = ref([])
const performanceOptions = ref([])
const dialogVisible = ref(false)
const formRef = ref()
const form = reactive({
  performanceId: null,
  commentScore: 90,
  commentContent: ''
})
const rules = {
  performanceId: [{ required: true, message: '请选择排期', trigger: 'change' }],
  commentScore: [{ required: true, message: '请填写评分', trigger: 'change' }]
}

const loadScheduleOptions = async () => {
  if (userStore.role === 'admin') {
    const res = await getScheduleList({ pageNum: 1, pageSize: 200, status: 1 })
    performanceOptions.value = res.data?.list || []
    return
  }
  const res = isMember.value ? await getMemberSchedule() : await getArtistSchedule()
  performanceOptions.value = res.data || []
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
    performanceId: null,
    commentScore: 90,
    commentContent: ''
  })
}

const openDialog = () => {
  resetForm()
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  await addEvaluation(form)
  ElMessage.success('赏析互动提交成功')
  dialogVisible.value = false
  loadData()
}

const resetFilters = () => {
  filters.performanceId = undefined
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


