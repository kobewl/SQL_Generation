import { request } from '@/utils/request';
import type { ${interfaceName}, ${interfaceName}Query, ${interfaceName}CreateParams, ${interfaceName}UpdateParams } from './interface';

const BASE_URL = '/api/${tableName}';

/**
 * ${tableComment} 服务
 */
export class ${className}Service {
    /**
     * 创建${tableComment}
     */
    static async create(params: ${interfaceName}CreateParams): Promise<${interfaceName}> {
        return request.post(BASE_URL, params);
    }

    /**
     * 删除${tableComment}
     */
    static async delete(id: number): Promise<void> {
        return request.delete(`${r'${BASE_URL}'}/${r'${id}'}`);
    }

    /**
     * 更新${tableComment}
     */
    static async update(id: number, params: ${interfaceName}UpdateParams): Promise<${interfaceName}> {
        return request.put(`${r'${BASE_URL}'}/${r'${id}'}`, params);
    }

    /**
     * 获取${tableComment}详情
     */
    static async get(id: number): Promise<${interfaceName}> {
        return request.get(`${r'${BASE_URL}'}/${r'${id}'}`);
    }

    /**
     * 获取${tableComment}列表
     */
    static async list(params: ${interfaceName}Query): Promise<{
        data: ${interfaceName}[];
        total: number;
        pageSize: number;
        current: number;
    }> {
        return request.get(BASE_URL, { params });
    }
} 