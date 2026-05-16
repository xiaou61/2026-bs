<template><DataPage title="去向填报" description="填报编号、毕业学生、去向类型、审核老师、填报时间和填报状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CAREER', 'COLLEGE', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDestinationReport, deleteDestinationReport, finishDestinationReport, getDestinationReportPage, processDestinationReport, updateDestinationReport } from '../api'
const api = { page: getDestinationReportPage, add: addDestinationReport, update: updateDestinationReport, delete: deleteDestinationReport, process: processDestinationReport, finish: finishDestinationReport }
const columns = [{ prop: 'recordNo', label: '填报编号' }, { prop: 'recordName', label: '毕业学生' }, { prop: 'category', label: '去向类型' }, { prop: 'ownerName', label: '审核老师' }, { prop: 'planTime', label: '填报时间' }, { prop: 'status', label: '填报状态' }, { prop: 'remark', label: '填报说明' }]
const formFields = [{ prop: 'recordNo', label: '填报编号' }, { prop: 'recordName', label: '毕业学生' }, { prop: 'category', label: '去向类型' }, { prop: 'ownerName', label: '审核老师' }, { prop: 'planTime', label: '填报时间' }, { prop: 'status', label: '填报状态', type: 'select' }, { prop: 'remark', label: '填报说明', type: 'textarea' }]
</script>
