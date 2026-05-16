<template><DataPage title="拾物上报" description="拾物编号、拾物名称、拾物类型、上报人、发现时间和拾物状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SERVICE', 'SECURITY', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFoundReport, deleteFoundReport, finishFoundReport, getFoundReportPage, processFoundReport, updateFoundReport } from '../api'
const api = { page: getFoundReportPage, add: addFoundReport, update: updateFoundReport, delete: deleteFoundReport, process: processFoundReport, finish: finishFoundReport }
const columns = [{ prop: 'recordNo', label: '拾物编号' }, { prop: 'recordName', label: '拾物名称' }, { prop: 'category', label: '拾物类型' }, { prop: 'ownerName', label: '上报人' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '拾物状态' }, { prop: 'remark', label: '拾物说明' }]
const formFields = [{ prop: 'recordNo', label: '拾物编号' }, { prop: 'recordName', label: '拾物名称' }, { prop: 'category', label: '拾物类型' }, { prop: 'ownerName', label: '上报人' }, { prop: 'planTime', label: '发现时间' }, { prop: 'status', label: '拾物状态', type: 'select' }, { prop: 'remark', label: '拾物说明', type: 'textarea' }]
</script>
