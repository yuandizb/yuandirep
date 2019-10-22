import request from '@/utils/request'
import service from '@/api/config/const'
import { paramToString, like, update } from '@/utils/param'

const baseUrl = '/upmsRole'
/**
* 添加
* @param pojo
* @returns {*}
*/
export function add(pojo) {
return request.post(service.upms + baseUrl, paramToString(pojo))
}

/**
* 根据id查询
* @param id
* @returns {V|*}
*/
export function getById(id) {
return request.get(service.upms + baseUrl + '/' + id)
}

/**
* 条件查询
* @param pojo
* @param pageNum
* @param pageSize
* @param orderBy
* @returns {V|*}
*/
export function getByCondition(pojo, pageNum, pageSize, orderBy) {
const data = {}
// 拼接查询条件
if (pojo) {
getCondition(data, pojo, ['name', 'description'])
}
pageNum == null ? '' : data.pageNum = pageNum
pageSize == null ? '' : data.pageSize = pageSize
orderBy == null ? '' : data.orderBy = orderBy
return request.get(service.upms + baseUrl + '?' + paramToString(data))
}

/**
* 根据id删除多个
* @param ids
* @returns {boolean|*}
*/
export function deleteByIds(ids) {
const data = {
ids: ids
}
return request.delete(service.upms + baseUrl + '?' + paramToString(data))
}

/**
* 更新数据
* @param pojo
* @returns {*}
*/
export function updateSelectiveById(pojo) {
const data = {}
update(data, pojo)
return request.put(service.upms + baseUrl + '/' + pojo.id, paramToString(data))
}
