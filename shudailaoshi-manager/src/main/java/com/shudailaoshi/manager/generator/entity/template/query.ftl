package com.shudailaoshi.pojo.query.${moduleName};

import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.pojo.query.base.BaseQuery;
${imports}
/**
 * @createAuthor ${generatorName}
 * @createDate ${currentDate}
 * @modifyAuthor ${generatorName}
 * @modifyDate ${currentDate}
 */
public class ${entityName}Query extends BaseQuery {

	private static final long serialVersionUID = 1L;

	${qfields}

	${getSets}

}