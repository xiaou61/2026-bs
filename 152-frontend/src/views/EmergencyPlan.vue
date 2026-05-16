<template><DataPage title="应急预案" description="预案编号、预案名称、适用场景、负责人、演练时间和启用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEmergencyPlan, deleteEmergencyPlan, finishEmergencyPlan, getEmergencyPlanPage, processEmergencyPlan, updateEmergencyPlan } from '../api'
const api = { page: getEmergencyPlanPage, add: addEmergencyPlan, update: updateEmergencyPlan, delete: deleteEmergencyPlan, process: processEmergencyPlan, finish: finishEmergencyPlan }
const columns = [{ prop: 'recordNo', label: '预案编号' }, { prop: 'recordName', label: '预案名称' }, { prop: 'category', label: '适用场景' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '演练时间' }, { prop: 'status', label: '启用状态' }, { prop: 'remark', label: '预案说明' }]
const formFields = [{ prop: 'recordNo', label: '预案编号' }, { prop: 'recordName', label: '预案名称' }, { prop: 'category', label: '适用场景' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '演练时间' }, { prop: 'status', label: '启用状态', type: 'select' }, { prop: 'remark', label: '预案说明', type: 'textarea' }]
</script>
