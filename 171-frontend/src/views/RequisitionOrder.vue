<template><DataPage title="申领工单" description="工单编号、申领物资、申领单位、申请人员、申请时间和工单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'APPLICANT', 'DISPATCH', 'AUDITOR', 'WAREHOUSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addRequisitionOrder, deleteRequisitionOrder, finishRequisitionOrder, getRequisitionOrderPage, processRequisitionOrder, updateRequisitionOrder } from '../api'
const api = { page: getRequisitionOrderPage, add: addRequisitionOrder, update: updateRequisitionOrder, delete: deleteRequisitionOrder, process: processRequisitionOrder, finish: finishRequisitionOrder }
const columns = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '申领物资' }, { prop: 'category', label: '申领单位' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '工单状态' }, { prop: 'remark', label: '工单说明' }]
const formFields = [{ prop: 'recordNo', label: '工单编号' }, { prop: 'recordName', label: '申领物资' }, { prop: 'category', label: '申领单位' }, { prop: 'ownerName', label: '申请人员' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '工单状态', type: 'select' }, { prop: 'remark', label: '工单说明', type: 'textarea' }]
</script>
