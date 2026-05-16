<template><DataPage title="手术使用" description="使用编号、手术名称、使用类型、手术医生、使用时间和使用状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORNURSE', 'SURGEON', 'CSSD']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSurgeryUse, deleteSurgeryUse, finishSurgeryUse, getSurgeryUsePage, processSurgeryUse, updateSurgeryUse } from '../api'
const api = { page: getSurgeryUsePage, add: addSurgeryUse, update: updateSurgeryUse, delete: deleteSurgeryUse, process: processSurgeryUse, finish: finishSurgeryUse }
const columns = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '手术名称' }, { prop: 'category', label: '使用类型' }, { prop: 'ownerName', label: '手术医生' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态' }, { prop: 'remark', label: '使用说明' }]
const formFields = [{ prop: 'recordNo', label: '使用编号' }, { prop: 'recordName', label: '手术名称' }, { prop: 'category', label: '使用类型' }, { prop: 'ownerName', label: '手术医生' }, { prop: 'planTime', label: '使用时间' }, { prop: 'status', label: '使用状态', type: 'select' }, { prop: 'remark', label: '使用说明', type: 'textarea' }]
</script>
