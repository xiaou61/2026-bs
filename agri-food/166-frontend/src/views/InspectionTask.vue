<template><DataPage title="摊位巡检" description="巡检编号、巡检摊位、巡检类型、巡检员、巡检时间和巡检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'INSPECTOR', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addInspectionTask, deleteInspectionTask, finishInspectionTask, getInspectionTaskPage, processInspectionTask, updateInspectionTask } from '../api'
const api = { page: getInspectionTaskPage, add: addInspectionTask, update: updateInspectionTask, delete: deleteInspectionTask, process: processInspectionTask, finish: finishInspectionTask }
const columns = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '巡检摊位' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态' }, { prop: 'remark', label: '巡检说明' }]
const formFields = [{ prop: 'recordNo', label: '巡检编号' }, { prop: 'recordName', label: '巡检摊位' }, { prop: 'category', label: '巡检类型' }, { prop: 'ownerName', label: '巡检员' }, { prop: 'planTime', label: '巡检时间' }, { prop: 'status', label: '巡检状态', type: 'select' }, { prop: 'remark', label: '巡检说明', type: 'textarea' }]
</script>
