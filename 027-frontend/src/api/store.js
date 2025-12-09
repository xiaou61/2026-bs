import request from '@/utils/request'

/**
 * 门店列表
 */
export function getStoreList(params) {
    return request({
        url: '/store/list',
        method: 'get',
        params
    })
}

/**
 * 门店详情
 */
export function getStoreDetail(id) {
    return request({
        url: `/store/${id}`,
        method: 'get'
    })
}

/**
 * 门店的理发师列表
 */
export function getStoreHairdressers(id) {
    return request({
        url: `/store/${id}/hairdressers`,
        method: 'get'
    })
}

/**
 * 门店的服务项目列表
 */
export function getStoreServices(id) {
    return request({
        url: `/store/${id}/services`,
        method: 'get'
    })
}
