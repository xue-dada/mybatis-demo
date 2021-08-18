package com.example.mybatisdemo.service;

import com.example.mybatisdemo.global.BizException;
import com.example.mybatisdemo.mapper.ApiConfigMapper;
import com.example.mybatisdemo.mapper.NameSpaceMapper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLStatementBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceApp.class);
    @Qualifier("sqlSessionFactory")
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private ApiConfigMapper apiConfigMapper;

    public Map<String, Object> getData(Map<String, Object> map) {
        LOGGER.info("+++++++++++++++");
        // https://blog.csdn.net/kfyty725/article/details/106729240
        // https://blog.csdn.net/isea533/article/details/52368341

        if (map == null || map.get("apiId") == null) {
            throw new BizException("-1", "异常");
        }
        String apiId = (String) map.get("apiId");
        List<Map<String, Object>> mapList = apiConfigMapper.selectApiConfig(apiId);

        if (mapList == null) {
            throw new BizException("-1", "apiId 不存在");
        }

        String sqlStr = (String) mapList.get(0).get("sqlStr");
        String apiId1 = (String) mapList.get(0).get("apiId");
        String sqlId = (String) mapList.get(0).get("sqlId");

        String uniqueId = apiId1 + sqlId;

        // 根据apiId 查询 sqlStr
        String xml = "<select id=\"{0}\" resultType=\"map\">{1}</select>";
        xml = MessageFormat.format(xml, uniqueId, sqlStr);

        Configuration configuration = sqlSessionFactory.getConfiguration();
//        configuration.getMappedStatementNames();
        configuration.getMappedStatements().removeIf(po -> po.getId().endsWith("a_1"));

        if (!configuration.getMappedStatementNames().contains(uniqueId)) {

            MapperBuilderAssistant mapperBuilderAssistant = new MapperBuilderAssistant(configuration, xml);
            XNode xNode = new XPathParser(xml).evalNode("select");
            mapperBuilderAssistant.setCurrentNamespace(NameSpaceMapper.class.getName());

            XMLStatementBuilder xmlStatementBuilder =
                    new XMLStatementBuilder(configuration, mapperBuilderAssistant, xNode, configuration.getDatabaseId());
            xmlStatementBuilder.parseStatementNode();

            MappedStatement mappedStatement = configuration.getMappedStatement(NameSpaceMapper.class.getName() + ".a1");
//            mappedStatement.
        }
        List<Map<String, Object>> resultMapList = null;
        try(SqlSession sqlSession = sqlSessionFactory.openSession();) {
            resultMapList = sqlSession.selectList(uniqueId, map);

        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("dataList", resultMapList);
        return returnMap;

    }
}
