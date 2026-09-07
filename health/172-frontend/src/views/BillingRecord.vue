<template><DataPage title="费用结算" description="结算编号、结算患者、结算类型、收费人员、结算时间和结算状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'RECEPTION', 'DENTIST', 'CASHIER', 'PATIENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBillingRecord, deleteBillingRecord, finishBillingRecord, getBillingRecordPage, processBillingRecord, updateBillingRecord } from '../api'
const api = { page: getBillingRecordPage, add: addBillingRecord, update: updateBillingRecord, delete: deleteBillingRecord, process: processBillingRecord, finish: finishBillingRecord }
const columns = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '结算患者' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '收费人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态' }, { prop: 'remark', label: '结算说明' }]
const formFields = [{ prop: 'recordNo', label: '结算编号' }, { prop: 'recordName', label: '结算患者' }, { prop: 'category', label: '结算类型' }, { prop: 'ownerName', label: '收费人员' }, { prop: 'planTime', label: '结算时间' }, { prop: 'status', label: '结算状态', type: 'select' }, { prop: 'remark', label: '结算说明', type: 'textarea' }]
</script>
