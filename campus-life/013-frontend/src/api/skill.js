import request from '@/utils/request'

export function publishSkill(data) {
  return request({
    url: '/skill/publish',
    method: 'post',
    data
  })
}

export function getSkillList(params) {
  return request({
    url: '/skill/list',
    method: 'get',
    params
  })
}

export function getSkillDetail(id) {
  return request({
    url: `/skill/${id}`,
    method: 'get'
  })
}

export function updateSkill(id, data) {
  return request({
    url: `/skill/${id}`,
    method: 'put',
    data
  })
}

export function deleteSkill(id) {
  return request({
    url: `/skill/${id}`,
    method: 'delete'
  })
}

export function getMySkill(params) {
  return request({
    url: '/skill/my',
    method: 'get',
    params
  })
}

export function searchSkill(params) {
  return request({
    url: '/skill/search',
    method: 'get',
    params
  })
}

