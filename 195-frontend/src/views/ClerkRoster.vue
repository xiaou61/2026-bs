<template><DataPage title="人员排班" description="排班编号、窗口人员、排班类型、排班负责人、排班时间和排班状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'WINDOW']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClerkRoster, deleteClerkRoster, finishClerkRoster, getClerkRosterPage, processClerkRoster, updateClerkRoster } from '../api'
const api = { page: getClerkRosterPage, add: addClerkRoster, update: updateClerkRoster, delete: deleteClerkRoster, process: processClerkRoster, finish: finishClerkRoster }
const columns = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '窗口人员' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '排班负责人' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态' }, { prop: 'remark', label: '排班说明' }]
const formFields = [{ prop: 'recordNo', label: '排班编号' }, { prop: 'recordName', label: '窗口人员' }, { prop: 'category', label: '排班类型' }, { prop: 'ownerName', label: '排班负责人' }, { prop: 'planTime', label: '排班时间' }, { prop: 'status', label: '排班状态', type: 'select' }, { prop: 'remark', label: '排班说明', type: 'textarea' }]
</script>
