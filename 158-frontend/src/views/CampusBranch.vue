<template><DataPage title="校区档案" description="校区编号、校区名称、校区类型、负责人、启用时间和校区状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PRINCIPAL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCampusBranch, deleteCampusBranch, finishCampusBranch, getCampusBranchPage, processCampusBranch, updateCampusBranch } from '../api'
const api = { page: getCampusBranchPage, add: addCampusBranch, update: updateCampusBranch, delete: deleteCampusBranch, process: processCampusBranch, finish: finishCampusBranch }
const columns = [{ prop: 'recordNo', label: '校区编号' }, { prop: 'recordName', label: '校区名称' }, { prop: 'category', label: '校区类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '校区状态' }, { prop: 'remark', label: '校区说明' }]
const formFields = [{ prop: 'recordNo', label: '校区编号' }, { prop: 'recordName', label: '校区名称' }, { prop: 'category', label: '校区类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '校区状态', type: 'select' }, { prop: 'remark', label: '校区说明', type: 'textarea' }]
</script>
