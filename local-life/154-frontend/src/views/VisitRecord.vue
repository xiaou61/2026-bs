<template><DataPage title="接诊记录" description="接诊编号、关联预约、疾病类型、接诊医生、接诊时间和接诊状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DOCTOR', 'NURSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addVisitRecord, deleteVisitRecord, finishVisitRecord, getVisitRecordPage, processVisitRecord, updateVisitRecord } from '../api'
const api = { page: getVisitRecordPage, add: addVisitRecord, update: updateVisitRecord, delete: deleteVisitRecord, process: processVisitRecord, finish: finishVisitRecord }
const columns = [{ prop: 'recordNo', label: '接诊编号' }, { prop: 'recordName', label: '关联预约' }, { prop: 'category', label: '疾病类型' }, { prop: 'ownerName', label: '接诊医生' }, { prop: 'planTime', label: '接诊时间' }, { prop: 'status', label: '接诊状态' }, { prop: 'remark', label: '诊疗建议' }]
const formFields = [{ prop: 'recordNo', label: '接诊编号' }, { prop: 'recordName', label: '关联预约' }, { prop: 'category', label: '疾病类型' }, { prop: 'ownerName', label: '接诊医生' }, { prop: 'planTime', label: '接诊时间' }, { prop: 'status', label: '接诊状态', type: 'select' }, { prop: 'remark', label: '诊疗建议', type: 'textarea' }]
</script>
