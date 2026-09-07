<template>
  <DataPage
    title="家校沟通"
    description="维护沟通编号、咨询主题、沟通主题、沟通方式和沟通时间，支撑家校联动提醒与跟进"
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
import { getFamilyCommunicationPage, addFamilyCommunication, updateFamilyCommunication, deleteFamilyCommunication, processFamilyCommunication, finishFamilyCommunication } from '../api'

const api = { page: getFamilyCommunicationPage, add: addFamilyCommunication, update: updateFamilyCommunication, delete: deleteFamilyCommunication }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'communicationNo', label: '沟通编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'communicationTopic', label: '沟通主题', width: 180 },
  { prop: 'communicationMethod', label: '沟通方式', width: 140 },
  { prop: 'communicationTime', label: '沟通时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'communicationNo', label: '沟通编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'communicationTopic', label: '沟通主题' },
  { prop: 'communicationMethod', label: '沟通方式' },
  { prop: 'communicationTime', label: '沟通时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '沟通中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '沟通中', type: 'primary' },
  { command: 'finish', label: '已完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processFamilyCommunication(row.id)
  if (command === 'finish') await finishFamilyCommunication(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








