<template>
  <DataPage title="通知公告" description="通知编号、患者姓名、通知类型、通知内容、接收人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addHospitalNotice, deleteHospitalNotice, finishHospitalNotice, getHospitalNoticePage, publishHospitalNotice, updateHospitalNotice } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'publish', label: '发布通知', type: 'primary' },
      { command: 'finish', label: '结束通知', type: 'success' }
    ]
  : [])
const api = { page: getHospitalNoticePage, add: addHospitalNotice, update: updateHospitalNotice, delete: deleteHospitalNotice }
const columns = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', width: 240 },
  { prop: 'receiverName', label: '接收人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'noticeNo', label: '通知编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'noticeType', label: '通知类型' },
  { prop: 'noticeContent', label: '通知内容', type: 'textarea' },
  { prop: 'receiverName', label: '接收人' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '已发布', value: 'PUBLISHED' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'PUBLISHED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishHospitalNotice(row.id)
  if (command === 'finish') await finishHospitalNotice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
