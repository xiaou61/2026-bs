<template><DataPage title="作业回执" description="回执编号、作业派单、回执类型、机手人员、完成时间和回执状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'STATION', 'DRIVER', 'FARMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkCompletion, deleteWorkCompletion, finishWorkCompletion, getWorkCompletionPage, processWorkCompletion, updateWorkCompletion } from '../api'
const api = { page: getWorkCompletionPage, add: addWorkCompletion, update: updateWorkCompletion, delete: deleteWorkCompletion, process: processWorkCompletion, finish: finishWorkCompletion }
const columns = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '作业派单' }, { prop: 'category', label: '回执类型' }, { prop: 'ownerName', label: '机手人员' }, { prop: 'planTime', label: '完成时间' }, { prop: 'status', label: '回执状态' }, { prop: 'remark', label: '回执说明' }]
const formFields = [{ prop: 'recordNo', label: '回执编号' }, { prop: 'recordName', label: '作业派单' }, { prop: 'category', label: '回执类型' }, { prop: 'ownerName', label: '机手人员' }, { prop: 'planTime', label: '完成时间' }, { prop: 'status', label: '回执状态', type: 'select' }, { prop: 'remark', label: '回执说明', type: 'textarea' }]
</script>
