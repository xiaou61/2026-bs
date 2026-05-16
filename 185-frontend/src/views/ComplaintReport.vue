<template><DataPage title="投诉反馈" description="反馈编号、公厕点位、反馈类型、反馈人员、反馈时间和反馈状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'INSPECTOR', 'CITIZEN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addComplaintReport, deleteComplaintReport, finishComplaintReport, getComplaintReportPage, processComplaintReport, updateComplaintReport } from '../api'
const api = { page: getComplaintReportPage, add: addComplaintReport, update: updateComplaintReport, delete: deleteComplaintReport, process: processComplaintReport, finish: finishComplaintReport }
const columns = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态' }, { prop: 'remark', label: '反馈说明' }]
const formFields = [{ prop: 'recordNo', label: '反馈编号' }, { prop: 'recordName', label: '公厕点位' }, { prop: 'category', label: '反馈类型' }, { prop: 'ownerName', label: '反馈人员' }, { prop: 'planTime', label: '反馈时间' }, { prop: 'status', label: '反馈状态', type: 'select' }, { prop: 'remark', label: '反馈说明', type: 'textarea' }]
</script>
