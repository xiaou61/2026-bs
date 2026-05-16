<template>
  <DataPage title="定损记录" description="维护定损编号、定损金额、定损时间和定损员，支撑理赔核价、赔付测算和案件进度推进" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getLossAssessmentPage, addLossAssessment, updateLossAssessment, deleteLossAssessment, activateLossAssessment, finishLossAssessment } from '../api'

const api = { page: getLossAssessmentPage, add: addLossAssessment, update: updateLossAssessment, delete: deleteLossAssessment }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canWorkflow = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'assessmentNo', label: '定损编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'assessmentAmount', label: '定损金额' },
  { prop: 'assessmentTime', label: '定损时间', width: 160 },
  { prop: 'assessorName', label: '定损员' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'assessmentNo', label: '定损编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'assessmentAmount', label: '定损金额', type: 'number' },
  { prop: 'assessmentTime', label: '定损时间' },
  { prop: 'assessorName', label: '定损员' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canWorkflow.value ? [{ command: 'activate', label: '启动定损', type: 'warning' }, { command: 'finish', label: '完成定损', type: 'success' }] : [])
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateLossAssessment(row.id)
  }
  if (command === 'finish') {
    await finishLossAssessment(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
