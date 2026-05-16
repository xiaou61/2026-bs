<template>
  <DataPage
    title="测评问卷"
    description="维护问卷编号、咨询主题、问卷名称、提交时间和适用对象，支撑来访测评与结果留档"
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
import { getAssessmentQuestionnairePage, addAssessmentQuestionnaire, updateAssessmentQuestionnaire, deleteAssessmentQuestionnaire, processAssessmentQuestionnaire, finishAssessmentQuestionnaire } from '../api'

const api = { page: getAssessmentQuestionnairePage, add: addAssessmentQuestionnaire, update: updateAssessmentQuestionnaire, delete: deleteAssessmentQuestionnaire }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'STUDENT'].includes(role.value))
const canProcess = computed(() => ['ADMIN', 'TEACHER'].includes(role.value))
const columns = [
  { prop: 'questionnaireNo', label: '问卷编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'questionnaireName', label: '问卷名称', width: 180 },
  { prop: 'submitTime', label: '提交时间', width: 160 },
  { prop: 'targetGroup', label: '适用对象', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'questionnaireNo', label: '问卷编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'questionnaireName', label: '问卷名称' },
  { prop: 'submitTime', label: '提交时间' },
  { prop: 'targetGroup', label: '适用对象' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '评测中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canProcess.value ? [
  { command: 'process', label: '评测中', type: 'primary' },
  { command: 'finish', label: '已完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAssessmentQuestionnaire(row.id)
  if (command === 'finish') await finishAssessmentQuestionnaire(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








