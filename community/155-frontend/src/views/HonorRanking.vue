<template><DataPage title="榜单统计" description="榜单编号、榜单名称、榜单类型、统计人、统计周期和发布状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'SECRETARY', 'ORGANIZER', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addHonorRanking, deleteHonorRanking, finishHonorRanking, getHonorRankingPage, processHonorRanking, updateHonorRanking } from '../api'
const api = { page: getHonorRankingPage, add: addHonorRanking, update: updateHonorRanking, delete: deleteHonorRanking, process: processHonorRanking, finish: finishHonorRanking }
const columns = [{ prop: 'recordNo', label: '榜单编号' }, { prop: 'recordName', label: '榜单名称' }, { prop: 'category', label: '榜单类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '发布状态' }, { prop: 'remark', label: '榜单说明' }]
const formFields = [{ prop: 'recordNo', label: '榜单编号' }, { prop: 'recordName', label: '榜单名称' }, { prop: 'category', label: '榜单类型' }, { prop: 'ownerName', label: '统计人' }, { prop: 'planTime', label: '统计周期' }, { prop: 'status', label: '发布状态', type: 'select' }, { prop: 'remark', label: '榜单说明', type: 'textarea' }]
</script>
