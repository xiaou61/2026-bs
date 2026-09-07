<template><DataPage title="归还流转" description="流转编号、归还图书、流转类型、交接人员、归还时间和流转状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LIBRARY', 'CURATOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addReturnExchange, deleteReturnExchange, finishReturnExchange, getReturnExchangePage, processReturnExchange, updateReturnExchange } from '../api'
const api = { page: getReturnExchangePage, add: addReturnExchange, update: updateReturnExchange, delete: deleteReturnExchange, process: processReturnExchange, finish: finishReturnExchange }
const columns = [{ prop: 'recordNo', label: '流转编号' }, { prop: 'recordName', label: '归还图书' }, { prop: 'category', label: '流转类型' }, { prop: 'ownerName', label: '交接人员' }, { prop: 'planTime', label: '归还时间' }, { prop: 'status', label: '流转状态' }, { prop: 'remark', label: '流转说明' }]
const formFields = [{ prop: 'recordNo', label: '流转编号' }, { prop: 'recordName', label: '归还图书' }, { prop: 'category', label: '流转类型' }, { prop: 'ownerName', label: '交接人员' }, { prop: 'planTime', label: '归还时间' }, { prop: 'status', label: '流转状态', type: 'select' }, { prop: 'remark', label: '流转说明', type: 'textarea' }]
</script>
