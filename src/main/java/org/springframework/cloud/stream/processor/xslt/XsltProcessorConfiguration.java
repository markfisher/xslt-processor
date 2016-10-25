/*
 * Copyright 2016 the original author or authors.
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

package org.springframework.cloud.stream.processor.xslt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.xml.transformer.XsltPayloadTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @author Mark Fisher
 */
@Configuration
@EnableBinding(Processor.class)
@EnableConfigurationProperties(XsltProcessorConfigurationProperties.class)
public class XsltProcessorConfiguration {

	@Autowired
	private XsltProcessorConfigurationProperties properties;

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Message<?> process(Message<?> input) {
		return transformer().transform(input);
	}

	@Bean
	@RefreshScope
	public XsltPayloadTransformer transformer() {
		return new XsltPayloadTransformer(properties.getStylesheet());
	}
}
