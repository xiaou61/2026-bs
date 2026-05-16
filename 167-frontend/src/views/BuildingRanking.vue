<template><DataPage title="楼栋排名" description="排名编号、排名楼栋、排名周期、统计人员、统计时间和排名状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COMMUNITY', 'SUPERVISOR', 'VOLUNTEER', 'RESIDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addBuildingRanking, deleteBuildingRanking, finishBuildingRanking, getBuildingRankingPage, processBuildingRanking, updateBuildingRanking } from '../api'
const api = { page: getBuildingRankingPage, add: addBuildingRanking, update: updateBuildingRanking, delete: deleteBuildingRanking, process: processBuildingRanking, finish: finishBuildingRanking }
const columns = [{ prop: 'recordNo', label: '排名编号' }, { prop: 'recordName', label: '排名楼栋' }, { prop: 'category', label: '排名周期' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '排名状态' }, { prop: 'remark', label: '排名说明' }]
const formFields = [{ prop: 'recordNo', label: '排名编号' }, { prop: 'recordName', label: '排名楼栋' }, { prop: 'category', label: '排名周期' }, { prop: 'ownerName', label: '统计人员' }, { prop: 'planTime', label: '统计时间' }, { prop: 'status', label: '排名状态', type: 'select' }, { prop: 'remark', label: '排名说明', type: 'textarea' }]
</script>
