package base;

import Utilities.POJO.Builder;
import Utilities.POJO.response.GET_SigninWithValidCredentials;
import Utilities.POJO.response.POST_signUpWithValidCredentials;
import Utilities.envPicker.Environment;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;

import static Utilities.ReUsableUtils.getBookStoreRequestBody;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseClass {
	public static Environment envProperties;
	protected String env="";
	protected Builder requestBody = new Builder();
	public static String authToken;

	@BeforeSuite()
	@Parameters({"environment"})
	@SneakyThrows
	public void initEnvironmentProperties(String envFromTestNG) {
		env=envFromTestNG;
		if((env== null || env.isEmpty()) && System.getProperty("environment") != null) {
			env = System.getProperty("environment");
			// env="PROD";
		}
		String propFilesLocation = "\\src\\main\\resources\\environment\\";
		File propertiesFile = new File(System.getProperty("user.dir") + propFilesLocation + envFromTestNG + ".properties");
		ConfigFactory.setProperty("file", String.valueOf(propertiesFile.toURI()));
		envProperties = ConfigFactory.create(Environment.class);
		env=envFromTestNG;
		authToken = globalSignUP();

	}

	@SneakyThrows
	public String globalSignUP()
	{
		Response signUP=
                given().log().all()
						.baseUri(envProperties.BookStore_BaseURI())
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "globalUser")).
                when()
                        .post("/signup").
                then().log().all()
                        .extract()
                        .response();

				assertThat(signUP.getStatusCode()).isIn(200,400);
				if(signUP.statusCode()==400)
				{
					assertThat(signUP.jsonPath().getString("detail")).contains("Email already registered");
				}

		GET_SigninWithValidCredentials response =
				given().log().all()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "globalUser")).
                when()
                        .post("/login").
                then().log().all()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(GET_SigninWithValidCredentials.class);
				return response.getAccess_token();

	}


	protected final RequestSpecification bookStoreRequestSpec() {
		return new RequestSpecBuilder()
					.setBaseUri(envProperties.BookStore_BaseURI())
					.addHeader("accept", "*/*")
					.addHeader("Content-Type", "application/json")
					.build();
	}

	protected final RequestSpecification bookStoreRequestSpec_Authenticated() {
		return new RequestSpecBuilder()
					.setBaseUri(envProperties.BookStore_BaseURI())
					.setAuth(oauth2(authToken))
					.addHeader("accept", "*/*")
					.addHeader("Content-Type", "application/json")
					.build();
	}

}

