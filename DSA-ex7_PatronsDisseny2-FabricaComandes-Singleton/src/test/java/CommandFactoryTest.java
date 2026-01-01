import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandFactoryTest {
    CommandFactory cf = null;

    @Before
    public void setUp() throws Exception {
        cf = CommandFactory.getInstance();
    }

    @After
    public void tearDown() {
        cf.clear();
    }

    @Test
    public void testCopyCommand() throws Exception {
        Assert.assertEquals(0, cf.numCommands());
        Command copyCommand = cf.getCommand("CopyCommand");
        Assert.assertEquals(copyCommand.getClass(), CopyCommand.class);

        copyCommand = cf.getCommand("CopyCommand");
        Assert.assertEquals(copyCommand.getClass(), CopyCommand.class);
        Assert.assertEquals(1, cf.numCommands());

        copyCommand.execute();

        RuntimeException ex = Assert.assertThrows(RuntimeException.class, () ->
                cf.getCommand("XXXXCommand"));
        Assert.assertEquals(ClassNotFoundException.class, ex.getCause().getClass());
    }

    @Test
    public void testPasteCommand() throws Exception {
        Assert.assertEquals(0, cf.numCommands());
        Command pasteCommand = cf.getCommand("PasteCommand");
        Assert.assertEquals(pasteCommand.getClass(), PasteCommand.class);

        pasteCommand = cf.getCommand("PasteCommand");
        Assert.assertEquals(pasteCommand.getClass(), PasteCommand.class);
        Assert.assertEquals(1, cf.numCommands());

        pasteCommand.execute();

    }
}
