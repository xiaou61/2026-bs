<template>
  <DataPage
    title="危机干预"
    description="维护干预编号、咨询主题、干预类型、干预对象和干预结果，支撑突发情绪风险快速处置"
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
import { getCrisisInterventionPage, addCrisisIntervention, updateCrisisIntervention, deleteCrisisIntervention, submitCrisisIntervention, approveCrisisIntervention } from '../api'

const api = { page: getCrisisInterventionPage, add: addCrisisIntervention, update: updateCrisisIntervention, delete: deleteCrisisIntervention }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'interventionNo', label: '干预编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'interventionType', label: '干预类型', width: 140 },
  { prop: 'targetPerson', label: '干预对象', width: 140 },
  { prop: 'interventionResult', label: '干预结果', width: 240 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'interventionNo', label: '干预编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'interventionType', label: '干预类型' },
  { prop: 'targetPerson', label: '干预对象' },
  { prop: 'interventionResult', label: '干预结果', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'submit', label: '提交', type: 'primary' },
  { command: 'approve', label: '审批', type: 'success' }
] : [])
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCrisisIntervention(row.id)
  if (command === 'approve') await approveCrisisIntervention(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








