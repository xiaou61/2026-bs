<template>
  <DataPage title="签到记录" description="签到编号、患者姓名、签到方式、签到窗口、签到时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addCheckinRecord, approveCheckinRecord, deleteCheckinRecord, getCheckinRecordPage, submitCheckinRecord, updateCheckinRecord } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'submit', label: '提交签到', type: 'primary' },
      { command: 'approve', label: '确认到检', type: 'success' }
    ]
  : [])
const api = { page: getCheckinRecordPage, add: addCheckinRecord, update: updateCheckinRecord, delete: deleteCheckinRecord }
const columns = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'checkinMethod', label: '签到方式' },
  { prop: 'checkinWindow', label: '签到窗口' },
  { prop: 'checkinTime', label: '签到时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'checkinNo', label: '签到编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'checkinMethod', label: '签到方式' },
  { prop: 'checkinWindow', label: '签到窗口' },
  { prop: 'checkinTime', label: '签到时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已通过', value: 'APPROVED' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCheckinRecord(row.id)
  if (command === 'approve') await approveCheckinRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
