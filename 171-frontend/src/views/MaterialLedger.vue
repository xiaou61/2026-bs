<template><DataPage title="物资台账" description="物资编号、物资名称、物资类别、管理人员、建档时间和物资状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'WAREHOUSE', 'CHECKER', 'DISPATCH', 'APPLICANT', 'AUDITOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addMaterialLedger, deleteMaterialLedger, finishMaterialLedger, getMaterialLedgerPage, processMaterialLedger, updateMaterialLedger } from '../api'
const api = { page: getMaterialLedgerPage, add: addMaterialLedger, update: updateMaterialLedger, delete: deleteMaterialLedger, process: processMaterialLedger, finish: finishMaterialLedger }
const columns = [{ prop: 'recordNo', label: '物资编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '物资类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '物资状态' }, { prop: 'remark', label: '物资说明' }]
const formFields = [{ prop: 'recordNo', label: '物资编号' }, { prop: 'recordName', label: '物资名称' }, { prop: 'category', label: '物资类别' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '建档时间' }, { prop: 'status', label: '物资状态', type: 'select' }, { prop: 'remark', label: '物资说明', type: 'textarea' }]
</script>
