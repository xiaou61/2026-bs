<template><DataPage title="费用分摊" description="分摊编号、费用项目、分摊类型、财务人员、分摊时间和分摊状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'FINANCE', 'LEADER', 'PARTICIPANT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCostShare, deleteCostShare, finishCostShare, getCostSharePage, processCostShare, updateCostShare } from '../api'
const api = { page: getCostSharePage, add: addCostShare, update: updateCostShare, delete: deleteCostShare, process: processCostShare, finish: finishCostShare }
const columns = [{ prop: 'recordNo', label: '分摊编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '分摊类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '分摊时间' }, { prop: 'status', label: '分摊状态' }, { prop: 'remark', label: '分摊说明' }]
const formFields = [{ prop: 'recordNo', label: '分摊编号' }, { prop: 'recordName', label: '费用项目' }, { prop: 'category', label: '分摊类型' }, { prop: 'ownerName', label: '财务人员' }, { prop: 'planTime', label: '分摊时间' }, { prop: 'status', label: '分摊状态', type: 'select' }, { prop: 'remark', label: '分摊说明', type: 'textarea' }]
</script>
