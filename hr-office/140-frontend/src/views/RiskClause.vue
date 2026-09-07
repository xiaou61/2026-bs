<template>
  <DataPage title="风险条款" description="维护合同重点风险条款、风险级别和复核人信息，支撑合同法务审查" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRiskClausePage, addRiskClause, updateRiskClause, deleteRiskClause, processRiskClause, finishRiskClause } from '../api'

const api = { page: getRiskClausePage, add: addRiskClause, update: updateRiskClause, delete: deleteRiskClause }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'clauseNo', label: '条款编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'clauseType', label: '条款类型', width: 180 },
  { prop: 'riskLevel', label: '风险级别' },
  { prop: 'reviewerName', label: '复核人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'clauseNo', label: '条款编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'clauseType', label: '条款类型', type: 'select', options: [{ label: '违约责任条款', value: '违约责任条款' }, { label: '保密条款', value: '保密条款' }, { label: '数据安全条款', value: '数据安全条款' }, { label: '付款结算条款', value: '付款结算条款' }] },
  { prop: 'riskLevel', label: '风险级别', type: 'select', options: [{ label: '低风险', value: 'LOW' }, { label: '中风险', value: 'MEDIUM' }, { label: '高风险', value: 'HIGH' }] },
  { prop: 'reviewerName', label: '复核人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '处理', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { riskLevel: 'MEDIUM', status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') {
    await processRiskClause(row.id)
  }
  if (command === 'finish') {
    await finishRiskClause(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
