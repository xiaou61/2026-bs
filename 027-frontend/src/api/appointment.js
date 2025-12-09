import request from '@/utils/request'

/**
 * 创建预约
 */
export function createAppointment(data) {
    return request({
        url: '/appointment/create',
        method: 'post',
        data
    })
}

/**
 * 我的预约列表
 */
export function getMyAppointments(params) {
    return request({
        url: '/appointment/my-list',
        method: 'get',
        params
    })
}

/**
 * 预约详情
 */
export function getAppointmentDetail(id) {
    return request({
        url: `/appointment/${id}`,
        method: 'get'
    })
}

/**
 * 取消预约
 */
export function cancelAppointment(id, reason) {
    return request({
        url: `/appointment/${id}/cancel`,
        method: 'put',
        data: { reason }
    })
}

/**
 * 检查时段是否可预约
 */
export function checkTimeSlotAvailable(hairdresserId, date, time) {
    return request({
        url: '/appointment/check-available',
        method: 'get',
        params: { hairdresserId, date, time }
    })
}

/**
 * 获取可预约时段
 */
export function getAvailableTimeSlots(hairdresserId, date) {
    return request({
        url: '/appointment/available-times',
        method: 'get',
        params: { hairdresserId, date }
    })
}
