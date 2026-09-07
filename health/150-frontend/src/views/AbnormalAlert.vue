<template>
  <DataPage title="异常预警" description="预警编号、患者姓名、预警类型、预警时间、处理人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addAbnormalAlert, deleteAbnormalAlert, finishAbnormalAlert, getAbnormalAlertPage, processAbnormalAlert, updateAbnormalAlert } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'process', label: '处理中', type: 'warning' },
      { command: 'finish', label: '处理完成', type: 'success' }
    ]
  : [])
const api = { page: getAbnormalAlertPage, add: addAbnormalAlert, update: updateAbnormalAlert, delete: deleteAbnormalAlert }
const columns = [
  { prop: 'alertNo', label: '预警编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'alertType', label: '预警类型' },
  { prop: 'alertTime', label: '预警时间' },
  { prop: 'handlerName', label: '处理人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'alertNo', label: '预警编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'alertType', label: '预警类型' },
  { prop: 'alertTime', label: '预警时间' },
  { prop: 'handlerName', label: '处理人' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '预警中', value: 'WARNING' },
      { label: '处理中', value: 'PROCESSING' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'WARNING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAbnormalAlert(row.id)
  if (command === 'finish') await finishAbnormalAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
