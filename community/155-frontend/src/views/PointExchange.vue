<template><DataPage title="积分兑换" description="兑换编号、兑换项目、兑换类型、申请人、申请时间和兑换状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPointExchange, deletePointExchange, finishPointExchange, getPointExchangePage, processPointExchange, updatePointExchange } from '../api'
const api = { page: getPointExchangePage, add: addPointExchange, update: updatePointExchange, delete: deletePointExchange, process: processPointExchange, finish: finishPointExchange }
const columns = [{ prop: 'recordNo', label: '兑换编号' }, { prop: 'recordName', label: '兑换项目' }, { prop: 'category', label: '兑换类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '兑换状态' }, { prop: 'remark', label: '兑换说明' }]
const formFields = [{ prop: 'recordNo', label: '兑换编号' }, { prop: 'recordName', label: '兑换项目' }, { prop: 'category', label: '兑换类型' }, { prop: 'ownerName', label: '申请人' }, { prop: 'planTime', label: '申请时间' }, { prop: 'status', label: '兑换状态', type: 'select' }, { prop: 'remark', label: '兑换说明', type: 'textarea' }]
</script>
