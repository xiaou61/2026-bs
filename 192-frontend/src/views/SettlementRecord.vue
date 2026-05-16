<template><DataPage title="评价结算" description="结算编号、服务记录、结算类型、结算人员、结算时间和结算状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COORDINATOR', 'FINANCE', 'FAMILY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSettlementRecord, deleteSettlementRecord, finishSettlementRecord, getSettlementRecordPage, processSettlementRecord, updateSettlementRecord } from '../api'
const api = { page: getSettlementRecordPage, add: addSettlementRecord, update: updateSettlementRecord, delete: deleteSettlementRecord, process: processSettlementRecord, finish: finishSettlementRecord }
const columns = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '服务记录' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '结算人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态' }, { prop: 'remark', label: '结算说明' }]
const formFields = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '服务记录' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '结算人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态', type: 'select' }, { prop: 'remark', label: '结算说明', type: 'textarea' }]
</script>
