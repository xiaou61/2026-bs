<template><DataPage title="护理区域" description="区域编号、区域名称、区域类型、负责人员、启用时间和区域状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ADMISSION', 'NURSING', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareArea, deleteCareArea, finishCareArea, getCareAreaPage, processCareArea, updateCareArea } from '../api'
const api = { page: getCareAreaPage, add: addCareArea, update: updateCareArea, delete: deleteCareArea, process: processCareArea, finish: finishCareArea }
const columns = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态' }, { prop: 'remark', label: '区域说明' }]
const formFields = [{ prop: 'recordNo', label: '区域编号' }, { prop: 'recordName', label: '区域名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '区域状态', type: 'select' }, { prop: 'remark', label: '区域说明', type: 'textarea' }]
</script>
