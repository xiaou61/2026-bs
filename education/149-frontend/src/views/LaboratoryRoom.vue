<template>
  <DataPage
    title="实验室档案"
    description="实验室编号、实验室名称、实验室类型、所属校区、容纳数量和状态维护"
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
import { getLaboratoryRoomPage, addLaboratoryRoom, updateLaboratoryRoom, deleteLaboratoryRoom, activateLaboratoryRoom, finishLaboratoryRoom } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getLaboratoryRoomPage, add: addLaboratoryRoom, update: updateLaboratoryRoom, delete: deleteLaboratoryRoom }
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'labNo', label: '实验室编号' },
  { prop: 'labName', label: '实验室名称' },
  { prop: 'labType', label: '实验室类型' },
  { prop: 'campusName', label: '所属校区' },
  { prop: 'capacity', label: '容纳数量' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'labNo', label: '实验室编号' },
  { prop: 'labName', label: '实验室名称' },
  { prop: 'labType', label: '实验室类型' },
  { prop: 'campusName', label: '所属校区' },
  { prop: 'capacity', label: '容纳数量', type: 'number' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '启用中', value: 'ACTIVE' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateLaboratoryRoom(row.id)
  if (command === 'finish') await finishLaboratoryRoom(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
