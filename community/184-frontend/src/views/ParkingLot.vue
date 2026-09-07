<template><DataPage title="停车场档案" description="场库编号、场库名称、区域类型、负责人员、启用时间和场库状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'PARKING', 'PATROL']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addParkingLot, deleteParkingLot, finishParkingLot, getParkingLotPage, processParkingLot, updateParkingLot } from '../api'
const api = { page: getParkingLotPage, add: addParkingLot, update: updateParkingLot, delete: deleteParkingLot, process: processParkingLot, finish: finishParkingLot }
const columns = [{ prop: 'recordNo', label: '场库编号' }, { prop: 'recordName', label: '场库名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '场库状态' }, { prop: 'remark', label: '场库说明' }]
const formFields = [{ prop: 'recordNo', label: '场库编号' }, { prop: 'recordName', label: '场库名称' }, { prop: 'category', label: '区域类型' }, { prop: 'ownerName', label: '负责人员' }, { prop: 'planTime', label: '启用时间' }, { prop: 'status', label: '场库状态', type: 'select' }, { prop: 'remark', label: '场库说明', type: 'textarea' }]
</script>
