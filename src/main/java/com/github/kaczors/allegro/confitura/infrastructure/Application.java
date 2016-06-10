/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kaczors.allegro.confitura.infrastructure;

import com.github.kaczors.allegro.confitura.domain.AllegroService;
import com.github.kaczors.allegro.confitura.domain.AllegroWsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    AllegroWsClient allegroWsClient;

    @Autowired
    AllegroService allegroService;

    @Override
    public void run(String... args) {
        String welcome = "    _    _     _     _____ ____ ____   ___  \n" +
                "   / \\  | |   | |   | ____/ ___|  _ \\ / _ \\ \n" +
                "  / _ \\ | |   | |   |  _|| |  _| |_) | | | |\n" +
                " / ___ \\| |___| |___| |__| |_| |  _ <| |_| |\n" +
                "/_/   \\_|_____|_____|_____\\____|_| \\_\\\\___/";

        System.out.println(welcome);
        //TODO
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
