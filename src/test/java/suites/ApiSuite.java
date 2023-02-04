package suites;

import FUNC1.HhBdTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses(value = {HhBdTest.class})
public class ApiSuite {
}
