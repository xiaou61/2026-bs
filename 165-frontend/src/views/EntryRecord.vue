<template><DataPage title="入园登记" description="登记编号、来访对象、通行方式、门岗人员、入园时间和登记状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'SECURITY', 'VISITOR', 'EMPLOYEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addEntryRecord, deleteEntryRecord, finishEntryRecord, getEntryRecordPage, processEntryRecord, updateEntryRecord } from '../api'
const api = { page: getEntryRecordPage, add: addEntryRecord, update: updateEntryRecord, delete: deleteEntryRecord, process: processEntryRecord, finish: finishEntryRecord }
const columns = [{ prop: 'recordNo', label: '登记编号' }, { prop: 'recordName', label: '来访对象' }, { prop: 'category', label: '通行方式' }, { prop: 'ownerName', label: '门岗人员' }, { prop: 'planTime', label: '入园时间' }, { prop: 'status', label: '登记状态' }, { prop: 'remark', label: '登记说明' }]
const formFields = [{ prop: 'recordNo', label: '登记编号' }, { prop: 'recordName', label: '来访对象' }, { prop: 'category', label: '通行方式' }, { prop: 'ownerName', label: '门岗人员' }, { prop: 'planTime', label: '入园时间' }, { prop: 'status', label: '登记状态', type: 'select' }, { prop: 'remark', label: '登记说明', type: 'textarea' }]
</script>
