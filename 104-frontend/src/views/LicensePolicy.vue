<template>
  <DataPage title="许可证策略" description="许可证策略数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLicensePolicyPage, addLicensePolicy, updateLicensePolicy, deleteLicensePolicy } from '../api'
const api = { page: getLicensePolicyPage, add: addLicensePolicy, update: updateLicensePolicy, delete: deleteLicensePolicy }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'licenseName', label: '许可证' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'compatibleFlag', label: '兼容' }, { prop: 'commercialUseFlag', label: '商用' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'licenseName', label: '许可证' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'compatibleFlag', label: '兼容', type: 'number' }, { prop: 'commercialUseFlag', label: '商用', type: 'number' }, { prop: 'policyText', label: '策略说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
