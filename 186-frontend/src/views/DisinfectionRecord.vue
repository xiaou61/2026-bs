<template><DataPage title="消毒记录" description="消毒编号、后厨区域、消毒类型、执行人员、消毒时间和消毒状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CANTEEN', 'CHEF', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDisinfectionRecord, deleteDisinfectionRecord, finishDisinfectionRecord, getDisinfectionRecordPage, processDisinfectionRecord, updateDisinfectionRecord } from '../api'
const api = { page: getDisinfectionRecordPage, add: addDisinfectionRecord, update: updateDisinfectionRecord, delete: deleteDisinfectionRecord, process: processDisinfectionRecord, finish: finishDisinfectionRecord }
const columns = [{ prop: 'recordNo', label: '消毒编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '消毒类型' }, { prop: 'ownerName', label: '执行人员' }, { prop: 'planTime', label: '消毒时间' }, { prop: 'status', label: '消毒状态' }, { prop: 'remark', label: '消毒说明' }]
const formFields = [{ prop: 'recordNo', label: '消毒编号' }, { prop: 'recordName', label: '后厨区域' }, { prop: 'category', label: '消毒类型' }, { prop: 'ownerName', label: '执行人员' }, { prop: 'planTime', label: '消毒时间' }, { prop: 'status', label: '消毒状态', type: 'select' }, { prop: 'remark', label: '消毒说明', type: 'textarea' }]
</script>
