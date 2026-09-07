<template>
  <DataPage title="审批记录" description="审批记录数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getApprovalRecordPage, addApprovalRecord, updateApprovalRecord, deleteApprovalRecord, approveApprovalRecord, rejectApprovalRecord } from '../api'
const api = { page: getApprovalRecordPage, add: addApprovalRecord, update: updateApprovalRecord, delete: deleteApprovalRecord }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'businessType', label: '业务类型' }, { prop: 'businessId', label: '业务ID' }, { prop: 'applicant', label: '申请人' }, { prop: 'approver', label: '审批人' }, { prop: 'decision', label: '审批结果' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'businessType', label: '业务类型' }, { prop: 'businessId', label: '业务ID', type: 'number' }, { prop: 'applicant', label: '申请人' }, { prop: 'approver', label: '审批人' }, { prop: 'decision', label: '审批结果' }, { prop: 'opinion', label: '审批意见', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = [{ label: '通过', command: 'approve', type: 'warning' }, { label: '驳回', command: 'reject', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'approve') await approveApprovalRecord(row.id)
if (command === 'reject') await rejectApprovalRecord(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
