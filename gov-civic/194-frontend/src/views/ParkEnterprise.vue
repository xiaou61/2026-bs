<template><DataPage title="园区企业" description="企业编号、企业名称、行业类别、环保联系人、入驻时间和监管状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARK', 'ENTERPRISE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addParkEnterprise, deleteParkEnterprise, finishParkEnterprise, getParkEnterprisePage, processParkEnterprise, updateParkEnterprise } from '../api'
const api = { page: getParkEnterprisePage, add: addParkEnterprise, update: updateParkEnterprise, delete: deleteParkEnterprise, process: processParkEnterprise, finish: finishParkEnterprise }
const columns = [{ prop: 'recordNo', label: '企业编号' }, { prop: 'recordName', label: '企业名称' }, { prop: 'category', label: '行业类别' }, { prop: 'ownerName', label: '环保联系人' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '监管状态' }, { prop: 'remark', label: '企业说明' }]
const formFields = [{ prop: 'recordNo', label: '企业编号' }, { prop: 'recordName', label: '企业名称' }, { prop: 'category', label: '行业类别' }, { prop: 'ownerName', label: '环保联系人' }, { prop: 'planTime', label: '入驻时间' }, { prop: 'status', label: '监管状态', type: 'select' }, { prop: 'remark', label: '企业说明', type: 'textarea' }]
</script>
