<template><DataPage title="组织关系" description="转接编号、转接对象、转接类型、经办人、申请时间和转接状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addOrganizationTransfer, deleteOrganizationTransfer, finishOrganizationTransfer, getOrganizationTransferPage, processOrganizationTransfer, updateOrganizationTransfer } from '../api'
const api = { page: getOrganizationTransferPage, add: addOrganizationTransfer, update: updateOrganizationTransfer, delete: deleteOrganizationTransfer, process: processOrganizationTransfer, finish: finishOrganizationTransfer }
const columns = [{ prop: 'recordNo', label: '转接编号' }, { prop: 'recordName', label: '转接对象' }, { prop: 'category', label: '转接类型' }, { prop: 'ownerName', label: '经办人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '转接状态' }, { prop: 'remark', label: '转接说明' }]
const formFields = [{ prop: 'recordNo', label: '转接编号' }, { prop: 'recordName', label: '转接对象' }, { prop: 'category', label: '转接类型' }, { prop: 'ownerName', label: '经办人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '转接状态', type: 'select' }, { prop: 'remark', label: '转接说明', type: 'textarea' }]
</script>
