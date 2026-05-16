<template>
  <DataPage
    title="随访计划"
    description="维护随访编号、咨询主题、随访阶段、随访内容和计划时间，支撑结案后的连续追踪"
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
import { getFollowUpPlanPage, addFollowUpPlan, updateFollowUpPlan, deleteFollowUpPlan, activateFollowUpPlan, finishFollowUpPlan } from '../api'

const api = { page: getFollowUpPlanPage, add: addFollowUpPlan, update: updateFollowUpPlan, delete: deleteFollowUpPlan }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'followUpNo', label: '随访编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'followUpStage', label: '随访阶段', width: 140 },
  { prop: 'followUpContent', label: '随访内容', width: 240 },
  { prop: 'plannedTime', label: '计划时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'followUpNo', label: '随访编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'followUpStage', label: '随访阶段' },
  { prop: 'followUpContent', label: '随访内容', type: 'textarea' },
  { prop: 'plannedTime', label: '计划时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '执行中', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '执行中', type: 'primary' },
  { command: 'finish', label: '已完成', type: 'success' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFollowUpPlan(row.id)
  if (command === 'finish') await finishFollowUpPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








