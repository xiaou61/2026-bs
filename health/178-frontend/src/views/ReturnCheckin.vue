<template><DataPage title="回收清点" description="清点编号、器械包名称、清点类型、回收人员、回收时间和清点状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORNURSE', 'CSSD', 'STERILE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReturnCheckin, deleteReturnCheckin, finishReturnCheckin, getReturnCheckinPage, processReturnCheckin, updateReturnCheckin } from '../api'
const api = { page: getReturnCheckinPage, add: addReturnCheckin, update: updateReturnCheckin, delete: deleteReturnCheckin, process: processReturnCheckin, finish: finishReturnCheckin }
const columns = [{ prop: 'recordNo', label: '清点编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '清点类型' }, { prop: 'ownerName', label: '回收人员' }, { prop: 'planTime', label: '回收时间' }, { prop: 'status', label: '清点状态' }, { prop: 'remark', label: '清点说明' }]
const formFields = [{ prop: 'recordNo', label: '清点编号' }, { prop: 'recordName', label: '器械包名称' }, { prop: 'category', label: '清点类型' }, { prop: 'ownerName', label: '回收人员' }, { prop: 'planTime', label: '回收时间' }, { prop: 'status', label: '清点状态', type: 'select' }, { prop: 'remark', label: '清点说明', type: 'textarea' }]
</script>
