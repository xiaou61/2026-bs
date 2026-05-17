<template><DataPage title="租借订单" description="订单编号、点位名称、租借类型、下单用户、下单时间和订单状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'FINANCE', 'MERCHANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addLeaseOrder, deleteLeaseOrder, finishLeaseOrder, getLeaseOrderPage, processLeaseOrder, updateLeaseOrder } from '../api'
const api = { page: getLeaseOrderPage, add: addLeaseOrder, update: updateLeaseOrder, delete: deleteLeaseOrder, process: processLeaseOrder, finish: finishLeaseOrder }
const columns = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '租借类型' }, { prop: 'ownerName', label: '下单用户' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态' }, { prop: 'remark', label: '订单说明' }]
const formFields = [{ prop: 'recordNo', label: '订单编号' }, { prop: 'recordName', label: '点位名称' }, { prop: 'category', label: '租借类型' }, { prop: 'ownerName', label: '下单用户' }, { prop: 'planTime', label: '下单时间' }, { prop: 'status', label: '订单状态', type: 'select' }, { prop: 'remark', label: '订单说明', type: 'textarea' }]
</script>
