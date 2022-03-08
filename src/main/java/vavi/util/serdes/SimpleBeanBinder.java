/*
 * Copyright (c) 2022 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.util.serdes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import vavi.util.serdes.BeanBinder.IOSource;
import vavi.util.serdes.Binder.EachContext;


/**
 * SimpleBeanBinder. base for non binary sequences.
 * <p>
 * {@link #getIOSource(Object...)} argument args index 1 is boolean true: big endian
 * ({@link BaseBeanBinder} spec.)
 * </p>
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2022/02/26 umjammer initial version <br>
 */
public abstract class SimpleBeanBinder<T extends IOSource> extends BaseBeanBinder<T> {

    protected static class SimpleContext<U> implements BeanBinder.Context {
        public U in;
        SimpleContext(U in, List<Field> fields, Object bean) {
            this.in = in;
        }
    }

    public class SimpleEachContext implements Binder.EachContext {
        int sequence;
        Field field;
        public SimpleContext<T> context;
        SimpleEachContext(int sequence, Boolean isBigEndian, Field field, Context context) {
            this.sequence = sequence;
            this.field = field;
            this.context = SimpleContext.class.cast(context);
        }

        @Override public void deserialize(Object destBean) throws IOException {
            deserialize0(context.in, destBean);
        }

        Object value;
        @Override public Object getValue() {
            return value;
        }
        @Override public void setValue(Object value) {
            this.value = value;
        }
        @Override public int getSequence() {
            return sequence;
        }
        @Override public void validate(String validation) {
        }
        @Override public boolean condition(String condition) {
            return true;
        }
        @Override public void settleValues() {
        }
    }

    /** {@link Binder} is state-less */
    private static final DefaultBinder defaultBinder = new DefaultBinder();

    @Override
    protected Binder getDefaultBinder() {
        return defaultBinder;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Context getContext(IOSource in, List<Field> fields, Object bean) {
        return new SimpleContext(in, fields, bean);
    }

    @Override
    protected EachContext getEachContext(int sequence, Boolean isBigEndian, Field field, Context context) {
        return new SimpleEachContext(sequence, isBigEndian, field, context);
    }
}
/* */
