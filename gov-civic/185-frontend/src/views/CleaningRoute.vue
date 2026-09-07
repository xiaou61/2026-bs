<template><DataPage title="保洁路线" description="路线编号、覆盖区域、路线类型、保洁人员、计划时间和路线状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SANITATION', 'CLEANER', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCleaningRoute, deleteCleaningRoute, finishCleaningRoute, getCleaningRoutePage, processCleaningRoute, updateCleaningRoute } from '../api'
const api = { page: getCleaningRoutePage, add: addCleaningRoute, update: updateCleaningRoute, delete: deleteCleaningRoute, process: processCleaningRoute, finish: finishCleaningRoute }
const columns = [{ prop: 'recordNo', label: '路线编号' }, { prop: 'recordName', label: '覆盖区域' }, { prop: 'category', label: '路线类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '路线状态' }, { prop: 'remark', label: '路线说明' }]
const formFields = [{ prop: 'recordNo', label: '路线编号' }, { prop: 'recordName', label: '覆盖区域' }, { prop: 'category', label: '路线类型' }, { prop: 'ownerName', label: '保洁人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '路线状态', type: 'select' }, { prop: 'remark', label: '路线说明', type: 'textarea' }]
</script>
