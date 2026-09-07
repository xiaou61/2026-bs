<template>
  <DataPage
    title="值班安排"
    description="维护排班编号、值班老师、日期、时段和生效时间，支撑预约分流与值守安排"
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
import { getDutySchedulePage, addDutySchedule, updateDutySchedule, deleteDutySchedule, processDutySchedule, finishDutySchedule } from '../api'

const api = { page: getDutySchedulePage, add: addDutySchedule, update: updateDutySchedule, delete: deleteDutySchedule }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TEACHER'].includes(role.value))
const columns = [
  { prop: 'scheduleNo', label: '排班编号' },
  { prop: 'dutyTeacher', label: '值班老师', width: 160 },
  { prop: 'dutyDate', label: '值班日期', width: 140 },
  { prop: 'dutyPeriod', label: '值班时段', width: 180 },
  { prop: 'effectiveTime', label: '生效时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'scheduleNo', label: '排班编号' },
  { prop: 'dutyTeacher', label: '值班老师' },
  { prop: 'dutyDate', label: '值班日期' },
  { prop: 'dutyPeriod', label: '值班时段' },
  { prop: 'effectiveTime', label: '生效时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '排班中', value: 'PROCESSING' }, { label: '已结束', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'process', label: '排班中', type: 'primary' },
  { command: 'finish', label: '已结束', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processDutySchedule(row.id)
  if (command === 'finish') await finishDutySchedule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








