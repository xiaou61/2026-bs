<template><DataPage title="课程签到" description="签到编号、课程排期、签到类型、记录人员、签到时间和签到状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WORKSHOP', 'TEACHER', 'VISITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addClassCheckin, deleteClassCheckin, finishClassCheckin, getClassCheckinPage, processClassCheckin, updateClassCheckin } from '../api'
const api = { page: getClassCheckinPage, add: addClassCheckin, update: updateClassCheckin, delete: deleteClassCheckin, process: processClassCheckin, finish: finishClassCheckin }
const columns = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '课程排期' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态' }, { prop: 'remark', label: '签到说明' }]
const formFields = [{ prop: 'recordNo', label: '签到编号' }, { prop: 'recordName', label: '课程排期' }, { prop: 'category', label: '签到类型' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '签到时间' }, { prop: 'status', label: '签到状态', type: 'select' }, { prop: 'remark', label: '签到说明', type: 'textarea' }]
</script>
