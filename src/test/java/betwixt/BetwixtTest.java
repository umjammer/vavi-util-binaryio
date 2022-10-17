/*
 * Copyright (c) 2003 by Naohide Sano, All Rights Reserved.
 *
 * Programmed by Naohide Sano
 */

package betwixt;

import java.io.File;

import org.apache.commons.betwixt.io.BeanReader;


/**
 * Binary Binding test.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 031215 nsano initial version <br>
 */
public class BetwixtTest {

    /**
     *
     */
    public static void main(String[] args) throws Exception {

        BeanReader reader = new BeanReader();
        reader.registerBeanClass(CsvFormat.class);
        CsvFormat bean = (CsvFormat) reader.parse(new File(args[0]));

        System.err.println("bean: " + bean);
        System.err.println("count: " + bean.getCount());
        System.err.println("validators: " + bean.getValidators().size());
        for (Validator validator : bean.getValidators()) {
            System.err.println("validator: " + validator);
        }
    }
}

/* */
