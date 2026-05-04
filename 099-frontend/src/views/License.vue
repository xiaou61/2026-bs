<template>
  <DataPage
    ref="pageRef"
    title="授权记录"
    description="维护版权登记后的授权对象、用途、有效期和撤销状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 1 }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addLicense, deleteLicense, getLicensePage, revokeLicense, updateLicense } from '../api'

const pageRef = ref()
const statusMap = {
  1: { label: '有效', type: 'success' },
  2: { label: '已撤销', type: 'danger' }
}
const api = { page: getLicensePage, add: addLicense, update: updateLicense, delete: deleteLicense }
const statusOptions = [
  { label: '有效', value: 1 },
  { label: '已撤销', value: 2 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '被授权方/用途' },
  { type: 'input', prop: 'registerId', label: '登记ID' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'registerId', label: '登记ID' },
  { prop: 'licensee', label: '被授权方', minWidth: 160 },
  { prop: 'purpose', label: '授权用途', minWidth: 220, long: true },
  { prop: 'startDate', label: '开始日期' },
  { prop: 'endDate', label: '结束日期' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'createTime', label: '创建时间', minWidth: 170 }
]
const formFields = [
  { prop: 'registerId', label: '登记ID', type: 'number', min: 1, required: true },
  { prop: 'licensee', label: '被授权方', required: true },
  { prop: 'purpose', label: '授权用途', type: 'textarea', rows: 3, required: true },
  { prop: 'startDate', label: '开始日期', type: 'date', required: true },
  { prop: 'endDate', label: '结束日期', type: 'date', required: true },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
const rowActions = [
  { name: 'revoke', label: '撤销', type: 'danger' }
]

const handleAction = async (name, row) => {
  if (name === 'revoke') {
    await revokeLicense(row.id)
    ElMessage.success('撤销成功')
    pageRef.value.loadData()
  }
}
</script>
