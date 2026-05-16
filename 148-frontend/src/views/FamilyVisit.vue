<template>
  <DataPage
    title="家属回访"
    description="维护回访编号、老人姓名、回访主题、回访方式和回访时间，支撑家属沟通与满意度跟踪"
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
import { getFamilyVisitPage, addFamilyVisit, updateFamilyVisit, deleteFamilyVisit, activateFamilyVisit, finishFamilyVisit } from '../api'

const api = { page: getFamilyVisitPage, add: addFamilyVisit, update: updateFamilyVisit, delete: deleteFamilyVisit }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'visitNo', label: '回访编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'visitSubject', label: '回访主题', width: 180 },
  { prop: 'visitMethod', label: '回访方式', width: 140 },
  { prop: 'visitTime', label: '回访时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'visitNo', label: '回访编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'visitSubject', label: '回访主题' },
  { prop: 'visitMethod', label: '回访方式' },
  { prop: 'visitTime', label: '回访时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用中', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '发起回访', type: 'primary' },
  { command: 'finish', label: '完成回访', type: 'success' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFamilyVisit(row.id)
  if (command === 'finish') await finishFamilyVisit(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
