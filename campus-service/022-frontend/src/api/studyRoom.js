import request from '@/utils/request'

export function getStudyRooms() {
  return request({
    url: '/room/list',
    method: 'get'
  })
}

export function getStudyRoomPage(params) {
  return request({
    url: '/room/page',
    method: 'get',
    params
  })
}

export function getRoomDetail(roomId) {
  return request({
    url: `/room/${roomId}`,
    method: 'get'
  })
}

export function createStudyRoom(data) {
  return request({
    url: '/room',
    method: 'post',
    data
  })
}

export function updateStudyRoom(roomId, data) {
  return request({
    url: `/room/${roomId}`,
    method: 'put',
    data
  })
}

export function updateStudyRoomStatus(roomId, status) {
  return request({
    url: `/room/${roomId}/status`,
    method: 'put',
    params: { status }
  })
}

export function deleteStudyRoom(roomId) {
  return request({
    url: `/room/${roomId}`,
    method: 'delete'
  })
}
