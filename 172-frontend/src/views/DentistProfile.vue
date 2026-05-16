<template><DataPage title="医生档案" description="医生编号、医生姓名、擅长方向、所属诊室、建档时间和医生状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDentistProfile, deleteDentistProfile, finishDentistProfile, getDentistProfilePage, processDentistProfile, updateDentistProfile } from '../api'
const api = { page: getDentistProfilePage, add: addDentistProfile, update: updateDentistProfile, delete: deleteDentistProfile, process: processDentistProfile, finish: finishDentistProfile }
const columns = [{ prop: 'recordNo', label: '医生编号' }, { prop: 'recordName', label: '医生姓名' }, { prop: 'category', label: '擅长方向' }, { prop: 'ownerName', label: '所属诊室' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '医生状态' }, { prop: 'remark', label: '医生说明' }]
const formFields = [{ prop: 'recordNo', label: '医生编号' }, { prop: 'recordName', label: '医生姓名' }, { prop: 'category', label: '擅长方向' }, { prop: 'ownerName', label: '所属诊室' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '医生状态', type: 'select' }, { prop: 'remark', label: '医生说明', type: 'textarea' }]
</script>
