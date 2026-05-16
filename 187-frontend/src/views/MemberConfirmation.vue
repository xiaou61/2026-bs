<template><DataPage title="成员确认" description="确认编号、报名记录、确认类型、确认人员、确认时间和确认状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMemberConfirmation, deleteMemberConfirmation, finishMemberConfirmation, getMemberConfirmationPage, processMemberConfirmation, updateMemberConfirmation } from '../api'
const api = { page: getMemberConfirmationPage, add: addMemberConfirmation, update: updateMemberConfirmation, delete: deleteMemberConfirmation, process: processMemberConfirmation, finish: finishMemberConfirmation }
const columns = [{ prop: 'recordNo', label: '确认编号' }, { prop: 'recordName', label: '报名记录' }, { prop: 'category', label: '确认类型' }, { prop: 'ownerName', label: '确认人员' }, { prop: 'planTime', label: '确认时间' }, { prop: 'status', label: '确认状态' }, { prop: 'remark', label: '确认说明' }]
const formFields = [{ prop: 'recordNo', label: '确认编号' }, { prop: 'recordName', label: '报名记录' }, { prop: 'category', label: '确认类型' }, { prop: 'ownerName', label: '确认人员' }, { prop: 'planTime', label: '确认时间' }, { prop: 'status', label: '确认状态', type: 'select' }, { prop: 'remark', label: '确认说明', type: 'textarea' }]
</script>
