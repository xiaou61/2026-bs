<template><DataPage title="随访记录" description="随访编号、慢病顾客、随访类型、随访人员、随访时间和随访状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'FOLLOWUP', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addFollowupRecord, deleteFollowupRecord, finishFollowupRecord, getFollowupRecordPage, processFollowupRecord, updateFollowupRecord } from '../api'
const api = { page: getFollowupRecordPage, add: addFollowupRecord, update: updateFollowupRecord, delete: deleteFollowupRecord, process: processFollowupRecord, finish: finishFollowupRecord }
const columns = [{ prop: 'recordNo', label: '随访编号' }, { prop: 'recordName', label: '慢病顾客' }, { prop: 'category', label: '随访类型' }, { prop: 'ownerName', label: '随访人员' }, { prop: 'planTime', label: '随访时间' }, { prop: 'status', label: '随访状态' }, { prop: 'remark', label: '随访说明' }]
const formFields = [{ prop: 'recordNo', label: '随访编号' }, { prop: 'recordName', label: '慢病顾客' }, { prop: 'category', label: '随访类型' }, { prop: 'ownerName', label: '随访人员' }, { prop: 'planTime', label: '随访时间' }, { prop: 'status', label: '随访状态', type: 'select' }, { prop: 'remark', label: '随访说明', type: 'textarea' }]
</script>
