<template>
  <DataPage title="患者档案" description="患者编号、患者姓名、身份证号、联系电话、就诊卡号和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activatePatientProfile, addPatientProfile, deletePatientProfile, disablePatientProfile, getPatientProfilePage, updatePatientProfile } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'activate', label: '恢复', type: 'success' },
      { command: 'disable', label: '停用', type: 'warning' }
    ]
  : [])
const api = { page: getPatientProfilePage, add: addPatientProfile, update: updatePatientProfile, delete: deletePatientProfile }
const columns = [
  { prop: 'patientNo', label: '患者编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'idCard', label: '身份证号', width: 180 },
  { prop: 'phone', label: '联系电话' },
  { prop: 'visitCardNo', label: '就诊卡号' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'patientNo', label: '患者编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'idCard', label: '身份证号' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'visitCardNo', label: '就诊卡号' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '正常', value: 'NORMAL' },
      { label: '停用', value: 'DISABLED' }
    ]
  }
]
const defaults = { status: 'NORMAL' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activatePatientProfile(row.id)
  if (command === 'disable') await disablePatientProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
