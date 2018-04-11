package com.shudailaoshi.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 树节点-工具类
 * 
 * @author liu
 *
 */
public class TreeUtil {

	/**
	 * 将ns转为树结构并拼接到node上
	 * 
	 * @param node
	 *            某个节点
	 * @param ns
	 *            节点雷彪
	 */
	public static void appendChildren(BaseTree node, List<? extends BaseTree> ns) {
		if (ns == null) {
			return;
		}
		for (BaseTree n : ns) {
			if (node.getId() == n.getParentId() || node.getId().equals(n.getParentId())) {
				node.addChild(n);
				appendChildren(n, ns);
			}
		}
	}

	/**
	 * 将指定节点下的所有子节转换为List<TreeNode>
	 * 
	 * @param node
	 */
	public static void copy2List(List<BaseTree> target, BaseTree source) {
		if (source == null) {
			return;
		}
		List<BaseTree> children = source.getChildren();
		for (BaseTree c : children) {
			target.add(c);
			if (c.getChildren() != null) {
				copy2List(target, c);
			}
		}
	}

	/**
	 * 查找source下标识为id的节点
	 * 
	 * @param source
	 * @param id
	 * @return
	 */
	public static BaseTree findChildNodeById(BaseTree source, String id) {
		if (source == null || id == null) {
			return null;
		}
		List<BaseTree> tns = new ArrayList<BaseTree>();
		copy2List(tns, source);
		for (BaseTree n : tns) {
			if (n.getId().equals(id)) {
				return n;
			}
		}
		return null;

	}

	/**
	 * 查找给定root下id为childNodeId的所有子节点的ID（包含自身childNodeId）
	 * 
	 * @param node
	 * @param nodeId
	 * @return
	 */
	public static List<String> listAllChildrenIds(BaseTree root, String childNodeId) {
		List<String> ids = new ArrayList<String>();
		List<BaseTree> tns = new ArrayList<BaseTree>();
		BaseTree currentNode = findChildNodeById(root, childNodeId);
		copy2List(tns, currentNode);
		for (BaseTree n : tns) {
			ids.add(n.getId());
		}
		ids.add(childNodeId);
		return ids;
	}

	public static void main(String[] args) throws JSONException {
		// String
		// json=\"[{\\"children\\":[],\\"id\\":\\"117\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"审核经理\\"},{\\"children\\":[],\\"id\\":\\"119\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"总经理\\"},{\\"children\\":[],\\"id\\":\\"0\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"SUPER_ADMI\\"},{\\"children\\":[],\\"id\\":\\"121\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"副总经理\\"},{\\"children\\":[],\\"id\\":\\"106\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"报价员\\"},{\\"children\\":[],\\"id\\":\\"123\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"0005\\"},{\\"children\\":[],\\"id\\":\\"108\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"核价员\\"},{\\"children\\":[],\\"id\\":\\"110\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"定损员\\"},{\\"children\\":[],\\"id\\":\\"112\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"品管\\"},{\\"children\\":[],\\"id\\":\\"114\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"业务人员\\"},{\\"children\\":[],\\"id\\":\\"116\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"估损员\\"},{\\"children\\":[],\\"id\\":\\"118\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"部门主管\\"},{\\"children\\":[],\\"id\\":\\"120\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"财务主管\\"},{\\"children\\":[],\\"id\\":\\"105\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"案件主管\\"},{\\"children\\":[],\\"id\\":\\"122\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"财务会计\\"},{\\"children\\":[],\\"id\\":\\"107\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"施救员\\"},{\\"children\\":[],\\"id\\":\\"124\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"大案组\\"},{\\"children\\":[],\\"id\\":\\"109\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"人事经理\\"},{\\"children\\":[],\\"id\\":\\"111\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"查勘员\\"},{\\"children\\":[],\\"id\\":\\"113\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"接单员\\"},{\\"children\\":[],\\"id\\":\\"115\\",\\"parentId\\":\\"-1\\",\\"text\\":\\"员工\\"},{\\"children\\":[],\\"id\\":\\"0\\",\\"parentId\\":\\"0\\",\\"text\\":\\"系统管理员\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"0\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"105\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST001\\",\\"parentId\\":\\"105\\",\\"text\\":\\"曹操\\"},{\\"children\\":[],\\"id\\":\\"JM001\\",\\"parentId\\":\\"105\\",\\"text\\":\\"子云\\"},{\\"children\\":[],\\"id\\":\\"ST006\\",\\"parentId\\":\\"106\\",\\"text\\":\\"张辽\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"106\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"107\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST002\\",\\"parentId\\":\\"107\\",\\"text\\":\\"夏侯惇\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"108\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST004\\",\\"parentId\\":\\"108\\",\\"text\\":\\"李典\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"109\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST150\\",\\"parentId\\":\\"109\\",\\"text\\":\\"舒畅\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"110\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST005\\",\\"parentId\\":\\"110\\",\\"text\\":\\"徐晃\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"111\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST003\\",\\"parentId\\":\\"111\\",\\"text\\":\\"夏侯渊\\"},{\\"children\\":[],\\"id\\":\\"JM001\\",\\"parentId\\":\\"111\\",\\"text\\":\\"子云\\"},{\\"children\\":[],\\"id\\":\\"TE001\\",\\"parentId\\":\\"111\\",\\"text\\":\\"TE001\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"112\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST007\\",\\"parentId\\":\\"112\\",\\"text\\":\\"曹洪\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"113\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST008\\",\\"parentId\\":\\"113\\",\\"text\\":\\"郭嘉\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"114\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"115\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST300\\",\\"parentId\\":\\"115\\",\\"text\\":\\"李菲\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"116\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST009\\",\\"parentId\\":\\"116\\",\\"text\\":\\"典韦\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"117\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST200\\",\\"parentId\\":\\"117\\",\\"text\\":\\"林雅美\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"118\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"ST001\\",\\"parentId\\":\\"118\\",\\"text\\":\\"曹操\\"},{\\"children\\":[],\\"id\\":\\"ST100\\",\\"parentId\\":\\"119\\",\\"text\\":\\"王宝强\\"},{\\"children\\":[],\\"id\\":\\"ST010\\",\\"parentId\\":\\"120\\",\\"text\\":\\"许褚\\"},{\\"children\\":[],\\"id\\":\\"ST011\\",\\"parentId\\":\\"121\\",\\"text\\":\\"曹仁\\"},{\\"children\\":[],\\"id\\":\\"ST010\\",\\"parentId\\":\\"122\\",\\"text\\":\\"许褚\\"},{\\"children\\":[],\\"id\\":\\"0005\\",\\"parentId\\":\\"124\\",\\"text\\":\\"name\\"},{\\"children\\":[],\\"id\\":\\"JM001\\",\\"parentId\\":\\"124\\",\\"text\\":\\"子云\\"}]\";
		// Gson g=new Gson();
		// Type type = new TypeToken<ArrayList<TreeNode>>() {}.getType();
		// List<TreeNode> tl=g.fromJson(json, type );
		// System.out.println(tl.size());
		// TreeNode root=new TreeNode();
		// root.setId(\"-1\");
		// appendChildren(root,tl);
		// System.out.println(g.toJson(root));
		String json = "{\"children\":[{\"children\":[{\"children\":[],\"hierarchy\":0,\"id\":\"c3\",\"leaf\":false,\"parentId\":\"c1\",\"text\":\"上海徐家汇公司\"},{\"children\":[],\"hierarchy\":0,\"id\":\"c4\",\"leaf\":false,\"parentId\":\"c1\",\"text\":\"上海浦东公司\"},{\"children\":[],\"hierarchy\":0,\"id\":\"d1\",\"leaf\":false,\"parentId\":\"c1\",\"text\":\"IT部\"},{\"children\":[{\"children\":[],\"hierarchy\":0,\"id\":\"d3\",\"leaf\":false,\"parentId\":\"d2\",\"text\":\"经理室\"},{\"children\":[],\"hierarchy\":0,\"id\":\"d4\",\"leaf\":false,\"parentId\":\"d2\",\"text\":\"会计室\"}],\"hierarchy\":0,\"id\":\"d2\",\"leaf\":false,\"parentId\":\"c1\",\"text\":\"财务部\"}],\"hierarchy\":0,\"id\":\"c1\",\"leaf\":false,\"parentId\":\"0\",\"text\":\"上海公司\"},{\"children\":[],\"hierarchy\":0,\"id\":\"c2\",\"leaf\":false,\"parentId\":\"0\",\"text\":\"江苏公司\"}],\"hierarchy\":0,\"id\":\"0\",\"leaf\":false}";
		TreeNode root = (TreeNode) JSONObject.parseObject(json, TreeNode.class);
		List<String> ids = listAllChildrenIds(root, "c1");
		for (String s : ids) {
			System.out.print(s + ",");
		}

	}
}
