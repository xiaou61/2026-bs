<template>
  <DataPage
    title="风险评估"
    description="维护评估编号、咨询主题、评估老师、风险等级和评估时间，支撑危机识别与分级处理"
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
import { getRiskAssessmentPage, addRiskAssessment, updateRiskAssessment, deleteRiskAssessment, submitRiskAssessment, approveRiskAssessment } from '../api'

const api = { page: getRiskAssessmentPage, add: addRiskAssessment, update: updateRiskAssessment, delete: deleteRiskAssessment }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'assessmentNo', label: '评估编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'assessorName', label: '评估老师', width: 140 },
  { prop: 'riskLevel', label: '风险等级', width: 140 },
  { prop: 'assessmentTime', label: '评估时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'assessmentNo', label: '评估编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'assessorName', label: '评估老师' },
  { prop: 'riskLevel', label: '风险等级' },
  { prop: 'assessmentTime', label: '评估时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'submit', label: '提交', type: 'primary' },
  { command: 'approve', label: '审批', type: 'success' }
] : [])
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRiskAssessment(row.id)
  if (command === 'approve') await approveRiskAssessment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








