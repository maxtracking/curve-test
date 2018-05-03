package me.alexeygusev.curve.model;

import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Very basic unit test. We could use @RunWith(MockitoJUnitRunner.class) except it doesn't let you mock static methods.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class FieldsTest {

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getTotalSum() {
        String value = "";
        when(TextUtils.isEmpty(value)).thenAnswer((Answer<Boolean>) invocation -> {
            String value1 = invocation.getArgument(0);
            return (value1 == null || value1.equalsIgnoreCase(""));
        });
        Fields fields = new Fields();
        fields.setField01("1");
        assertEquals("1", fields.getTotalSum());
        fields.setField05("6");
        assertEquals("7", fields.getTotalSum());
        fields.setField05("");
        assertEquals("1", fields.getTotalSum());
    }
}
