<template><DataPage title="监管抽查" description="抽查编号、抽查对象、抽查类型、监管人、抽查时间和抽查状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addComplianceAudit, deleteComplianceAudit, finishComplianceAudit, getComplianceAuditPage, processComplianceAudit, updateComplianceAudit } from '../api'
const api = { page: getComplianceAuditPage, add: addComplianceAudit, update: updateComplianceAudit, delete: deleteComplianceAudit, process: processComplianceAudit, finish: finishComplianceAudit }
const columns = [{ prop: 'recordNo', label: '抽查编号' }, { prop: 'recordName', label: '抽查对象' }, { prop: 'category', label: '抽查类型' }, { prop: 'ownerName', label: '监管人' }, { prop: 'planTime', label: '抽查时间' }, { prop: 'status', label: '抽查状态' }, { prop: 'remark', label: '抽查说明' }]
const formFields = [{ prop: 'recordNo', label: '抽查编号' }, { prop: 'recordName', label: '抽查对象' }, { prop: 'category', label: '抽查类型' }, { prop: 'ownerName', label: '监管人' }, { prop: 'planTime', label: '抽查时间' }, { prop: 'status', label: '抽查状态', type: 'select' }, { prop: 'remark', label: '抽查说明', type: 'textarea' }]
</script>
