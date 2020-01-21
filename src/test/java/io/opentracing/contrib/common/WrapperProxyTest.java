/* Copyright 2020 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentracing.contrib.common;

import static io.opentracing.contrib.common.InterfacesTest.*;
import static org.junit.Assert.*;

import org.junit.Test;

import io.opentracing.contrib.common.WrapperProxy;
import io.opentracing.contrib.common.InterfacesTest.A;
import io.opentracing.contrib.common.InterfacesTest.B;

@SuppressWarnings("all")
public class WrapperProxyTest {
  @Test
  public void testFoo() {
    final B proxyB = WrapperProxy.wrap(foo, b);
    assertTrue(proxyB instanceof A);
    proxyB.a();
    proxyB.b();

    final A proxyA = WrapperProxy.wrap(foo, a);
    assertTrue(proxyA instanceof B);
    proxyA.a();
    ((B)proxyA).b();
  }
}