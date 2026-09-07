<template>
  <DataPage title="运输轨迹" description="车辆定位、经纬度、速度和轨迹状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getGpsTrackPage, addGpsTrack, updateGpsTrack, deleteGpsTrack } from '../api'
const api = { page: getGpsTrackPage, add: addGpsTrack, update: updateGpsTrack, delete: deleteGpsTrack }
const columns = [{"prop": "trackNo", "label": "轨迹编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "vehicleNo", "label": "车辆"}, {"prop": "locationName", "label": "位置"}, {"prop": "longitudeText", "label": "经度"}, {"prop": "latitudeText", "label": "纬度"}, {"prop": "speedValue", "label": "速度"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "trackNo", "label": "轨迹编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "vehicleNo", "label": "车辆"}, {"prop": "locationName", "label": "位置"}, {"prop": "longitudeText", "label": "经度"}, {"prop": "latitudeText", "label": "纬度"}, {"prop": "speedValue", "label": "速度", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "DEVIATED", "value": "DEVIATED"}, {"label": "STOPPED", "value": "STOPPED"}]}]
const rowActions = []
const defaults = {"status": "NORMAL"}
const handleAction = () => {}
</script>
