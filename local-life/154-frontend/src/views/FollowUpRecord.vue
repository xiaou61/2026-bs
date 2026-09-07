<template><DataPage title="随访记录" description="随访编号、关联接诊、随访方式、随访人、随访时间和随访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DOCTOR', 'NURSE', 'OWNER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFollowUpRecord, deleteFollowUpRecord, finishFollowUpRecord, getFollowUpRecordPage, processFollowUpRecord, updateFollowUpRecord } from '../api'
const api = { page: getFollowUpRecordPage, add: addFollowUpRecord, update: updateFollowUpRecord, delete: deleteFollowUpRecord, process: processFollowUpRecord, finish: finishFollowUpRecord }
const columns = [{ prop: 'recordNo', label: '随访编号' }, { prop: 'recordName', label: '关联接诊' }, { prop: 'category', label: '随访方式' }, { prop: 'ownerName', label: '随访人' }, { prop: 'planTime', label: '随访时间' }, { prop: 'status', label: '随访状态' }, { prop: 'remark', label: '随访结论' }]
const formFields = [{ prop: 'recordNo', label: '随访编号' }, { prop: 'recordName', label: '关联接诊' }, { prop: 'category', label: '随访方式' }, { prop: 'ownerName', label: '随访人' }, { prop: 'planTime', label: '随访时间' }, { prop: 'status', label: '随访状态', type: 'select' }, { prop: 'remark', label: '随访结论', type: 'textarea' }]
</script>
