package com.shudailaoshi.service.sys.impl;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shudailaoshi.dao.sys.ResourceDao;
import com.shudailaoshi.dao.sys.RoleDao;
import com.shudailaoshi.entity.enums.StatusEnum;
import com.shudailaoshi.entity.sys.Resource;
import com.shudailaoshi.pojo.annotation.RedisCacheMethod;
import com.shudailaoshi.pojo.constants.Constant;
import com.shudailaoshi.pojo.exceptions.ExceptionEnum;
import com.shudailaoshi.pojo.exceptions.ServiceException;
import com.shudailaoshi.pojo.vos.sys.PermitVO;
import com.shudailaoshi.service.base.impl.BaseServiceImpl;
import com.shudailaoshi.service.sys.ResourceService;
import com.shudailaoshi.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 *
 * @author Liaoyifan
 * @date 2016年8月6日
 *
 */
@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByResourceId", paramNames = { "id" })
	@Override
	public void updateResource(long id, Resource resource) {
		String text = resource.getResourceName().trim();
		Resource textParam = new Resource();
		textParam.setResourceName(text);
		Resource textResource = this.resourceDao.get(textParam);
		if (textResource != null && textResource.getId().longValue() != id) {
			throw new ServiceException(ExceptionEnum.TEXT_EXISTED);
		}
		String url = resource.getUrl().trim();
		Resource urlParam = new Resource();
		urlParam.setUrl(url);
		Resource urlResource = this.resourceDao.get(urlParam);
		if (urlResource != null && urlResource.getId().longValue() != id) {
			throw new ServiceException(ExceptionEnum.URL_EXISTED);
		}
		resource.setResourceName(text);
		resource.setUrl(url);
		resource.setModifyTime(DateUtil.getTime());
		this.resourceDao.updateByPrimaryKeySelective(resource);
	}

	@Override
	public void saveResource(Resource resource) {
		// String text = resource.getResourceName().trim();
		// Resource textParam = new Resource();
		// textParam.setResourceName(text);
		// if (this.resourceDao.getCount(textParam) > 0) {
		// throw new ServiceException(ExceptionEnum.TEXT_EXISTED);
		// }
		// resource.setResourceName(text);
		String url = resource.getUrl().trim();
		Resource urlParam = new Resource();
		urlParam.setUrl(url);
		if (this.resourceDao.getCount(urlParam) > 0) {
			throw new ServiceException(ExceptionEnum.URL_EXISTED);
		}
		resource.setUrl(url);
		long time = DateUtil.getTime();
		resource.setCreateTime(time);
		resource.setModifyTime(time);
		resource.setStatus(StatusEnum.NORMAL.getValue());
		this.resourceDao.save(resource);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByResourceId", paramNames = { "id" })
	@Override
	public boolean removeResource(long id) {
		Example example = new Example(Resource.class);
		example.createCriteria().andEqualTo("parentId", id);
		example.createCriteria().andNotEqualTo(Constant.STATE, StatusEnum.DELETE.getValue());
		if (this.resourceDao.getCountByExample(example) > 0) {
			throw new ServiceException(ExceptionEnum.HAS_CHILD_NODE);
		}
		try {
			this.resourceDao.removeByPrimaryKey(id);
			return true;
		} catch (Exception e) {
			throw new ServiceException(ExceptionEnum.USERD_NOT_ALLOW_DELETE);
		}
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByResourceId", paramNames = { "id" })
	@Override
	public void recover(long id) {
		Resource resource = new Resource(id);
		resource.setModifyTime(DateUtil.getTime());
		resource.setStatus(StatusEnum.NORMAL.getValue());
		this.resourceDao.updateByPrimaryKeySelective(resource);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByResourceId", paramNames = { "id" })
	@Override
	public void freeze(long id) {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setModifyTime(DateUtil.getTime());
		resource.setStatus(StatusEnum.FREEZE.getValue());
		this.resourceDao.updateByPrimaryKeySelective(resource);
	}

	@RedisCacheMethod(methodName = "deleteAuthorizationCacheByResourceId", paramNames = { "id" })
	@Override
	public void unfreeze(long id) {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setModifyTime(DateUtil.getTime());
		resource.setStatus(StatusEnum.NORMAL.getValue());
		this.resourceDao.updateByPrimaryKeySelective(resource);
	}

	@Override
	public Resource move(long id, long parentId) {
		Resource resource = this.resourceDao.getByPrimaryKey(id);
		resource.setParentId(parentId);
		resource.setModifyTime(DateUtil.getTime());
		this.resourceDao.updateByPrimaryKey(resource);
		return resource;
	}

	@Override
	public List<PermitVO> listPermit(Set<String> permissions) {
		return this.resourceDao.listPermit(permissions);
	}

	@Override
	public Resource listResource() {
		return this.getRoot(new Resource(Constant.ROOT), false);
	}

	@Override
	public Resource listMenu() {
		return this.getRoot(new Resource(0L), true);
	}

	@Override
	public Resource listGrant(long roleId) {
		if (this.roleDao.getByPrimaryKey(roleId).getRoleName().equals(Constant.ADMIN)) {
			throw new ServiceException(ExceptionEnum.ADMIN_NOT_NEED_GRANT);
		}
		Resource root = new Resource(Constant.ROOT);
		this.appendGrantNode(root, listGrant(), this.resourceDao.listIdByRoleId(roleId));
		return root;
	}

	/**
	 * 获取资源root
	 *
	 * @param root
	 * @param isMenu
	 * @return
	 */
	private Resource getRoot(Resource root, boolean isMenu) {
		if (SecurityUtils.getSubject().hasRole(Constant.ADMIN)) {
			this.appendNode(root, listResource(isMenu));
		} else {
			this.appendPermittedNode(root, listResource(isMenu));
		}
		return root;
	}

	/**
	 * 获取所有资源
	 *
	 * @param isMenu
	 * @return
	 */
	private List<Resource> listResource(boolean isMenu) {
		if (isMenu) {
			Example example = new Example(Resource.class);
			Criteria criteria = example.createCriteria();
			criteria.andNotEqualTo(Constant.STATE, StatusEnum.DELETE.getValue());
			criteria.andNotEqualTo(Constant.STATE, StatusEnum.FREEZE.getValue());
			criteria.andEqualTo("isMenu", true);
			return resourceDao.listByExample(example);
		} else {
			return resourceDao.listAll();
		}
	}

	/**
	 * 拼接所有资源树
	 *
	 * @param root
	 * @param resources
	 */
	private void appendNode(Resource root, List<Resource> resources) {
		for (Resource node : resources) {
			if (node.getParentId().longValue() == root.getId()) {
				root.getChildren().add(node);
				appendNode(node, resources);
			}
		}
	}

	/**
	 * 拼接权限资源树
	 *
	 * @param root
	 * @param resources
	 */
	private void appendPermittedNode(Resource root, List<Resource> resources) {
		Subject subject = SecurityUtils.getSubject();
		for (Resource node : resources) {
			if (node.getParentId().longValue() == root.getId()) {
				if (subject.isPermitted(node.getUrl())) {
					root.getChildren().add(node);
				}
				appendPermittedNode(node, resources);
			}
		}
	}

	/**
	 * 拼接角色授权资源树
	 *
	 * @param root
	 * @param resources
	 * @param roleResources
	 */
	private void appendGrantNode(Resource root, List<Resource> resources, List<Long> roleResources) {
		for (Resource node : resources) {
			if (node.getParentId().longValue() == root.getId()) {
				if (roleResources.contains(node.getId())) {
					node.setChecked(true);
				} else {
					node.setChecked(false);
				}
				root.getChildren().add(node);
				appendGrantNode(node, resources, roleResources);
			}
		}
	}

	/**
	 * 获取授权集合
	 *
	 * @return
	 */
	private List<Resource> listGrant() {
		Example example = new Example(Resource.class);
		Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo(Constant.STATE, StatusEnum.DELETE.getValue());
		criteria.andNotEqualTo(Constant.STATE, StatusEnum.FREEZE.getValue());
		return resourceDao.listByExample(example);
	}

}