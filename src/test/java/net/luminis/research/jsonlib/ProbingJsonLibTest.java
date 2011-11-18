package net.luminis.research.jsonlib;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProbingJsonLibTest {
	private static final String EXPECTED = "{\"name\":\"test\"}";

	@Test
	public void mapToJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "test");

		JSONObject jsonObject = JSONObject.fromMap(map);

		assertEquals(EXPECTED, jsonObject.toString());
	}

	@Test
	public void beanToJson() {
		ExampleBean bean = new ExampleBean("test");

		JSONObject jsonObject = JSONObject.fromBean(bean);

		assertEquals(EXPECTED, jsonObject.toString());
	}

	@Test
	public void JsonToMap() {
		JSONObject jsonObject = JSONObject.fromString(EXPECTED);

		assertEquals("test", jsonObject.get("name"));
	}

	@Test
	public void JsonToBean() {
		JSONObject jsonObject = JSONObject.fromString(EXPECTED);

		ExampleBean bean = (ExampleBean) JSONObject.toBean(jsonObject, ExampleBean.class);

		assertEquals(new ExampleBean("test"), bean);
	}
}