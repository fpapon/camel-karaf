/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.blueprint;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.camel.core.xml.AbstractCamelContextFactoryBean;
import org.apache.camel.util.blueprint.SSLContextParametersFactoryBean;
import org.apache.camel.xml.jaxb.DefaultModelJAXBContextFactory;

public class BlueprintModelJAXBContextFactory extends DefaultModelJAXBContextFactory {

    private final ClassLoader classLoader;

    public BlueprintModelJAXBContextFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    protected ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    protected String getPackages() {
        // we nedd to have a class from each different package with jaxb models
        // and we must use the .class for the classloader to work in OSGi
        Set<Class<?>> classes = new LinkedHashSet<>();
        classes.add(CamelContextFactoryBean.class);
        classes.add(AbstractCamelContextFactoryBean.class);
        classes.add(SSLContextParametersFactoryBean.class);
        classes.add(org.apache.camel.ExchangePattern.class);
        classes.add(org.apache.camel.model.RouteDefinition.class);
        classes.add(org.apache.camel.model.config.StreamResequencerConfig.class);
        classes.add(org.apache.camel.model.dataformat.DataFormatsDefinition.class);
        classes.add(org.apache.camel.model.language.ExpressionDefinition.class);
        classes.add(org.apache.camel.model.loadbalancer.RoundRobinLoadBalancerDefinition.class);
        classes.add(org.apache.camel.model.rest.RestDefinition.class);
        classes.add(org.apache.camel.model.cloud.ServiceCallDefinition.class);

        StringBuilder packages = new StringBuilder();
        for (Class<?> cl : classes) {
            if (packages.length() > 0) {
                packages.append(":");
            }
            packages.append(cl.getName(), 0, cl.getName().lastIndexOf('.'));
        }
        return packages.toString();
    }

}