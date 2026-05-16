<template><DataPage title="主播排班" description="排班编号、排班主播、排班类型、运营人员、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'BASE', 'ANCHOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAnchorSchedule, deleteAnchorSchedule, finishAnchorSchedule, getAnchorSchedulePage, processAnchorSchedule, updateAnchorSchedule } from '../api'
const api = { page: getAnchorSchedulePage, add: addAnchorSchedule, update: updateAnchorSchedule, delete: deleteAnchorSchedule, process: processAnchorSchedule, finish: finishAnchorSchedule }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '排班主播' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '排班主播' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '运营人员' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>
