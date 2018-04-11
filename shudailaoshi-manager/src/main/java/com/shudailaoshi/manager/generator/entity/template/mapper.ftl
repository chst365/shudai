<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.shudailaoshi.dao.${moduleName}.${entityName}Dao">

 	<resultMap id="${packageName}Map" type="${entityName}">
	    ${results}
	</resultMap>

	<sql id="listWhere">
		<where>
			<if test="${packageName}Query!=null">
				${wheres}
				<if test="@com.shudailaoshi.utils.ValidUtil@isNotBlank(${packageName}Query.startDate)">
					and FROM_UNIXTIME(create_time/1000,'%Y-%m-%d') <![CDATA[>=]]> ${startDate}
				</if>
				<if test="@com.shudailaoshi.utils.ValidUtil@isNotBlank(${packageName}Query.endDate)">
					and FROM_UNIXTIME(create_time/1000,'%Y-%m-%d') <![CDATA[<=]]> ${endDate}
				</if>
			</if>
		</where>
	</sql>

	<select id="list${entityName}" resultMap="${packageName}Map">
		select *
		from ${tableName}
		<include refid="listWhere" />
		<if test="${packageName}Query.sort!=null">
		order by ${@com.shudailaoshi.utils.StringUtil@camelhumpToUnderline(${packageName}Query.sort.property)} ${${packageName}Query.sort.direction}
		</if>
		<if test="start!=null and limit!=null">
			limit ${limit}
		</if>
	</select>

	<select id="count${entityName}" resultType="java.lang.Long">
		select count(*) from ${tableName}
		<include refid="listWhere" />
	</select>

</mapper>