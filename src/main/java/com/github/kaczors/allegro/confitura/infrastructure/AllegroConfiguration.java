package com.github.kaczors.allegro.confitura.infrastructure;

import com.github.kaczors.allegro.confitura.domain.AllegroWsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@ComponentScan("com.github.kaczors.allegro.confitura.domain")
public class AllegroConfiguration {

    @Value("${login}")
    String login;
    @Value("${password}")
    String password;
    @Value("${apiKey}")
    String apiKey;
    @Value("${uri}")
    String uri;
    @Value("${ns}")
    String namespace;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("pl.allegro");
        return marshaller;
    }

    @Bean
    public AllegroWsClient allegroWsClient(Jaxb2Marshaller marshaller) {
        AllegroWsClient client = new AllegroWsClient(login, password, apiKey, uri, namespace);
        client.setDefaultUri(namespace);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
