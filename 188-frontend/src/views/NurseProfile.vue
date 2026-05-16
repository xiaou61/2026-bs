<template><DataPage title="护理人员档案" description="档案编号、护士姓名、科室类型、责任老师、建档时间和在岗状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'EDU', 'HR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addNurseProfile, deleteNurseProfile, finishNurseProfile, getNurseProfilePage, processNurseProfile, updateNurseProfile } from '../api'
const api = { page: getNurseProfilePage, add: addNurseProfile, update: updateNurseProfile, delete: deleteNurseProfile, process: processNurseProfile, finish: finishNurseProfile }
const columns = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '护士姓名' }, { prop: 'category', label: '科室类型' }, { prop: 'ownerName', label: '责任老师' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '在岗状态' }, { prop: 'remark', label: '档案说明' }]
const formFields = [{ prop: 'recordNo', label: '档案编号' }, { prop: 'recordName', label: '护士姓名' }, { prop: 'category', label: '科室类型' }, { prop: 'ownerName', label: '责任老师' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '在岗状态', type: 'select' }, { prop: 'remark', label: '档案说明', type: 'textarea' }]
</script>
