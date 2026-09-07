<template>
  <DataPage title="变更检查" description="变更检查维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChangeChecklistPage, addChangeChecklist, updateChangeChecklist, deleteChangeChecklist } from '../api'
const api = { page: getChangeChecklistPage, add: addChangeChecklist, update: updateChangeChecklist, delete: deleteChangeChecklist }
const columns = [{ prop: 'orderId', label: '发布单ID' }, { prop: 'itemName', label: '检查项' }, { prop: 'checkOwner', label: '检查人' }, { prop: 'requiredFlag', label: '必检' }, { prop: 'checkResult', label: '检查结果' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'orderId', label: '发布单ID', type: 'number' }, { prop: 'itemName', label: '检查项' }, { prop: 'checkOwner', label: '检查人' }, { prop: 'requiredFlag', label: '必检', type: 'number' }, { prop: 'checkResult', label: '检查结果' }, { prop: 'evidenceUrl', label: '证据地址' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
