<template>
  <DataPage
    title="护理档案"
    description="维护护理编号、护理姓名、联系电话、护理等级和所属机构，支撑护理资源统一调度"
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
import { getCaregiverProfilePage, addCaregiverProfile, updateCaregiverProfile, deleteCaregiverProfile, processCaregiverProfile, finishCaregiverProfile } from '../api'

const api = { page: getCaregiverProfilePage, add: addCaregiverProfile, update: updateCaregiverProfile, delete: deleteCaregiverProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'caregiverNo', label: '护理编号' },
  { prop: 'caregiverName', label: '护理姓名', width: 140 },
  { prop: 'phoneNumber', label: '联系电话', width: 140 },
  { prop: 'caregiverLevel', label: '护理等级', width: 140 },
  { prop: 'organizationName', label: '所属机构', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'caregiverNo', label: '护理编号' },
  { prop: 'caregiverName', label: '护理姓名' },
  { prop: 'phoneNumber', label: '联系电话' },
  { prop: 'caregiverLevel', label: '护理等级' },
  { prop: 'organizationName', label: '所属机构' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '在岗处理中', value: 'PROCESSING' }, { label: '已停用', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '安排上岗', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCaregiverProfile(row.id)
  if (command === 'finish') await finishCaregiverProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
