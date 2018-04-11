package com.shudailaoshi.service.sys;

import java.util.List;
import java.util.Set;

import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.pojo.vos.sys.PermitVO;
import com.shudailaoshi.service.base.BaseService;

/**
 * ResourceService
 * 
 * @author Liaoyifan
 * @date 2016年8月21日
 */
public interface ResourceService extends BaseService<Resource> {

	/**
	 * 获取菜单树
	 * 
	 * @return
	 */
	Resource listMenu();

	/**
	 * 获取权限vo
	 * 
	 * @param permissions
	 * @return
	 */
	List<PermitVO> listPermit(Set<String> permissions);

	/**
	 * 获取资源树
	 * 
	 * @return
	 */
	Resource listResource();

	/**
	 * 保存资源
	 * 
	 * @param resource
	 * @return
	 */
	void saveResource(Resource resource);

	/**
	 * 编辑资源
	 * 
	 * @return
	 */
	void updateResource(long id, Resource resource);

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	boolean removeResource(long id);

	/**
	 * 恢复资源
	 * 
	 * @param id
	 */
	void recover(long id);

	/**
	 * 冻结资源
	 * 
	 * @param id
	 */
	void freeze(long id);

	/**
	 * 解冻资源
	 * 
	 * @param id
	 */
	void unfreeze(long id);

	/**
	 * 移动资源
	 * 
	 * @param id
	 * @param parentId
	 * @return
	 */
	Resource move(long id, long parentId);

	/**
	 * 获取授权树
	 * 
	 * @param roleId
	 * @return
	 */
	Resource listGrant(long roleId);

}
