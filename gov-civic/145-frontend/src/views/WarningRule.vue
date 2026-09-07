<template>
  <DataPage
    title="预警规则"
    description="维护规则编号、预警指标、阈值配置和生效时间，支撑噪声超标自动预警策略管理"
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
import { useUserStore } from '../store/user'
import { getWarningRulePage, addWarningRule, updateWarningRule, deleteWarningRule, activateWarningRule, finishWarningRule } from '../api'

const api = { page: getWarningRulePage, add: addWarningRule, update: updateWarningRule, delete: deleteWarningRule }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'ruleNo', label: '规则编号' },
  { prop: 'complaintTitle', label: '关联投诉', width: 180 },
  { prop: 'warningMetric', label: '预警指标', width: 160 },
  { prop: 'thresholdConfig', label: '阈值配置', width: 220 },
  { prop: 'effectiveTime', label: '生效时间', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'ruleNo', label: '规则编号' },
  { prop: 'complaintTitle', label: '关联投诉' },
  { prop: 'warningMetric', label: '预警指标' },
  { prop: 'thresholdConfig', label: '阈值配置' },
  { prop: 'effectiveTime', label: '生效时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateWarningRule(row.id)
  if (command === 'finish') await finishWarningRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
