<template><DataPage title="积分记录" description="积分编号、积分居民、积分来源、记录人员、积分时间和积分状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'RESIDENT', 'EXCHANGE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPointsLedger, deletePointsLedger, finishPointsLedger, getPointsLedgerPage, processPointsLedger, updatePointsLedger } from '../api'
const api = { page: getPointsLedgerPage, add: addPointsLedger, update: updatePointsLedger, delete: deletePointsLedger, process: processPointsLedger, finish: finishPointsLedger }
const columns = [{ prop: 'recordNo', label: '积分编号' }, { prop: 'recordName', label: '积分居民' }, { prop: 'category', label: '积分来源' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '积分时间' }, { prop: 'status', label: '积分状态' }, { prop: 'remark', label: '积分说明' }]
const formFields = [{ prop: 'recordNo', label: '积分编号' }, { prop: 'recordName', label: '积分居民' }, { prop: 'category', label: '积分来源' }, { prop: 'ownerName', label: '记录人员' }, { prop: 'planTime', label: '积分时间' }, { prop: 'status', label: '积分状态', type: 'select' }, { prop: 'remark', label: '积分说明', type: 'textarea' }]
</script>
