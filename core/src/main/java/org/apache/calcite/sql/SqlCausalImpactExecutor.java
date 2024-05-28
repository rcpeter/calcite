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

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Uses Executor to pass arguments to python file for execution.
 */
public final class SqlCausalImpactExecutor {

  private SqlCausalImpactExecutor() {
    // Private constructor to prevent instantiation
  }

  /**
   * Executes the causal impact analysis using the given SQL statement.
   *
   * @param sqlCausalImpact the SQL statement representing the causal impact analysis
   * @return the result of the analysis as a String
   * @throws Exception if an error occurs during execution
   */
  public static Object executeCausalImpact(SqlCausalImpact sqlCausalImpact) throws Exception {
    String sourceVariable = sqlCausalImpact.getSourceVariable().toString();
    String targetVariable = sqlCausalImpact.getTargetVariable().toString();

    String[] command = new String[] {
        "python", "python/causal_impact_executor.py", sourceVariable, targetVariable
    };

    // Execute the py script
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    Process process = processBuilder.start();

    // Read the output from the python script
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    StringBuilder result = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      result.append(line);
    }

    // Wait for the process to complete
    process.waitFor();

    // Return the result from the python script
    return result.toString();
  }
}
