import request from '@/utils/request'

// 提交请假申请
export const applyLeave = (data) => {
  return request({
    url: '/leave/apply',
    method: 'post',
    data
  })
}

// 获取请假列表
export const getLeaveList = (params) => {
  return request({
    url: '/leave/list',
    method: 'get',
    params
  })
}

// 审批请假
export const approveLeave = (data) => {
  return request({
    url: '/leave/approve',
    method: 'post',
    data
  })
}

// 获取请假统计
export const getLeaveStatistics = () => {
  return request({
    url: '/leave/statistics',
    method: 'get'
  })
}

