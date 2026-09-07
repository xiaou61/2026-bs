<template><DataPage title="备件台账" description="备件编号、备件名称、备件类型、仓储人员、入库时间和备件状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'REPAIR', 'WAREHOUSE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSparePartLedger, deleteSparePartLedger, finishSparePartLedger, getSparePartLedgerPage, processSparePartLedger, updateSparePartLedger } from '../api'
const api = { page: getSparePartLedgerPage, add: addSparePartLedger, update: updateSparePartLedger, delete: deleteSparePartLedger, process: processSparePartLedger, finish: finishSparePartLedger }
const columns = [{ prop: 'recordNo', label: '备件编号' }, { prop: 'recordName', label: '备件名称' }, { prop: 'category', label: '备件类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '备件状态' }, { prop: 'remark', label: '备件说明' }]
const formFields = [{ prop: 'recordNo', label: '备件编号' }, { prop: 'recordName', label: '备件名称' }, { prop: 'category', label: '备件类型' }, { prop: 'ownerName', label: '仓储人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '备件状态', type: 'select' }, { prop: 'remark', label: '备件说明', type: 'textarea' }]
</script>
