<template>
  <div>
    <DataPage
      ref="pageRef"
      title="评测结果"
      description="查看每个测试用例的实际输出、分数、通过结论和人工复核意见。"
      :api="{ page: getResultPage }"
      :filters="filters"
      :columns="columns"
      :row-actions="rowActions"
      readonly
      @row-action="openReview"
    />
    <el-dialog v-model="dialogVisible" title="复核评测结果" width="620px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="复核状态">
          <el-select v-model="form.reviewStatus" style="width: 100%">
            <el-option label="待复核" :value="0" />
            <el-option label="通过" :value="1" />
            <el-option label="退回" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="复核意见">
          <el-input v-model="form.reviewComment" type="textarea" :rows="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getResultPage, reviewResult } from '../api'

const pageRef = ref()
const dialogVisible = ref(false)
const form = reactive({ id: undefined, reviewStatus: 1, reviewComment: '' })
const reviewOptions = [
  { label: '待复核', value: 0 },
  { label: '通过', value: 1 },
  { label: '退回', value: 2 }
]
const passedMap = {
  1: { label: '通过', type: 'success' },
  0: { label: '未通过', type: 'danger' }
}
const reviewMap = {
  0: { label: '待复核', type: 'info' },
  1: { label: '通过', type: 'success' },
  2: { label: '退回', type: 'danger' }
}
const filters = [
  { prop: 'taskId', label: '任务ID', type: 'input', placeholder: '任务ID' },
  { prop: 'reviewStatus', label: '复核状态', type: 'select', options: reviewOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'taskId', label: '任务ID' },
  { prop: 'caseId', label: '用例ID' },
  { prop: 'inputText', label: '输入', minWidth: 220, long: true },
  { prop: 'expectedOutput', label: '期望输出', minWidth: 220, long: true },
  { prop: 'actualOutput', label: '实际输出', minWidth: 260, long: true },
  { prop: 'score', label: '分数' },
  { prop: 'passed', label: '结论', map: passedMap },
  { prop: 'reviewStatus', label: '复核', map: reviewMap }
]
const rowActions = [{ name: 'review', label: '复核', type: 'primary' }]

const openReview = (name, row) => {
  form.id = row.id
  form.reviewStatus = row.reviewStatus ?? 1
  form.reviewComment = row.reviewComment || ''
  dialogVisible.value = true
}

const submit = async () => {
  await reviewResult(form)
  ElMessage.success('复核成功')
  dialogVisible.value = false
  pageRef.value.loadData()
}
</script>
