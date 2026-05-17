<template><DataPage title="充电宝档案" description="设备编号、所属机柜、电池规格、维护人员、入库时间和设备状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'OPERATOR', 'SITE', 'INSPECTOR']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addPowerBankDevice, deletePowerBankDevice, finishPowerBankDevice, getPowerBankDevicePage, processPowerBankDevice, updatePowerBankDevice } from '../api'
const api = { page: getPowerBankDevicePage, add: addPowerBankDevice, update: updatePowerBankDevice, delete: deletePowerBankDevice, process: processPowerBankDevice, finish: finishPowerBankDevice }
const columns = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '所属机柜' }, { prop: 'category', label: '电池规格' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '设备状态' }, { prop: 'remark', label: '设备说明' }]
const formFields = [{ prop: 'recordNo', label: '设备编号' }, { prop: 'recordName', label: '所属机柜' }, { prop: 'category', label: '电池规格' }, { prop: 'ownerName', label: '维护人员' }, { prop: 'planTime', label: '入库时间' }, { prop: 'status', label: '设备状态', type: 'select' }, { prop: 'remark', label: '设备说明', type: 'textarea' }]
</script>
