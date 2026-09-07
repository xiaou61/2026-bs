<template><DataPage title="下车核验" description="下车编号、核验线路、核验类型、核验人员、下车时间和下车状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'DRIVER', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDropoffCheckin, deleteDropoffCheckin, finishDropoffCheckin, getDropoffCheckinPage, processDropoffCheckin, updateDropoffCheckin } from '../api'
const api = { page: getDropoffCheckinPage, add: addDropoffCheckin, update: updateDropoffCheckin, delete: deleteDropoffCheckin, process: processDropoffCheckin, finish: finishDropoffCheckin }
const columns = [{ prop: 'recordNo', label: '下车编号' }, { prop: 'recordName', label: '核验线路' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '下车时间' }, { prop: 'status', label: '下车状态' }, { prop: 'remark', label: '下车说明' }]
const formFields = [{ prop: 'recordNo', label: '下车编号' }, { prop: 'recordName', label: '核验线路' }, { prop: 'category', label: '核验类型' }, { prop: 'ownerName', label: '核验人员' }, { prop: 'planTime', label: '下车时间' }, { prop: 'status', label: '下车状态', type: 'select' }, { prop: 'remark', label: '下车说明', type: 'textarea' }]
</script>
