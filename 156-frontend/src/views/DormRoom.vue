<template><DataPage title="宿舍房间" description="房间编号、宿舍房间、所属楼栋、责任人、入住时间和房间状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'LOGISTICS', 'COUNSELOR', 'STUDENT']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addDormRoom, deleteDormRoom, finishDormRoom, getDormRoomPage, processDormRoom, updateDormRoom } from '../api'
const api = { page: getDormRoomPage, add: addDormRoom, update: updateDormRoom, delete: deleteDormRoom, process: processDormRoom, finish: finishDormRoom }
const columns = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '宿舍房间' }, { prop: 'category', label: '所属楼栋' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '入住时间' }, { prop: 'status', label: '房间状态' }, { prop: 'remark', label: '房间说明' }]
const formFields = [{ prop: 'recordNo', label: '房间编号' }, { prop: 'recordName', label: '宿舍房间' }, { prop: 'category', label: '所属楼栋' }, { prop: 'ownerName', label: '责任人' }, { prop: 'planTime', label: '入住时间' }, { prop: 'status', label: '房间状态', type: 'select' }, { prop: 'remark', label: '房间说明', type: 'textarea' }]
</script>
