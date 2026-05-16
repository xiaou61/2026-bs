<template><DataPage title="门禁联动" description="联动编号、联动门禁、联动类型、联动人员、触发时间和联动状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'SECURITY', 'MANAGER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addAccessLinkage, deleteAccessLinkage, finishAccessLinkage, getAccessLinkagePage, processAccessLinkage, updateAccessLinkage } from '../api'
const api = { page: getAccessLinkagePage, add: addAccessLinkage, update: updateAccessLinkage, delete: deleteAccessLinkage, process: processAccessLinkage, finish: finishAccessLinkage }
const columns = [{ prop: 'recordNo', label: '联动编号' }, { prop: 'recordName', label: '联动门禁' }, { prop: 'category', label: '联动类型' }, { prop: 'ownerName', label: '联动人员' }, { prop: 'planTime', label: '触发时间' }, { prop: 'status', label: '联动状态' }, { prop: 'remark', label: '联动说明' }]
const formFields = [{ prop: 'recordNo', label: '联动编号' }, { prop: 'recordName', label: '联动门禁' }, { prop: 'category', label: '联动类型' }, { prop: 'ownerName', label: '联动人员' }, { prop: 'planTime', label: '触发时间' }, { prop: 'status', label: '联动状态', type: 'select' }, { prop: 'remark', label: '联动说明', type: 'textarea' }]
</script>
