<template><DataPage title="配送排线" description="排线编号、配送区域、排线类型、调度人员、排线时间和排线状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'DISPATCH', 'COURIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRoutePlan, deleteRoutePlan, finishRoutePlan, getRoutePlanPage, processRoutePlan, updateRoutePlan } from '../api'
const api = { page: getRoutePlanPage, add: addRoutePlan, update: updateRoutePlan, delete: deleteRoutePlan, process: processRoutePlan, finish: finishRoutePlan }
const columns = [{ prop: 'recordNo', label: '排线编号' }, { prop: 'recordName', label: '配送区域' }, { prop: 'category', label: '排线类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '排线时间' }, { prop: 'status', label: '排线状态' }, { prop: 'remark', label: '排线说明' }]
const formFields = [{ prop: 'recordNo', label: '排线编号' }, { prop: 'recordName', label: '配送区域' }, { prop: 'category', label: '排线类型' }, { prop: 'ownerName', label: '调度人员' }, { prop: 'planTime', label: '排线时间' }, { prop: 'status', label: '排线状态', type: 'select' }, { prop: 'remark', label: '排线说明', type: 'textarea' }]
</script>
