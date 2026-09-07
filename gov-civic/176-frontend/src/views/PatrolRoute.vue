<template><DataPage title="巡线路线" description="路线编号、路线名称、巡线类型、负责人员、计划时间和路线状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPatrolRoute, deletePatrolRoute, finishPatrolRoute, getPatrolRoutePage, processPatrolRoute, updatePatrolRoute } from '../api'
const api = { page: getPatrolRoutePage, add: addPatrolRoute, update: updatePatrolRoute, delete: deletePatrolRoute, process: processPatrolRoute, finish: finishPatrolRoute }
const columns = [{ prop: 'recordNo', label: '路线编号' }, { prop: 'recordName', label: '路线名称' }, { prop: 'category', label: '巡线类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '路线状态' }, { prop: 'remark', label: '路线说明' }]
const formFields = [{ prop: 'recordNo', label: '路线编号' }, { prop: 'recordName', label: '路线名称' }, { prop: 'category', label: '巡线类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '计划时间' }, { prop: 'status', label: '路线状态', type: 'select' }, { prop: 'remark', label: '路线说明', type: 'textarea' }]
</script>
