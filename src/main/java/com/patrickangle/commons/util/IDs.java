/*
 * Patrick Angle Commons Library
 * Copyright 2018 Patrick Angle
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrickangle.commons.util;

import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Patrick Angle
 */
public class IDs {
    public static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";
    
    private static final HashMap<String, Integer> sequentialIDMap = new HashMap<>();
    public static int sequentialID(String context) {
        if (!sequentialIDMap.containsKey(context)) {
            sequentialIDMap.put(context, 0);
        }
        sequentialIDMap.put(context, sequentialIDMap.get(context) + 1);
        return sequentialIDMap.get(context);
    }
    
    public static UUID uuid() {
        return UUID.randomUUID();
    }
    
    public static String uuidString() {
        return uuid().toString();
    }
}
