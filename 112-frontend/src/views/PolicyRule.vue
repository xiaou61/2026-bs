<template>
  <DataPage title="策略规则" description="策略条件、动作、优先级和状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPolicyRulePage, addPolicyRule, updatePolicyRule, deletePolicyRule } from '../api'
const api = { page: getPolicyRulePage, add: addPolicyRule, update: updatePolicyRule, delete: deletePolicyRule }
const columns = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "policyName", "label": "策略名称"}, {"prop": "conditionType", "label": "条件类型"}, {"prop": "actionType", "label": "动作"}, {"prop": "priority", "label": "优先级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "policyName", "label": "策略名称"}, {"prop": "conditionType", "label": "条件类型", "type": "select", "options": [{"label": "DEVICE", "value": "DEVICE"}, {"label": "MFA", "value": "MFA"}, {"label": "RISK", "value": "RISK"}, {"label": "LOCATION", "value": "LOCATION"}]}, {"prop": "actionType", "label": "动作", "type": "select", "options": [{"label": "ALLOW", "value": "ALLOW"}, {"label": "DENY", "value": "DENY"}, {"label": "MFA", "value": "MFA"}, {"label": "REVIEW", "value": "REVIEW"}]}, {"prop": "priority", "label": "优先级", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}]}]
const rowActions = []
const defaults = {"actionType": "ALLOW", "status": "ACTIVE"}
const handleAction = () => {}
</script>
