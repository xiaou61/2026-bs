<template><DataPage title="家长通知" description="通知编号、通知对象、通知类型、发送人员、发送时间和通知状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SCHOOL', 'DISPATCH', 'TEACHER', 'GUARDIAN']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addParentNotice, deleteParentNotice, finishParentNotice, getParentNoticePage, processParentNotice, updateParentNotice } from '../api'
const api = { page: getParentNoticePage, add: addParentNotice, update: updateParentNotice, delete: deleteParentNotice, process: processParentNotice, finish: finishParentNotice }
const columns = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '通知状态' }, { prop: 'remark', label: '通知说明' }]
const formFields = [{ prop: 'recordNo', label: '通知编号' }, { prop: 'recordName', label: '通知对象' }, { prop: 'category', label: '通知类型' }, { prop: 'ownerName', label: '发送人员' }, { prop: 'planTime', label: '发送时间' }, { prop: 'status', label: '通知状态', type: 'select' }, { prop: 'remark', label: '通知说明', type: 'textarea' }]
</script>
