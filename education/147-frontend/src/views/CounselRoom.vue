<template>
  <DataPage
    title="咨询室管理"
    description="维护咨询室编号、名称、咨询类型、所属校区和接待上限，支撑资源排班与预约分配"
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
import { getCounselRoomPage, addCounselRoom, updateCounselRoom, deleteCounselRoom, activateCounselRoom, finishCounselRoom } from '../api'

const api = { page: getCounselRoomPage, add: addCounselRoom, update: updateCounselRoom, delete: deleteCounselRoom }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER'].includes(role.value))
const columns = [
  { prop: 'roomNo', label: '咨询室编号' },
  { prop: 'roomName', label: '咨询室名称', width: 180 },
  { prop: 'roomType', label: '咨询类型', width: 140 },
  { prop: 'campusName', label: '所属校区', width: 140 },
  { prop: 'capacityLimit', label: '接待上限', width: 120 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'roomNo', label: '咨询室编号' },
  { prop: 'roomName', label: '咨询室名称' },
  { prop: 'roomType', label: '咨询类型' },
  { prop: 'campusName', label: '所属校区' },
  { prop: 'capacityLimit', label: '接待上限', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '停用', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCounselRoom(row.id)
  if (command === 'finish') await finishCounselRoom(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








