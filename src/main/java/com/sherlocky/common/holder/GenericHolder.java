package com.sherlocky.common.holder;

import java.io.Serializable;

/**
 * Holds a value of type <code>T</code>.
 *
 * @author sherlock
 * @date 2019/10/24 15:40
 * @since 0.2.0
 */
public final class GenericHolder<T> implements Serializable {
    private static final long serialVersionUID = 5398747352107158279L;
    /**
     * The value contained in the holder.
     */
    public T value;

    /**
     * Creates a new holder with a <code>null</code> value.
     */
    public GenericHolder() {
    }

    /**
     * Create a new holder with the specified value.
     *
     * @param value The value to be stored in the holder.
     */
    public GenericHolder(T value) {
        this.value = value;
    }
}

