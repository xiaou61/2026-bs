<template><DataPage title="违规处理" description="处理编号、异常占位、处理类型、处理人员、处理时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL', 'TENANT', 'SERVICE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addViolationHandling, deleteViolationHandling, finishViolationHandling, getViolationHandlingPage, processViolationHandling, updateViolationHandling } from '../api'
const api = { page: getViolationHandlingPage, add: addViolationHandling, update: updateViolationHandling, delete: deleteViolationHandling, process: processViolationHandling, finish: finishViolationHandling }
const columns = [{ prop: 'recordNo', label: '处理编号' }, { prop: 'recordName', label: '异常占位' }, { prop: 'category', label: '处理类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '处理时间' }, { prop: 'status', label: '处理状态' }, { prop: 'remark', label: '处理说明' }]
const formFields = [{ prop: 'recordNo', label: '处理编号' }, { prop: 'recordName', label: '异常占位' }, { prop: 'category', label: '处理类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '处理时间' }, { prop: 'status', label: '处理状态', type: 'select' }, { prop: 'remark', label: '处理说明', type: 'textarea' }]
</script>
