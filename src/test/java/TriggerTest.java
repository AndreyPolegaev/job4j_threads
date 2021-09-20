import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class TriggerTest  {

    @Test
    public void whenTest() {
        Trigger trigger = new Trigger();
        assertThat(trigger.test(), is(1));
    }
}