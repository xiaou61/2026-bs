<template><DataPage title="家属档案" description="家属编号、家属姓名、关联老人、联系人员、建档时间和家属状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFamilyProfile, deleteFamilyProfile, finishFamilyProfile, getFamilyProfilePage, processFamilyProfile, updateFamilyProfile } from '../api'
const api = { page: getFamilyProfilePage, add: addFamilyProfile, update: updateFamilyProfile, delete: deleteFamilyProfile, process: processFamilyProfile, finish: finishFamilyProfile }
const columns = [{ prop: 'recordNo', label: '家属编号' }, { prop: 'recordName', label: '家属姓名' }, { prop: 'category', label: '关联老人' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '家属状态' }, { prop: 'remark', label: '家属说明' }]
const formFields = [{ prop: 'recordNo', label: '家属编号' }, { prop: 'recordName', label: '家属姓名' }, { prop: 'category', label: '关联老人' }, { prop: 'ownerName', label: '联系人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '家属状态', type: 'select' }, { prop: 'remark', label: '家属说明', type: 'textarea' }]
</script>
