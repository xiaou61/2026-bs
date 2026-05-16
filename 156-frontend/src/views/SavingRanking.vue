<template><DataPage title="节能排行" description="排行编号、排行名称、排行类型、统计人、统计周期和发布状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'COUNSELOR', 'ENERGY', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addSavingRanking, deleteSavingRanking, finishSavingRanking, getSavingRankingPage, processSavingRanking, updateSavingRanking } from '../api'
const api = { page: getSavingRankingPage, add: addSavingRanking, update: updateSavingRanking, delete: deleteSavingRanking, process: processSavingRanking, finish: finishSavingRanking }
const columns = [{ prop: 'recordNo', label: '排行编号' }, { prop: 'recordName', label: '排行名称' }, { prop: 'category', label: '排行类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '发布状态' }, { prop: 'remark', label: '排行说明' }]
const formFields = [{ prop: 'recordNo', label: '排行编号' }, { prop: 'recordName', label: '排行名称' }, { prop: 'category', label: '排行类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '发布状态', type: 'select' }, { prop: 'remark', label: '排行说明', type: 'textarea' }]
</script>
