<template><DataPage title="接送授权" description="授权编号、授权儿童、授权类型、接送人员、有效时间和授权状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'CENTER', 'PARENT', 'SECURITY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPickupAuthorization, deletePickupAuthorization, finishPickupAuthorization, getPickupAuthorizationPage, processPickupAuthorization, updatePickupAuthorization } from '../api'
const api = { page: getPickupAuthorizationPage, add: addPickupAuthorization, update: updatePickupAuthorization, delete: deletePickupAuthorization, process: processPickupAuthorization, finish: finishPickupAuthorization }
const columns = [{ prop: 'recordNo', label: '授权编号' }, { prop: 'recordName', label: '授权儿童' }, { prop: 'category', label: '授权类型' }, { prop: 'ownerName', label: '接送人员' }, { prop: 'planTime', label: '有效时间' }, { prop: 'status', label: '授权状态' }, { prop: 'remark', label: '授权说明' }]
const formFields = [{ prop: 'recordNo', label: '授权编号' }, { prop: 'recordName', label: '授权儿童' }, { prop: 'category', label: '授权类型' }, { prop: 'ownerName', label: '接送人员' }, { prop: 'planTime', label: '有效时间' }, { prop: 'status', label: '授权状态', type: 'select' }, { prop: 'remark', label: '授权说明', type: 'textarea' }]
</script>
