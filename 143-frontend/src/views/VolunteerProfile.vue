<template>
  <DataPage title="志愿者档案" description="维护志愿者编号、姓名、服务专长、可服务时间和加入时间，支撑志愿队伍管理与服务调度" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getVolunteerProfilePage, addVolunteerProfile, updateVolunteerProfile, deleteVolunteerProfile, processVolunteerProfile, finishVolunteerProfile } from '../api'

const api = { page: getVolunteerProfilePage, add: addVolunteerProfile, update: updateVolunteerProfile, delete: deleteVolunteerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER', 'VOLUNTEER'].includes(role.value))
const columns = [
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'volunteerName', label: '志愿者姓名' },
  { prop: 'serviceExpertise', label: '服务专长' },
  { prop: 'availableTime', label: '可服务时间' },
  { prop: 'joinTime', label: '加入时间', width: 150 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'volunteerName', label: '志愿者姓名' },
  { prop: 'serviceExpertise', label: '服务专长' },
  { prop: 'availableTime', label: '可服务时间' },
  { prop: 'joinTime', label: '加入时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'process', label: '激活', type: 'warning' }, { command: 'finish', label: '完成', type: 'success' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processVolunteerProfile(row.id)
  if (command === 'finish') await finishVolunteerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
