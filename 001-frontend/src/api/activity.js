import request from '@/utils/request'

// 添加活动
export const addActivity = (data) => {
  return request({
    url: '/activity/add',
    method: 'post',
    data
  })
}

// 获取活动列表
export const getActivityList = (params) => {
  return request({
    url: '/activity/list',
    method: 'get',
    params
  })
}

// 获取活动详情
export const getActivityDetail = (id) => {
  return request({
    url: `/activity/${id}`,
    method: 'get'
  })
}

// 报名活动
export const signupActivity = (data) => {
  return request({
    url: '/activity/signup',
    method: 'post',
    data
  })
}

// 取消报名
export const cancelSignup = (data) => {
  return request({
    url: '/activity/cancel-signup',
    method: 'post',
    data
  })
}

// 获取活动报名列表
export const getActivitySignups = (id) => {
  return request({
    url: `/activity/${id}/signups`,
    method: 'get'
  })
}

// 更新活动
export const updateActivity = (data) => {
  return request({
    url: '/activity/update',
    method: 'put',
    data
  })
}

// 删除活动
export const deleteActivity = (id) => {
  return request({
    url: `/activity/${id}`,
    method: 'delete'
  })
}

