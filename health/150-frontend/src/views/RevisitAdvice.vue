<template>
  <DataPage title="复诊建议" description="建议编号、患者姓名、建议主题、建议类型、建议时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addRevisitAdvice, deleteRevisitAdvice, finishRevisitAdvice, getRevisitAdvicePage, publishRevisitAdvice, updateRevisitAdvice } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'publish', label: '发布建议', type: 'primary' },
      { command: 'finish', label: '建议完成', type: 'success' }
    ]
  : [])
const api = { page: getRevisitAdvicePage, add: addRevisitAdvice, update: updateRevisitAdvice, delete: deleteRevisitAdvice }
const columns = [
  { prop: 'adviceNo', label: '建议编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'adviceSubject', label: '建议主题' },
  { prop: 'adviceType', label: '建议类型' },
  { prop: 'adviceTime', label: '建议时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'adviceNo', label: '建议编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'adviceSubject', label: '建议主题' },
  { prop: 'adviceType', label: '建议类型' },
  { prop: 'adviceTime', label: '建议时间' },
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
  if (command === 'publish') await publishRevisitAdvice(row.id)
  if (command === 'finish') await finishRevisitAdvice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
