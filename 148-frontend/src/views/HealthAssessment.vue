<template>
  <DataPage
    title="健康评估"
    description="维护评估编号、老人姓名、评估项目、评估时间和评估人，支撑长者健康跟踪随访"
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
import { getHealthAssessmentPage, addHealthAssessment, updateHealthAssessment, deleteHealthAssessment, activateHealthAssessment, finishHealthAssessment } from '../api'

const api = { page: getHealthAssessmentPage, add: addHealthAssessment, update: updateHealthAssessment, delete: deleteHealthAssessment }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT', 'CAREGIVER'].includes(role.value))
const canClose = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'assessmentNo', label: '评估编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'assessmentItem', label: '评估项目', width: 180 },
  { prop: 'assessmentTime', label: '评估时间', width: 160 },
  { prop: 'assessorName', label: '评估人', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'assessmentNo', label: '评估编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'assessmentItem', label: '评估项目' },
  { prop: 'assessmentTime', label: '评估时间' },
  { prop: 'assessorName', label: '评估人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用中', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canClose.value ? [
  { command: 'activate', label: '启动评估', type: 'primary' },
  { command: 'finish', label: '完成评估', type: 'success' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateHealthAssessment(row.id)
  if (command === 'finish') await finishHealthAssessment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
