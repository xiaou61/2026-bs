<template><DataPage title="老人档案" description="老人编号、老人姓名、服务类型、联系人、建档时间和档案状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ELDER', 'DIETITIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addElderProfile, deleteElderProfile, finishElderProfile, getElderProfilePage, processElderProfile, updateElderProfile } from '../api'
const api = { page: getElderProfilePage, add: addElderProfile, update: updateElderProfile, delete: deleteElderProfile, process: processElderProfile, finish: finishElderProfile }
const columns = [{ prop: 'recordNo', label: '老人编号' }, { prop: 'recordName', label: '老人姓名' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '老人编号' }, { prop: 'recordName', label: '老人姓名' }, { prop: 'category', label: '服务类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '档案状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>
