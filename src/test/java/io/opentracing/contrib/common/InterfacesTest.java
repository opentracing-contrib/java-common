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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import io.opentracing.contrib.common.Interfaces;

@SuppressWarnings("all")
public class InterfacesTest {
 private static final Comparator<Class<?>> comparator = new Comparator<Class<?>>() {
   @Override
   public int compare(final Class<?> o1, final Class<?> o2) {
     return o1.getName().compareTo(o2.getName());
   }
 };

 private static void testGetAllInterfaces(final Object obj, final Class<?> ... expecteds) {
   final Class<?>[] ifaces = Interfaces.getAllInterfaces(obj.getClass());
   Arrays.sort(ifaces, comparator);
   Arrays.sort(expecteds, comparator);
   assertEquals(Arrays.toString(ifaces), Arrays.toString(expecteds));
   final List<Class<?>> list = Arrays.asList(ifaces);
   for (final Class<?> expected : expecteds)
     assertTrue(list.contains(expected));
 }

 public interface A {
   void a();
 }

 public interface B extends A {
   void b();
 }

 public interface C extends B {
 }

 public interface D {
 }

 public static class Bar implements C, D {
   @Override
   public void b() {
   }

   @Override
   public void a() {
   }
 }

 public static class Foo extends Bar implements B {
 }

 public static final Foo foo = new Foo();

 public static final Bar bar = new Bar();

 public static final A a = new A() {
   @Override
   public void a() {
   }
 };

 public static final B b = new B() {
   @Override
   public void a() {
   }

   @Override
   public void b() {
   }
 };

 public static final C c = new C() {
   @Override
   public void a() {
   }

   @Override
   public void b() {
   }
 };

 public static final D d = new D() {};

 @Test
 public void testA() {
   testGetAllInterfaces(new A() {
     @Override
     public void a() {
     }
   }, A.class);
 }

 @Test
 public void testB() {
   testGetAllInterfaces(b, A.class, B.class);
 }

 @Test
 public void testC() {
   testGetAllInterfaces(c, A.class, B.class, C.class);
 }

 @Test
 public void testD() {
   testGetAllInterfaces(d, D.class);
 }

 @Test
 public void testFoo() {
   testGetAllInterfaces(foo, A.class, B.class, C.class, D.class);
 }

 @Test
 public void testBar() {
   testGetAllInterfaces(bar, A.class, B.class, C.class, D.class);
 }
}