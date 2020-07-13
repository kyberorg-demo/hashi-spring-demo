package com.example.demo.vault.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;

import java.net.URI;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    /**
     * Specify an endpoint that was injected as URI.
     */
    @SneakyThrows
    @Override
    public VaultEndpoint vaultEndpoint() {
        String vaultUrl = getEnvironment().getProperty("vault.url", "");

        return VaultEndpoint.from(new URI(vaultUrl));
    }

    /**
     * Configure a client authentication.
     * Please consider a more secure authentication method
     * for production use.
     */
    @Override
    public ClientAuthentication clientAuthentication() {
        String token = getEnvironment().getProperty("vault.token", "");
        return new TokenAuthentication(token);
    }

    /**
     * Creates template object to operate with vault.
     *
     * @return configured {@link VaultTemplate} object.
     */
    @Override
    public VaultTemplate vaultTemplate() {
        return new VaultTemplate(vaultEndpoint(), clientAuthentication());
    }

    @Bean
    public VaultKeyValueOperations vaultOps() {

        return vaultTemplate()
                .opsForKeyValue("kv", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }

    @Bean
    @Qualifier("appName")
    public String applicationName() {
        return getEnvironment().getProperty("spring.application.name", "");
    }
}
