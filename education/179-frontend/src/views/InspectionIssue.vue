<template><DataPage title="巡查问题" description="问题编号、巡查事项、问题类型、责任人员、发现时间和问题状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ACADEMIC', 'INSPECTOR', 'COUNSELOR', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInspectionIssue, deleteInspectionIssue, finishInspectionIssue, getInspectionIssuePage, processInspectionIssue, updateInspectionIssue } from '../api'
const api = { page: getInspectionIssuePage, add: addInspectionIssue, update: updateInspectionIssue, delete: deleteInspectionIssue, process: processInspectionIssue, finish: finishInspectionIssue }
const columns = [{ prop: 'recordNo', label: '问题编号' }, { prop: 'recordName', label: '巡查事项' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '问题状态' }, { prop: 'remark', label: '问题说明' }]
const formFields = [{ prop: 'recordNo', label: '问题编号' }, { prop: 'recordName', label: '巡查事项' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '问题状态', type: 'select' }, { prop: 'remark', label: '问题说明', type: 'textarea' }]
</script>
