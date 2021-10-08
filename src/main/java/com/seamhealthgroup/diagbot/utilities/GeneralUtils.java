package com.seamhealthgroup.diagbot.utilities;

import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seamhealthgroup.diagbot.configs.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeneralUtils {
	@Autowired
	ApplicationProperties appProperties;

	@SuppressWarnings("static-access")
	public String hmac256() throws IOException {
		String hmac = "";
		try {
			String uri = appProperties.getAuthService();
			String secretKey = appProperties.getSecretKey();

			Mac sha256_HMAC = Mac.getInstance("HmacMD5");
			SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacMD5");
			sha256_HMAC.init(secret_key);
			hmac = Base64.encodeBase64String(sha256_HMAC.doFinal(uri.getBytes()));

		} catch (Exception e) {
			log.error("ERROR OCCURRED WHILE DECRYPTING:::" + e);
		}

		return hmac;
	}
}