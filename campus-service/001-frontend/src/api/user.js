import request from '@/utils/request'

// 获取用户列表
export const getUserList = (params) => {
  return request({
    url: '/user/list',
    method: 'get',
    params
  })
}

// 添加用户
export const addUser = (data) => {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

// 更新用户信息
export const updateUser = (data) => {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return request({
    url: `/user/${id}`,
    method: 'delete'
  })
}

