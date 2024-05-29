/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.sql;

import java.util.ArrayList;
import java.util.List;

import jpype.JPypeContext;
import jpype.JPypeException;
import jpype.JPypeModule;

public class SqlCausalImpactExecutor {

    public static void execute(String sourceVariable, String targetVariable) {
        if (!JPypeContext.isStarted()) {
            String pythonPath = "/Users/ronnit/anaconda3/envs/ronnit_thesis/bin/python";
            String jvmPath = "/opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home/lib/server/libjvm.dylib";
            String[] jvmArgs = { "-Dpython.home=" + pythonPath, "-ea" };
            JPypeContext.start(jvmPath, jvmArgs);
        }

        try {
            JPypeModule causalImpactModule = JPypeContext.importModule("causal_impact");

            // Arguments for the python function
            List<Object> args = new ArrayList<>();
            args.add(sourceVariable);
            args.add(targetVariable);
            causalImpactModule.callAttr("compute_cate", args.toArray());

        } catch (JPypeException e) {
            e.printStackTrace();
        } finally {
            if (JPypeContext.isStarted()) {
                JPypeContext.stop();
            }
        }
    }

    public static void main(String[] args) {
        execute("sourceVariable", "targetVariable");
    }
}
