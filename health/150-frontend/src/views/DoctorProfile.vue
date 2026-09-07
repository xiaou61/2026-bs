<template>
  <DataPage title="医生档案" description="医生编号、医生姓名、联系电话、所属科室、职称等级和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activateDoctorProfile, addDoctorProfile, deleteDoctorProfile, disableDoctorProfile, getDoctorProfilePage, updateDoctorProfile } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'DOCTOR'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'activate', label: '启用', type: 'success' },
      { command: 'disable', label: '停用', type: 'warning' }
    ]
  : [])
const api = { page: getDoctorProfilePage, add: addDoctorProfile, update: updateDoctorProfile, delete: deleteDoctorProfile }
const columns = [
  { prop: 'doctorNo', label: '医生编号' },
  { prop: 'doctorName', label: '医生姓名' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'departmentName', label: '所属科室' },
  { prop: 'titleLevel', label: '职称等级' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'doctorNo', label: '医生编号' },
  { prop: 'doctorName', label: '医生姓名' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'departmentName', label: '所属科室' },
  { prop: 'titleLevel', label: '职称等级' },
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
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDoctorProfile(row.id)
  if (command === 'disable') await disableDoctorProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
