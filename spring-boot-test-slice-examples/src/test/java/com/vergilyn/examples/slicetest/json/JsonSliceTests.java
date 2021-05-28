package com.vergilyn.examples.slicetest.json;

import com.vergilyn.examples.slicetest.json.app.ExampleBasicObject;
import com.vergilyn.examples.slicetest.json.app.ExampleCustomObject;
import com.vergilyn.examples.slicetest.json.app.ExampleJsonObjectWithView;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author vergilyn
 * @since 2021-05-28
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/2.2.11.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-tests">
 *          Auto-configured Tests</a>
 * @see <a href="https://github.com/spring-projects/spring-boot/tree/v2.2.11.RELEASE/spring-boot-project/spring-boot-test-autoconfigure/src/test/java/org/springframework/boot/test/autoconfigure/json">
 *          github, `@JsonTest` examples</a>
 */
@JsonTest
@ContextConfiguration(classes = JsonSliceApplication.class)
public class JsonSliceTests {
	@Autowired
	private BasicJsonTester basicJson;

	@Autowired
	private JacksonTester<ExampleBasicObject> jacksonBasicJson;

	@Autowired
	private JacksonTester<ExampleJsonObjectWithView> jacksonWithViewJson;

	@Autowired
	private JacksonTester<ExampleCustomObject> jacksonCustomJson;

	@Autowired(required = false)
	private GsonTester<ExampleBasicObject> gsonJson;

	@Autowired(required = false)
	private JsonbTester<ExampleBasicObject> jsonbJson;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void basicJson() {
		assertThat(this.basicJson.from("{\"a\":\"b\"}")).hasJsonPathStringValue("@.a");
	}

	@Test
	void jacksonBasic() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.jacksonBasicJson.write(object)).isEqualToJson("example.json");

		System.out.println(applicationContext);
	}

	@Test
	void jacksonCustom() throws Exception {
		ExampleCustomObject object = new ExampleCustomObject("spring");
		assertThat(this.jacksonCustomJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void gson() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.gsonJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void jsonb() throws Exception {
		ExampleBasicObject object = new ExampleBasicObject();
		object.setValue("spring");
		assertThat(this.jsonbJson.write(object)).isEqualToJson("example.json");
	}

	@Test
	void customView() throws Exception {
		ExampleJsonObjectWithView object = new ExampleJsonObjectWithView();
		object.setValue("spring");
		JsonContent<ExampleJsonObjectWithView> content = this.jacksonWithViewJson
				.forView(ExampleJsonObjectWithView.TestView.class).write(object);
		assertThat(content).doesNotHaveJsonPathValue("id");
		assertThat(content).isEqualToJson("example.json");
	}
}
