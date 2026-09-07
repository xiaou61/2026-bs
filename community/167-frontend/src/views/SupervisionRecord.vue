<template><DataPage title="督导记录" description="督导编号、督导楼栋、督导类型、督导人员、督导时间和督导状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSupervisionRecord, deleteSupervisionRecord, finishSupervisionRecord, getSupervisionRecordPage, processSupervisionRecord, updateSupervisionRecord } from '../api'
const api = { page: getSupervisionRecordPage, add: addSupervisionRecord, update: updateSupervisionRecord, delete: deleteSupervisionRecord, process: processSupervisionRecord, finish: finishSupervisionRecord }
const columns = [{ prop: 'recordNo', label: '督导编号' }, { prop: 'recordName', label: '督导楼栋' }, { prop: 'category', label: '督导类型' }, { prop: 'ownerName', label: '督导人员' }, { prop: 'planTime', label: '督导时间' }, { prop: 'status', label: '督导状态' }, { prop: 'remark', label: '督导说明' }]
const formFields = [{ prop: 'recordNo', label: '督导编号' }, { prop: 'recordName', label: '督导楼栋' }, { prop: 'category', label: '督导类型' }, { prop: 'ownerName', label: '督导人员' }, { prop: 'planTime', label: '督导时间' }, { prop: 'status', label: '督导状态', type: 'select' }, { prop: 'remark', label: '督导说明', type: 'textarea' }]
</script>
