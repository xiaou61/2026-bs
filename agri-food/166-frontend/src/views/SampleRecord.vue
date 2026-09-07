<template><DataPage title="抽检记录" description="抽检编号、抽检商品、抽检类型、抽检人员、抽检时间和抽检状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'MARKET', 'SAMPLER', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSampleRecord, deleteSampleRecord, finishSampleRecord, getSampleRecordPage, processSampleRecord, updateSampleRecord } from '../api'
const api = { page: getSampleRecordPage, add: addSampleRecord, update: updateSampleRecord, delete: deleteSampleRecord, process: processSampleRecord, finish: finishSampleRecord }
const columns = [{ prop: 'recordNo', label: '抽检编号' }, { prop: 'recordName', label: '抽检商品' }, { prop: 'category', label: '抽检类型' }, { prop: 'ownerName', label: '抽检人员' }, { prop: 'planTime', label: '抽检时间' }, { prop: 'status', label: '抽检状态' }, { prop: 'remark', label: '抽检说明' }]
const formFields = [{ prop: 'recordNo', label: '抽检编号' }, { prop: 'recordName', label: '抽检商品' }, { prop: 'category', label: '抽检类型' }, { prop: 'ownerName', label: '抽检人员' }, { prop: 'planTime', label: '抽检时间' }, { prop: 'status', label: '抽检状态', type: 'select' }, { prop: 'remark', label: '抽检说明', type: 'textarea' }]
</script>
