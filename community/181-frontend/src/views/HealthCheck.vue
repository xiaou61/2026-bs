<template><DataPage title="健康晨检" description="晨检编号、托管儿童、健康类型、保健老师、晨检时间和健康状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'NURSE', 'TEACHER', 'PARENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHealthCheck, deleteHealthCheck, finishHealthCheck, getHealthCheckPage, processHealthCheck, updateHealthCheck } from '../api'
const api = { page: getHealthCheckPage, add: addHealthCheck, update: updateHealthCheck, delete: deleteHealthCheck, process: processHealthCheck, finish: finishHealthCheck }
const columns = [{ prop: 'recordNo', label: '晨检编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '健康类型' }, { prop: 'ownerName', label: '保健老师' }, { prop: 'planTime', label: '晨检时间' }, { prop: 'status', label: '健康状态' }, { prop: 'remark', label: '健康说明' }]
const formFields = [{ prop: 'recordNo', label: '晨检编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '健康类型' }, { prop: 'ownerName', label: '保健老师' }, { prop: 'planTime', label: '晨检时间' }, { prop: 'status', label: '健康状态', type: 'select' }, { prop: 'remark', label: '健康说明', type: 'textarea' }]
</script>
