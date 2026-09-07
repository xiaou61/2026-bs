<template>
  <DataPage
    title="检测结果"
    description="记录检测结论、检测时间和实验室检测员"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addTestResult, deleteTestResult, finishTestResult, getTestResultPage, processTestResult, updateTestResult } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getTestResultPage, add: addTestResult, update: updateTestResult, delete: deleteTestResult }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'REVIEWER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'process', label: '复核中', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '办结', type: 'warning' })
  return actions
})
const resultOptions = [
  { label: '合格', value: 'QUALIFIED' },
  { label: '不合格', value: 'UNQUALIFIED' }
]
const statusOptions = [
  { label: '处理中', value: 'PROCESSING' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'resultNo', label: '结果编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'testConclusion', label: '检测结论' },
  { prop: 'testTime', label: '检测时间' },
  { prop: 'testerName', label: '检测员' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'resultNo', label: '结果编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'testConclusion', label: '检测结论', type: 'select', options: resultOptions },
  { prop: 'testTime', label: '检测时间' },
  { prop: 'testerName', label: '检测员' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { testConclusion: 'QUALIFIED', status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processTestResult(row.id)
  if (command === 'finish') await finishTestResult(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
