<template><DataPage title="阀门台账" description="阀门编号、阀门名称、阀门类型、维护人员、安装时间和阀门状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCH', 'PATROL', 'REPAIR', 'SUPERVISOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addValveLedger, deleteValveLedger, finishValveLedger, getValveLedgerPage, processValveLedger, updateValveLedger } from '../api'
const api = { page: getValveLedgerPage, add: addValveLedger, update: updateValveLedger, delete: deleteValveLedger, process: processValveLedger, finish: finishValveLedger }
const columns = [{ prop: 'recordNo', label: '阀门编号' }, { prop: 'recordName', label: '阀门名称' }, { prop: 'category', label: '阀门类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '安装时间' }, { prop: 'status', label: '阀门状态' }, { prop: 'remark', label: '阀门说明' }]
const formFields = [{ prop: 'recordNo', label: '阀门编号' }, { prop: 'recordName', label: '阀门名称' }, { prop: 'category', label: '阀门类型' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '安装时间' }, { prop: 'status', label: '阀门状态', type: 'select' }, { prop: 'remark', label: '阀门说明', type: 'textarea' }]
</script>
