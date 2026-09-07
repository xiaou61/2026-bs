<template><DataPage title="补贴记录" description="补贴编号、服务对象、补贴类型、审核人员、登记时间和补贴状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'ELDER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSubsidyRecord, deleteSubsidyRecord, finishSubsidyRecord, getSubsidyRecordPage, processSubsidyRecord, updateSubsidyRecord } from '../api'
const api = { page: getSubsidyRecordPage, add: addSubsidyRecord, update: updateSubsidyRecord, delete: deleteSubsidyRecord, process: processSubsidyRecord, finish: finishSubsidyRecord }
const columns = [{ prop: 'recordNo', label: '补贴编号' }, { prop: 'recordName', label: '服务对象' }, { prop: 'category', label: '补贴类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '补贴状态' }, { prop: 'remark', label: '补贴说明' }]
const formFields = [{ prop: 'recordNo', label: '补贴编号' }, { prop: 'recordName', label: '服务对象' }, { prop: 'category', label: '补贴类型' }, { prop: 'ownerName', label: '审核人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '补贴状态', type: 'select' }, { prop: 'remark', label: '补贴说明', type: 'textarea' }]
</script>
