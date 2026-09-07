<template><DataPage title="承运商档案" description="承运商编号、承运商名称、业务类型、联系人、合作时间和合作状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'DISPATCHER', 'CARRIER']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCarrierCompany, deleteCarrierCompany, finishCarrierCompany, getCarrierCompanyPage, processCarrierCompany, updateCarrierCompany } from '../api'
const api = { page: getCarrierCompanyPage, add: addCarrierCompany, update: updateCarrierCompany, delete: deleteCarrierCompany, process: processCarrierCompany, finish: finishCarrierCompany }
const columns = [{ prop: 'recordNo', label: '承运商编号' }, { prop: 'recordName', label: '承运商名称' }, { prop: 'category', label: '业务类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '合作状态' }, { prop: 'remark', label: '承运商说明' }]
const formFields = [{ prop: 'recordNo', label: '承运商编号' }, { prop: 'recordName', label: '承运商名称' }, { prop: 'category', label: '业务类型' }, { prop: 'ownerName', label: '联系人' }, { prop: 'planTime', label: '合作时间' }, { prop: 'status', label: '合作状态', type: 'select' }, { prop: 'remark', label: '承运商说明', type: 'textarea' }]
</script>
