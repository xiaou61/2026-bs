<template>
  <DataPage
    title="咨询记录"
    description="维护记录编号、咨询主题、咨询老师、咨询时间和结论摘要，支撑会谈留痕与复盘跟踪"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCounselRecordPage, addCounselRecord, updateCounselRecord, deleteCounselRecord, submitCounselRecord, approveCounselRecord } from '../api'

const api = { page: getCounselRecordPage, add: addCounselRecord, update: updateCounselRecord, delete: deleteCounselRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'recordNo', label: '记录编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'counselorName', label: '咨询老师', width: 140 },
  { prop: 'counselTime', label: '咨询时间', width: 160 },
  { prop: 'conclusionSummary', label: '结论摘要', width: 240 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'recordNo', label: '记录编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'counselorName', label: '咨询老师' },
  { prop: 'counselTime', label: '咨询时间' },
  { prop: 'conclusionSummary', label: '结论摘要', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已复核', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) {
    actions.push({ command: 'submit', label: '提交', type: 'primary' })
  }
  if (canApprove.value) {
    actions.push({ command: 'approve', label: '复核', type: 'success' })
  }
  return actions
})
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCounselRecord(row.id)
  if (command === 'approve') await approveCounselRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








