<template><DataPage title="老人档案" description="老人编号、老人姓名、照护等级、责任人员、建档时间和老人状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addElderProfile, deleteElderProfile, finishElderProfile, getElderProfilePage, processElderProfile, updateElderProfile } from '../api'
const api = { page: getElderProfilePage, add: addElderProfile, update: updateElderProfile, delete: deleteElderProfile, process: processElderProfile, finish: finishElderProfile }
const columns = [{ prop: 'recordNo', label: '老人编号' }, { prop: 'recordName', label: '老人姓名' }, { prop: 'category', label: '照护等级' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '老人状态' }, { prop: 'remark', label: '老人说明' }]
const formFields = [{ prop: 'recordNo', label: '老人编号' }, { prop: 'recordName', label: '老人姓名' }, { prop: 'category', label: '照护等级' }, { prop: 'ownerName', label: '责任人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '老人状态', type: 'select' }, { prop: 'remark', label: '老人说明', type: 'textarea' }]
</script>
