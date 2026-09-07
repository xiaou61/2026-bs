<template>
  <DataPage title="报告回传" description="回传编号、患者姓名、回传方式、回传时间、接收人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addReportDelivery, deleteReportDelivery, finishReportDelivery, getReportDeliveryPage, processReportDelivery, updateReportDelivery } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'process', label: '回传中', type: 'warning' },
      { command: 'finish', label: '回传完成', type: 'success' }
    ]
  : [])
const api = { page: getReportDeliveryPage, add: addReportDelivery, update: updateReportDelivery, delete: deleteReportDelivery }
const columns = [
  { prop: 'deliveryNo', label: '回传编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'deliveryMethod', label: '回传方式' },
  { prop: 'deliveryTime', label: '回传时间' },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'deliveryNo', label: '回传编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'deliveryMethod', label: '回传方式' },
  { prop: 'deliveryTime', label: '回传时间' },
  { prop: 'receiverName', label: '接收人' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '处理中', value: 'PROCESSING' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processReportDelivery(row.id)
  if (command === 'finish') await finishReportDelivery(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
