<template>
  <DataPage
    title="风险预警"
    description="维护食品风险类型、预警时间和处置建议"
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
import { addRiskWarning, deleteRiskWarning, finishRiskWarning, getRiskWarningPage, processRiskWarning, updateRiskWarning } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'REVIEWER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => [
  { command: 'process', label: '处置中', type: 'success' },
  { command: 'finish', label: '解除预警', type: 'warning' }
])
const statusOptions = [
  { label: '处理中', value: 'PROCESSING' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'warningNo', label: '预警编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'warningTime', label: '预警时间' },
  { prop: 'riskType', label: '风险类型' },
  { prop: 'disposalSuggestion', label: '处置建议', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'warningNo', label: '预警编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'warningTime', label: '预警时间' },
  { prop: 'riskType', label: '风险类型' },
  { prop: 'disposalSuggestion', label: '处置建议', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRiskWarning(row.id)
  if (command === 'finish') await finishRiskWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
