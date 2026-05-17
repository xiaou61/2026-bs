<template><DataPage title="购药记录" description="购药编号、顾客姓名、药品名称、经办人员、购药时间和购药状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PHARMACY', 'CLERK', 'CUSTOMER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPurchaseRecord, deletePurchaseRecord, finishPurchaseRecord, getPurchaseRecordPage, processPurchaseRecord, updatePurchaseRecord } from '../api'
const api = { page: getPurchaseRecordPage, add: addPurchaseRecord, update: updatePurchaseRecord, delete: deletePurchaseRecord, process: processPurchaseRecord, finish: finishPurchaseRecord }
const columns = [{ prop: 'recordNo', label: '购药编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '药品名称' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '购药时间' }, { prop: 'status', label: '购药状态' }, { prop: 'remark', label: '购药说明' }]
const formFields = [{ prop: 'recordNo', label: '购药编号' }, { prop: 'recordName', label: '顾客姓名' }, { prop: 'category', label: '药品名称' }, { prop: 'ownerName', label: '经办人员' }, { prop: 'planTime', label: '购药时间' }, { prop: 'status', label: '购药状态', type: 'select' }, { prop: 'remark', label: '购药说明', type: 'textarea' }]
</script>
