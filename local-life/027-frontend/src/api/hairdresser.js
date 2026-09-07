import request from '@/utils/request'

/**
 * 理发师列表
 */
export function getHairdresserList(params) {
    return request({
        url: '/hairdresser/list',
        method: 'get',
        params
    })
}

/**
 * 理发师详情
 */
export function getHairdresserDetail(id) {
    return request({
        url: `/hairdresser/${id}`,
        method: 'get'
    })
}
