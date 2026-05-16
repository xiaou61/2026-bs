<template>
  <DataPage
    title="结果公示"
    description="维护抽检结果公示标题、发布渠道和发布时间"
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
import { activatePublicReport, addPublicReport, deletePublicReport, finishPublicReport, getPublicReportPage, updatePublicReport } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getPublicReportPage, add: addPublicReport, update: updatePublicReport, delete: deletePublicReport }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'REVIEWER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'activate', label: '发布公示', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '归档', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '处理中', value: 'PROCESSING' },
  { label: '已公示', value: 'PUBLISHED' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'reportNo', label: '公示编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'reportTitle', label: '公示标题', width: 180 },
  { prop: 'publishChannel', label: '公示渠道' },
  { prop: 'publishTime', label: '公示时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reportNo', label: '公示编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'reportTitle', label: '公示标题', type: 'textarea' },
  { prop: 'publishChannel', label: '公示渠道' },
  { prop: 'publishTime', label: '公示时间' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePublicReport(row.id)
  if (command === 'finish') await finishPublicReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
