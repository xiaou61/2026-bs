<template><DataPage title="作业区域" description="区域编码、区域名称、危险等级、负责人、管控时段和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SAFETY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWorkArea, deleteWorkArea, finishWorkArea, getWorkAreaPage, processWorkArea, updateWorkArea } from '../api'
const api = { page: getWorkAreaPage, add: addWorkArea, update: updateWorkArea, delete: deleteWorkArea, process: processWorkArea, finish: finishWorkArea }
const columns = [{ prop: 'recordNo', label: '区域编码' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '危险等级' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '管控时段' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '管控说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编码' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '危险等级' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '管控时段' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '管控说明', type: 'textarea' }]
</script>
