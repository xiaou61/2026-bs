<template><DataPage title="能耗账单" description="账单编号、账单名称、费用类型、核算人、账期时间和账单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'ENERGY', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addConsumptionBill, deleteConsumptionBill, finishConsumptionBill, getConsumptionBillPage, processConsumptionBill, updateConsumptionBill } from '../api'
const api = { page: getConsumptionBillPage, add: addConsumptionBill, update: updateConsumptionBill, delete: deleteConsumptionBill, process: processConsumptionBill, finish: finishConsumptionBill }
const columns = [{ prop: 'recordNo', label: '账单编号' }, { prop: 'recordName', label: '账单名称' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '核算人' }, { prop: 'planTime', label: '账期时间' }, { prop: 'status', label: '账单状态' }, { prop: 'remark', label: '账单说明' }]
const formFields = [{ prop: 'recordNo', label: '账单编号' }, { prop: 'recordName', label: '账单名称' }, { prop: 'category', label: '费用类型' }, { prop: 'ownerName', label: '核算人' }, { prop: 'planTime', label: '账期时间' }, { prop: 'status', label: '账单状态', type: 'select' }, { prop: 'remark', label: '账单说明', type: 'textarea' }]
</script>
