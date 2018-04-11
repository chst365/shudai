package com.shudailaoshi.entity.${moduleName};

import javax.persistence.Table;
import com.shudailaoshi.entity.base.BaseEntity;
import com.shudailaoshi.pojo.annotation.Comment;
${imports}
/**
 * @createAuthor ${generatorName}
 * @createDate ${currentDate}
 * @modifyAuthor ${generatorName}
 * @modifyDate ${currentDate}
 */
@Table(name = "${tableName}")
public class ${entityName} extends BaseEntity {

	private static final long serialVersionUID = 1L;

	${efields}

	${getSets}

}