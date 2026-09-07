<template><DataPage title="入库登记" description="入库编号、危废名称、来源企业、登记人员、入库时间和入库状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'WAREHOUSE', 'ENTERPRISE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addStorageInbound, deleteStorageInbound, finishStorageInbound, getStorageInboundPage, processStorageInbound, updateStorageInbound } from '../api'
const api = { page: getStorageInboundPage, add: addStorageInbound, update: updateStorageInbound, delete: deleteStorageInbound, process: processStorageInbound, finish: finishStorageInbound }
const columns = [{ prop: 'recordNo', label: '入库编号' }, { prop: 'recordName', label: '危废名称' }, { prop: 'category', label: '来源企业' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '入库状态' }, { prop: 'remark', label: '入库说明' }]
const formFields = [{ prop: 'recordNo', label: '入库编号' }, { prop: 'recordName', label: '危废名称' }, { prop: 'category', label: '来源企业' }, { prop: 'ownerName', label: '登记人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '入库状态', type: 'select' }, { prop: 'remark', label: '入库说明', type: 'textarea' }]
</script>
