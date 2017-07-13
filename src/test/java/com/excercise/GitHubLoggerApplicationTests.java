package com.excercise;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.excercise.security.Authenticator;
import com.excercise.security.GitHubMessageValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubLoggerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testValidSignature() {
		String payload = "{\"action\": \"opened\",\"issue\": {\"url\": \"https://api.github.com/repos/Phil/repo1/issues/3\",\"user\": {\"login\": \"Phil11601\",\"id\": 18272263,\"site_admin\": false}}}";
		String signature = "sha1=bd4a0809b2e0e3db91afa57999b84069ca4ab52d";

		Authenticator gitHubMessageValidator = new GitHubMessageValidator(signature, payload);
		assertTrue(gitHubMessageValidator.authorized());

	}

	@Test
	public void testInValidSignature() {
		String payload = "{\"action\": \"opened\",\"issue\": {\"url\": \"https://api.github.com/repos/Phil/repo1/issues/3\",\"user\": {\"login\": \"Phil11601\",\"id\": 18272263,\"site_admin\": false}}}";
		String signature = "sha1=bd4a0809b2e0e3db91afa57999b84069ca4ab52x";

		Authenticator gitHubMessageValidator = new GitHubMessageValidator(signature, payload);
		assertFalse(gitHubMessageValidator.authorized());

	}
}
