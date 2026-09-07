<template><DataPage title="证书台账" description="台账编号、持证人员、证书等级、管理人员、登记时间和台账状态维护" :api="api" :columns="columns" :form-fields="formFields" :manage-roles="['ADMIN', 'TRAINING', 'CERTIFIER', 'TRAINEE']" /></template>
<script setup>
import DataPage from '../components/DataPage.vue'
import { addCertificateLedger, deleteCertificateLedger, finishCertificateLedger, getCertificateLedgerPage, processCertificateLedger, updateCertificateLedger } from '../api'
const api = { page: getCertificateLedgerPage, add: addCertificateLedger, update: updateCertificateLedger, delete: deleteCertificateLedger, process: processCertificateLedger, finish: finishCertificateLedger }
const columns = [{ prop: 'recordNo', label: '台账编号' }, { prop: 'recordName', label: '持证人员' }, { prop: 'category', label: '证书等级' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '台账状态' }, { prop: 'remark', label: '台账说明' }]
const formFields = [{ prop: 'recordNo', label: '台账编号' }, { prop: 'recordName', label: '持证人员' }, { prop: 'category', label: '证书等级' }, { prop: 'ownerName', label: '管理人员' }, { prop: 'planTime', label: '登记时间' }, { prop: 'status', label: '台账状态', type: 'select' }, { prop: 'remark', label: '台账说明', type: 'textarea' }]
</script>
