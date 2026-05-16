<template><DataPage title="续费缴费" description="缴费编号、月租合同、缴费类型、财务人员、缴费时间和缴费状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'FINANCE', 'TENANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRenewalPayment, deleteRenewalPayment, finishRenewalPayment, getRenewalPaymentPage, processRenewalPayment, updateRenewalPayment } from '../api'
const api = { page: getRenewalPaymentPage, add: addRenewalPayment, update: updateRenewalPayment, delete: deleteRenewalPayment, process: processRenewalPayment, finish: finishRenewalPayment }
const columns = [{ prop: 'recordNo', label: '缴费编号' }, { prop: 'recordName', label: '月租合同' }, { prop: 'category', label: '缴费类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '缴费时间' }, { prop: 'status', label: '缴费状态' }, { prop: 'remark', label: '缴费说明' }]
const formFields = [{ prop: 'recordNo', label: '缴费编号' }, { prop: 'recordName', label: '月租合同' }, { prop: 'category', label: '缴费类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '缴费时间' }, { prop: 'status', label: '缴费状态', type: 'select' }, { prop: 'remark', label: '缴费说明', type: 'textarea' }]
</script>
