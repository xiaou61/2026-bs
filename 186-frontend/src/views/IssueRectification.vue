<template><DataPage title="问题整改" description="整改编号、巡检问题、整改类型、责任人员、整改时间和整改状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR', 'SCHOOL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addIssueRectification, deleteIssueRectification, finishIssueRectification, getIssueRectificationPage, processIssueRectification, updateIssueRectification } from '../api'
const api = { page: getIssueRectificationPage, add: addIssueRectification, update: updateIssueRectification, delete: deleteIssueRectification, process: processIssueRectification, finish: finishIssueRectification }
const columns = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '巡检问题' }, { prop: 'category', label: '整改类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态' }, { prop: 'remark', label: '整改说明' }]
const formFields = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '巡检问题' }, { prop: 'category', label: '整改类型' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态', type: 'select' }, { prop: 'remark', label: '整改说明', type: 'textarea' }]
</script>
