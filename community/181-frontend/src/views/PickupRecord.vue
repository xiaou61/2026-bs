<template><DataPage title="接送记录" description="接送编号、托管儿童、接送类型、接送人员、接送时间和接送状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'TEACHER', 'PARENT', 'SECURITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPickupRecord, deletePickupRecord, finishPickupRecord, getPickupRecordPage, processPickupRecord, updatePickupRecord } from '../api'
const api = { page: getPickupRecordPage, add: addPickupRecord, update: updatePickupRecord, delete: deletePickupRecord, process: processPickupRecord, finish: finishPickupRecord }
const columns = [{ prop: 'recordNo', label: '接送编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '接送类型' }, { prop: 'ownerName', label: '接送人员' }, { prop: 'planTime', label: '接送时间' }, { prop: 'status', label: '接送状态' }, { prop: 'remark', label: '接送说明' }]
const formFields = [{ prop: 'recordNo', label: '接送编号' }, { prop: 'recordName', label: '托管儿童' }, { prop: 'category', label: '接送类型' }, { prop: 'ownerName', label: '接送人员' }, { prop: 'planTime', label: '接送时间' }, { prop: 'status', label: '接送状态', type: 'select' }, { prop: 'remark', label: '接送说明', type: 'textarea' }]
</script>
