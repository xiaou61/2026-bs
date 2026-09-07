<template><DataPage title="慢病方案" description="方案编号、顾客姓名、慢病类型、管理人员、方案时间和方案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'PHARMACIST', 'FOLLOWUP', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addChronicPlan, deleteChronicPlan, finishChronicPlan, getChronicPlanPage, processChronicPlan, updateChronicPlan } from '../api'
const api = { page: getChronicPlanPage, add: addChronicPlan, update: updateChronicPlan, delete: deleteChronicPlan, process: processChronicPlan, finish: finishChronicPlan }
const columns = [{ prop: 'recordNo', label: '方案编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '方案时间' }, { prop: 'status', label: '方案状态' }, { prop: 'remark', label: '方案说明' }]
const formFields = [{ prop: 'recordNo', label: '方案编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '慢病类型' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '方案时间' }, { prop: 'status', label: '方案状态', type: 'select' }, { prop: 'remark', label: '方案说明', type: 'textarea' }]
</script>
