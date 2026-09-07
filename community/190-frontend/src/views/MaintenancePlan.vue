<template><DataPage title="保养计划" description="计划编号、保养设备、保养类型、计划负责人、计划时间和计划状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PROPERTY', 'TECHNICIAN', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaintenancePlan, deleteMaintenancePlan, finishMaintenancePlan, getMaintenancePlanPage, processMaintenancePlan, updateMaintenancePlan } from '../api'
const api = { page: getMaintenancePlanPage, add: addMaintenancePlan, update: updateMaintenancePlan, delete: deleteMaintenancePlan, process: processMaintenancePlan, finish: finishMaintenancePlan }
const columns = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '保养设备' }, { prop: 'category', label: '保养类型' }, { prop: 'ownerName', label: '计划负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态' }, { prop: 'remark', label: '计划说明' }]
const formFields = [{ prop: 'recordNo', label: '计划编号' }, { prop: 'recordName', label: '保养设备' }, { prop: 'category', label: '保养类型' }, { prop: 'ownerName', label: '计划负责人' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '计划状态', type: 'select' }, { prop: 'remark', label: '计划说明', type: 'textarea' }]
</script>
