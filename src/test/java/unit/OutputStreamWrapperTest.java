package unit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Test;
import net.whg.we.util.ILogger;
import net.whg.we.util.OutputStreamWrapper;
import net.whg.we.util.OutputStreamWrapper.LogLevel;

public class OutputStreamWrapperTest
{
    @Test
    @SuppressWarnings("resource")
    public void writeLine()
    {
        ILogger logger = mock(ILogger.class);
        OutputStreamWrapper wrapper = new OutputStreamWrapper(LogLevel.INFO, logger);

        PrintStream stream = new PrintStream(wrapper);
        stream.println("Hello Steve.");
        stream.println("What's up?");

        verify(logger).info("Hello Steve.");
        verify(logger).info("What's up?");
    }

    @Test
    @SuppressWarnings("resource")
    public void writeTwoLines_oneString()
    {
        ILogger logger = mock(ILogger.class);
        OutputStreamWrapper wrapper = new OutputStreamWrapper(LogLevel.DEBUG, logger);

        PrintStream stream = new PrintStream(wrapper);
        stream.println("Abc\n123");

        verify(logger).debug("Abc");
        verify(logger).debug("123");
    }

    @Test
    @SuppressWarnings("resource")
    public void writePartialLine()
    {
        ILogger logger = mock(ILogger.class);
        OutputStreamWrapper wrapper = new OutputStreamWrapper(LogLevel.WARN, logger);

        PrintStream stream = new PrintStream(wrapper);
        stream.print("Apple-");

        verify(logger, never()).warn(anyString());
    }

    @Test
    @SuppressWarnings("resource")
    public void writeByte() throws IOException
    {
        ILogger logger = mock(ILogger.class);

        OutputStreamWrapper wrapper = new OutputStreamWrapper(LogLevel.TRACE, logger);

        wrapper.write((byte) 'A');
        verify(logger, never()).trace(anyString());

        wrapper.write((byte) '\n');
        verify(logger).trace("A");
    }
}
