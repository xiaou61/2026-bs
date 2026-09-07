<template><DataPage title="称重记录" description="称重编号、医废批次、称重类型、称重人、称重时间和称重状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'HOSPITAL', 'COLLECTOR', 'TRANSPORTER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addWeighingRecord, deleteWeighingRecord, finishWeighingRecord, getWeighingRecordPage, processWeighingRecord, updateWeighingRecord } from '../api'
const api = { page: getWeighingRecordPage, add: addWeighingRecord, update: updateWeighingRecord, delete: deleteWeighingRecord, process: processWeighingRecord, finish: finishWeighingRecord }
const columns = [{ prop: 'recordNo', label: '称重编号' }, { prop: 'recordName', label: '医废批次' }, { prop: 'category', label: '称重类型' }, { prop: 'ownerName', label: '称重人' }, { prop: 'planTime', label: '称重时间' }, { prop: 'status', label: '称重状态' }, { prop: 'remark', label: '称重说明' }]
const formFields = [{ prop: 'recordNo', label: '称重编号' }, { prop: 'recordName', label: '医废批次' }, { prop: 'category', label: '称重类型' }, { prop: 'ownerName', label: '称重人' }, { prop: 'planTime', label: '称重时间' }, { prop: 'status', label: '称重状态', type: 'select' }, { prop: 'remark', label: '称重说明', type: 'textarea' }]
</script>
