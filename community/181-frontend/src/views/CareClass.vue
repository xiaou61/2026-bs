<template><DataPage title="托管班级" description="班级编号、班级名称、托管类型、负责老师、开班时间和班级状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'TEACHER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCareClass, deleteCareClass, finishCareClass, getCareClassPage, processCareClass, updateCareClass } from '../api'
const api = { page: getCareClassPage, add: addCareClass, update: updateCareClass, delete: deleteCareClass, process: processCareClass, finish: finishCareClass }
const columns = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '托管类型' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班级状态' }, { prop: 'remark', label: '班级说明' }]
const formFields = [{ prop: 'recordNo', label: '班级编号' }, { prop: 'recordName', label: '班级名称' }, { prop: 'category', label: '托管类型' }, { prop: 'ownerName', label: '负责老师' }, { prop: 'planTime', label: '开班时间' }, { prop: 'status', label: '班级状态', type: 'select' }, { prop: 'remark', label: '班级说明', type: 'textarea' }]
</script>
