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

import java.util.HashSet;

public final class Interfaces {
  /**
   * Adds all interfaces extended by the specified {@code iface} interface
   * {@link Class}.
   *
   * @param iface The interface {@link Class}.
   * @param set The set into which all extended interfaces are to be added.
   * @throws NullPointerException If {@code iface} or {@code set} is null.
   */
  private static void recurse(final Class<?> iface, final HashSet<Class<?>> set) {
    if (set.contains(iface))
      return;

    set.add(iface);
    for (final Class<?> extended : iface.getInterfaces())
      recurse(extended, set);
  }

  /**
   * Returns all interfaces implemented by the class or interface represented by
   * the specified class. This method differentiates itself from
   * {@link Class#getInterfaces()} by returning <i>all</i> interfaces (full
   * depth and breadth) instead of just the interfaces <i>directly</i>
   * implemented by the class.
   *
   * @param cls The class.
   * @return All interfaces implemented by the class or interface represented by
   *         the specified class.
   * @throws NullPointerException If {@code cls} is null.
   */
  public static Class<?>[] getAllInterfaces(final Class<?> cls) {
    Class<?> parent = cls;
    HashSet<Class<?>> set = null;
    do {
      final Class<?>[] ifaces = parent.getInterfaces();
      if (set == null) {
        if (ifaces.length == 0)
          return ifaces;

        set = new HashSet<>(4);
      }

      for (final Class<?> iface : ifaces)
        recurse(iface, set);
    }
    while ((parent = parent.getSuperclass()) != null);
    return set.toArray(new Class[set.size()]);
  }

  private Interfaces() {
  }
}