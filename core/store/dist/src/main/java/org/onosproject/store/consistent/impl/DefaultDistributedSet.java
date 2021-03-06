/*
 * Copyright 2015 Open Networking Laboratory
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
package org.onosproject.store.consistent.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.Serializer;

import com.google.common.collect.Sets;

/**
 * Implementation of distributed set that is backed by a ConsistentMap.

 * @param <E> set element type
 */
public class DefaultDistributedSet<E> implements Set<E> {

    private final ConsistentMap<E, Boolean> backingMap;

    public DefaultDistributedSet(String name, Database database, Serializer serializer) {
        backingMap = new DefaultConsistentMap<>(name, database, serializer);
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return backingMap.containsKey((E) o);
    }

    @Override
    public Iterator<E> iterator() {
        return backingMap.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return backingMap.keySet().stream().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backingMap.keySet().stream().toArray(size -> a);
    }

    @Override
    public boolean add(E e) {
        return backingMap.putIfAbsent(e, true) == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        return backingMap.remove((E) o, true);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream()
                .allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return c.stream()
                .map(this::add)
                .reduce(Boolean::logicalOr)
                .orElse(false);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> retainSet = Sets.newHashSet(c);
        return backingMap.keySet()
                .stream()
                .filter(k -> !retainSet.contains(k))
                .map(this::remove)
                .reduce(Boolean::logicalOr)
                .orElse(false);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<?> removeSet = Sets.newHashSet(c);
        return backingMap.keySet()
            .stream()
            .filter(removeSet::contains)
            .map(this::remove)
            .reduce(Boolean::logicalOr)
            .orElse(false);
    }

    @Override
    public void clear() {
        backingMap.clear();
    }
}
