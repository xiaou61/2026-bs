<template>
  <DataPage title="设备借用人" description="借用人编号、借用人姓名、所属班级、联系电话、注册时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBorrowUserPage, addBorrowUser, updateBorrowUser, deleteBorrowUser, processBorrowUser, finishBorrowUser } from '../api'

const api = { page: getBorrowUserPage, add: addBorrowUser, update: updateBorrowUser, delete: deleteBorrowUser }
const columns = [
  { prop: 'borrowerNo', label: '借用人编号' },
  { prop: 'borrowerName', label: '借用人姓名' },
  { prop: 'className', label: '所属班级' },
  { prop: 'phoneNumber', label: '联系电话' },
  { prop: 'registerTime', label: '注册时间' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'borrowerNo', label: '借用人编号' },
  { prop: 'borrowerName', label: '借用人姓名' },
  { prop: 'className', label: '所属班级' },
  { prop: 'phoneNumber', label: '联系电话' },
  { prop: 'registerTime', label: '注册时间' },
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
const rowActions = [
  { command: 'process', label: '启用', type: 'success' },
  { command: 'finish', label: '停用', type: 'warning' }
]
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processBorrowUser(row.id)
  if (command === 'finish') await finishBorrowUser(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
