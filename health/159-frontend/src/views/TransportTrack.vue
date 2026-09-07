<template><DataPage title="运输轨迹" description="轨迹编号、转运车辆、轨迹类型、驾驶员、定位时间和轨迹状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'COLLECTOR', 'TRANSPORTER', 'REGULATOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addTransportTrack, deleteTransportTrack, finishTransportTrack, getTransportTrackPage, processTransportTrack, updateTransportTrack } from '../api'
const api = { page: getTransportTrackPage, add: addTransportTrack, update: updateTransportTrack, delete: deleteTransportTrack, process: processTransportTrack, finish: finishTransportTrack }
const columns = [{ prop: 'recordNo', label: '轨迹编号' }, { prop: 'recordName', label: '转运车辆' }, { prop: 'category', label: '轨迹类型' }, { prop: 'ownerName', label: '驾驶员' }, { prop: 'planTime', label: '定位时间' }, { prop: 'status', label: '轨迹状态' }, { prop: 'remark', label: '轨迹说明' }]
const formFields = [{ prop: 'recordNo', label: '轨迹编号' }, { prop: 'recordName', label: '转运车辆' }, { prop: 'category', label: '轨迹类型' }, { prop: 'ownerName', label: '驾驶员' }, { prop: 'planTime', label: '定位时间' }, { prop: 'status', label: '轨迹状态', type: 'select' }, { prop: 'remark', label: '轨迹说明', type: 'textarea' }]
</script>
