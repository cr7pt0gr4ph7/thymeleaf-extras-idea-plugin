package org.thymeleaf.extras.idea.util;

import com.intellij.util.containers.Convertor;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyContainerUtil {

    /**
     * Group all items inside {@code iterator} by the key returned by {@code keyConverter}
     * and return the result as a {@link com.intellij.util.containers.MultiMap} that preserves
     * order w.r.t. a single key.
     *
     * @param iterator     the source of the items
     * @param keyConvertor the function that maps items to keys
     * @param <K>          the type of the keys
     * @param <V>          the type of the values
     * @return a {@code MultiMap} containingthe elements grouped by key
     */
    @NotNull
    public static <K, V> MultiMap<K, V> groupBy(@NotNull final Iterator<V> iterator,
                                                @NotNull final Convertor<V, K> keyConvertor) {
        return groupBy(iterator, keyConvertor, new MultiMap<K, V>());
    }

    /**
     * Group all items inside {@code iterator} by the key returned by {@code keyConverter}
     * and add the result to {@code destination}.
     * <p/>
     * The {@code destination} parameter is primarily intended to supply a different map implementation
     * than the default {@link com.intellij.util.containers.MultiMap} implementation.
     * The collection should normally be empty when being passed into this function.
     *
     * @param iterator     the source of the items
     * @param keyConvertor the function that maps items to keys
     * @param destination  the map into which the resulting key value pairs will be added
     * @param <K>          the type of the keys
     * @param <V>          the type of the values
     * @return {@code destination} with the new elements added
     */
    @NotNull
    public static <K, V> MultiMap<K, V> groupBy(@NotNull final Iterator<V> iterator,
                                                @NotNull final Convertor<V, K> keyConvertor,
                                                @NotNull final MultiMap<K, V> destination) {
        while (iterator.hasNext()) {
            final V value = iterator.next();
            final K key = keyConvertor.convert(value);
            destination.putValue(key, value);
        }
        return destination;
    }

    @NotNull
    public static <K, V extends Comparable<? super V>> MultiMap<K, V> groupByAndSort(@NotNull final Iterator<V> iterator,
                                                                                     @NotNull final Convertor<V, K> keyConvertor) {
        final MultiMap<K, V> result = groupBy(iterator, keyConvertor);
        sortGroupsInPlace(result);
        return result;
    }

    public static <K, V extends Comparable<? super V>> void sortGroupsInPlace(@NotNull final MultiMap<K, V> source) {
        for (Map.Entry<K, Collection<V>> entry : source.entrySet()) {
            Collections.sort((List<V>) entry);
        }
    }
}
