<template><DataPage title="操作日志" description="日志编号、操作模块、操作类型、操作人、操作时间和执行结果维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOperationLog, deleteOperationLog, finishOperationLog, getOperationLogPage, processOperationLog, updateOperationLog } from '../api'
const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog, process: processOperationLog, finish: finishOperationLog }
const columns = [{ prop: 'recordNo', label: '日志编号' }, { prop: 'recordName', label: '操作模块' }, { prop: 'category', label: '操作类型' }, { prop: 'ownerName', label: '操作人' }, { prop: 'planTime', label: '操作时间' }, { prop: 'status', label: '执行结果' }, { prop: 'remark', label: '操作详情' }]
const formFields = [{ prop: 'recordNo', label: '日志编号' }, { prop: 'recordName', label: '操作模块' }, { prop: 'category', label: '操作类型' }, { prop: 'ownerName', label: '操作人' }, { prop: 'planTime', label: '操作时间' }, { prop: 'status', label: '执行结果', type: 'select' }, { prop: 'remark', label: '操作详情', type: 'textarea' }]
</script>
