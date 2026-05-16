<template><DataPage title="盘点差异" description="差异编号、关联盘点、差异类型、处理人员、发现时间和差异状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'CHECKER', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCheckDifference, deleteCheckDifference, finishCheckDifference, getCheckDifferencePage, processCheckDifference, updateCheckDifference } from '../api'
const api = { page: getCheckDifferencePage, add: addCheckDifference, update: updateCheckDifference, delete: deleteCheckDifference, process: processCheckDifference, finish: finishCheckDifference }
const columns = [{ prop: 'recordNo', label: '差异编号' }, { prop: 'recordName', label: '关联盘点' }, { prop: 'category', label: '差异类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '差异状态' }, { prop: 'remark', label: '差异说明' }]
const formFields = [{ prop: 'recordNo', label: '差异编号' }, { prop: 'recordName', label: '关联盘点' }, { prop: 'category', label: '差异类型' }, { prop: 'ownerName', label: '处理人员' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '差异状态', type: 'select' }, { prop: 'remark', label: '差异说明', type: 'textarea' }]
</script>
