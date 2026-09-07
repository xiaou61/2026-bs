<template><DataPage title="问题整改" description="整改编号、问题摊位、问题类型、责任商户、整改时间和整改状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'VENDOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addIssueRectification, deleteIssueRectification, finishIssueRectification, getIssueRectificationPage, processIssueRectification, updateIssueRectification } from '../api'
const api = { page: getIssueRectificationPage, add: addIssueRectification, update: updateIssueRectification, delete: deleteIssueRectification, process: processIssueRectification, finish: finishIssueRectification }
const columns = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '问题摊位' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任商户' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态' }, { prop: 'remark', label: '整改说明' }]
const formFields = [{ prop: 'recordNo', label: '整改编号' }, { prop: 'recordName', label: '问题摊位' }, { prop: 'category', label: '问题类型' }, { prop: 'ownerName', label: '责任商户' }, { prop: 'planTime', label: '整改时间' }, { prop: 'status', label: '整改状态', type: 'select' }, { prop: 'remark', label: '整改说明', type: 'textarea' }]
</script>
