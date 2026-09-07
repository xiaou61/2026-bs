<template>
  <DataPage title="审批流程" description="审批流程维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getApprovalFlowPage, addApprovalFlow, updateApprovalFlow, deleteApprovalFlow } from '../api'
const api = { page: getApprovalFlowPage, add: addApprovalFlow, update: updateApprovalFlow, delete: deleteApprovalFlow }
const columns = [{ prop: 'flowName', label: '流程名称' }, { prop: 'businessType', label: '业务类型' }, { prop: 'nodeName', label: '节点名称' }, { prop: 'approverRole', label: '审批角色' }, { prop: 'sortNo', label: '排序' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'flowName', label: '流程名称' }, { prop: 'businessType', label: '业务类型' }, { prop: 'nodeName', label: '节点名称' }, { prop: 'approverRole', label: '审批角色' }, { prop: 'sortNo', label: '排序', type: 'number' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
