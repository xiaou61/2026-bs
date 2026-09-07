<template><DataPage title="家属查询" description="查询编号、查询老人、查询类型、家属人员、查询时间和查询状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'NURSING', 'CAREGIVER', 'FAMILY', 'DIRECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFamilyQuery, deleteFamilyQuery, finishFamilyQuery, getFamilyQueryPage, processFamilyQuery, updateFamilyQuery } from '../api'
const api = { page: getFamilyQueryPage, add: addFamilyQuery, update: updateFamilyQuery, delete: deleteFamilyQuery, process: processFamilyQuery, finish: finishFamilyQuery }
const columns = [{ prop: 'recordNo', label: '查询编号' }, { prop: 'recordName', label: '查询老人' }, { prop: 'category', label: '查询类型' }, { prop: 'ownerName', label: '家属人员' }, { prop: 'planTime', label: '查询时间' }, { prop: 'status', label: '查询状态' }, { prop: 'remark', label: '查询说明' }]
const formFields = [{ prop: 'recordNo', label: '查询编号' }, { prop: 'recordName', label: '查询老人' }, { prop: 'category', label: '查询类型' }, { prop: 'ownerName', label: '家属人员' }, { prop: 'planTime', label: '查询时间' }, { prop: 'status', label: '查询状态', type: 'select' }, { prop: 'remark', label: '查询说明', type: 'textarea' }]
</script>
