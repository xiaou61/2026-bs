<template><DataPage title="党组织维护" description="组织编号、组织名称、组织类型、负责人、成立时间和组织状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPartyBranch, deletePartyBranch, finishPartyBranch, getPartyBranchPage, processPartyBranch, updatePartyBranch } from '../api'
const api = { page: getPartyBranchPage, add: addPartyBranch, update: updatePartyBranch, delete: deletePartyBranch, process: processPartyBranch, finish: finishPartyBranch }
const columns = [{ prop: 'recordNo', label: '组织编号' }, { prop: 'recordName', label: '组织名称' }, { prop: 'category', label: '组织类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '成立时间' }, { prop: 'status', label: '组织状态' }, { prop: 'remark', label: '组织说明' }]
const formFields = [{ prop: 'recordNo', label: '组织编号' }, { prop: 'recordName', label: '组织名称' }, { prop: 'category', label: '组织类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'planTime', label: '成立时间' }, { prop: 'status', label: '组织状态', type: 'select' }, { prop: 'remark', label: '组织说明', type: 'textarea' }]
</script>
